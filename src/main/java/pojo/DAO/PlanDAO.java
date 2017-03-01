package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojo.valueObject.assist.StudentProjectPlanVO;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;

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
            StudentProjectPlanVO sppVO = BeanFactory.getBean("studentProjectPlanVO", StudentProjectPlanVO.class);

            sppVO.setPlanVO(planVO);
            sppVO.setStudentVO(planVO.getStudentVO());
            sppVO.setProjectVO(projectVO);
            session.save(planVO);
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
}
