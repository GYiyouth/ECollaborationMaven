package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pojo.valueObject.assist.ProjectAccessTypeVO;
import pojo.valueObject.assist.StudentScoreVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 打分／评价相关的方法
 * Created by geyao on 2017/3/2.
 */
@Repository
public class AccessScoreDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;


    /**
     * 添加评价种类
     * 忘了考虑百分比
     * @param typeNames
     * @param projectIdList
     * @throws Exception
     */
    public void addProjectAccessType(
            ArrayList<String>typeNames, ArrayList<Integer> projectIdList
            ) throws Exception{



        try{
            ArrayList<ProjectVO> projectVOList = new ArrayList<>();
            for (Integer id : projectIdList){
                ProjectVO projectVO = hibernateTemplate.get(ProjectVO.class, id);
                if (projectVO != null) {
                    projectVOList.add(projectVO);
                }
            }

            Iterator iterator = projectVOList.iterator();

            //嵌套遍历完成插入
            int i = 0; // 20个写入一次
            for (String typeName: typeNames
                 ) {
                while (iterator.hasNext()){
                    ProjectVO projectVO = (ProjectVO) iterator.next();
                    ProjectAccessTypeVO projectAccessTypeVO =
                            BeanFactory.getBean("projectAccessTypeVO", ProjectAccessTypeVO.class);
                    projectAccessTypeVO.setProjectVO(projectVO);
                    projectAccessTypeVO.setType(typeName);
                    hibernateTemplate.save(projectAccessTypeVO);
                    i++;
                    if (i >= 20){
                        hibernateTemplate.flush();
                        i = 0;
                    }
                }
            }

        }catch (Exception e){

            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据project查询种类
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectAccessTypeVO> getTypeListByProject(ProjectVO projectVO) throws Exception{
        if (projectVO == null){
            throw new NullPointerException("projectVO == null");
        }
        ArrayList<ProjectAccessTypeVO> arrayList = new ArrayList<>();

//        Transaction transaction = session.beginTransaction();

        try{
            List list =
                    hibernateTemplate.find("from ProjectAccessTypeVO  p where  p.projectVO.id = ? ",
                            projectVO.getId());
//            transaction.commit();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                ProjectAccessTypeVO projectAccessTypeVO = (ProjectAccessTypeVO) iterator.next();
                arrayList.add(projectAccessTypeVO);
            }
            return arrayList;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取一个学生在特定评价种类下的分数
     * @param studentVO
     * @param projectAccessTypeVOS
     * @return
     * @throws Exception
     */
    public ArrayList<StudentScoreVO> getStudentScoreVOList(
            StudentVO studentVO, ArrayList<ProjectAccessTypeVO> projectAccessTypeVOS
    ) throws Exception{
        ArrayList<StudentScoreVO> studentScoreVOS = new ArrayList<>();
        //按照顺序遍历评价的种类，添加学生的分数
        for (ProjectAccessTypeVO p : projectAccessTypeVOS){
            StudentScoreVO studentScoreVO;
            ArrayList<StudentScoreVO> list = (ArrayList<StudentScoreVO>)
                    hibernateTemplate.find("from StudentScoreVO  s where s.projectAccessTypeVO.id = ? " +
                            "and  s.studentVO.id = ?", p.getId(), studentVO.getId());
            if (list.size() > 0)
                studentScoreVOS.add(list.get(0));
        }
        return studentScoreVOS;
    }


    /**
     * 添加个人评价得分
     * @param studentScoreVOS
     * @throws Exception
     */
    public void addAccessAcore(ArrayList<StudentScoreVO> studentScoreVOS) throws Exception{
        if(studentScoreVOS == null){
            throw new NullPointerException("studentScoreVOS is null---"+this.getClass().getName()+"---addAccessToStudentAction()");
        }else{
            for(StudentScoreVO studentScoreVO:studentScoreVOS){
                hibernateTemplate.save(studentScoreVO);
            }
        }
    }

    /**
     * 获得项目得分的ProjectAccessTypeVO
     * @param typeId
     * @return
     * @throws Exception
     */
    public ProjectAccessTypeVO getProjectAccessTypeVOByTypeId(Integer typeId) throws Exception{
        if(typeId == null){
            throw new  NullPointerException("typeId is null ---"+this.getClass().getName()+"----getProjectAccessTypeVOByTypeId()");
        }else{
            return hibernateTemplate.get(ProjectAccessTypeVO.class,typeId);
        }
    }
}
