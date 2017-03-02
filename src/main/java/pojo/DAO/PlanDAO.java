package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.assist.StudentProjectPlanVO;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by geyao on 2017/3/1.
 */
public class PlanDAO {
    /**
     * 添加计划，同时更改plan表与student_project_plan表
     * @param planVO
     * @param projectVO
     * @return
     * @throws Exception
     */
    public PlanVO addPlan(PlanVO planVO, ProjectVO projectVO) throws Exception{
        if (planVO == null || projectVO == null)
            return null;
        SessionFactory sessionFactory= BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            session.save(planVO);
            StudentProjectPlanVO sppVO = BeanFactory.getBean("studentProjectPlanVO", StudentProjectPlanVO.class);

            sppVO.setPlanVO(planVO);
            sppVO.setStudentVO(planVO.getStudentVO());
            sppVO.setProjectVO(projectVO);
            session.save(sppVO);
            transaction.commit();
            return planVO;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 查询计划，根据studentVO与projectId
     *
     * @param studentVO
     * @param projectId
     * @return
     * @throws Exception
     */
    public ArrayList<PlanVO> getPlanVOList(StudentVO studentVO, Integer projectId) throws Exception{
        ArrayList<PlanVO> arrayList = new ArrayList<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<StudentProjectPlanVO> list;
            ProjectVO projectVO = session.get(ProjectVO.class, projectId);
            list =  session.createCriteria(StudentProjectPlanVO.class)
                    .add(Restrictions.eq("studentVO", studentVO))
                    .add(Restrictions.eq("projectVO", projectVO))
                    .addOrder(Property.forName("id").desc())
                    .list();
            transaction.commit();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                arrayList.add(((StudentProjectPlanVO)iterator.next()).getPlanVO());
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
    }
}
