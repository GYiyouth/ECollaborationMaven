package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.valueObject.assist.ProjectTaskVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import tool.BeanFactory;

/**
 * Created by GR on 2017/3/2.
 */
public class TaskDAO {

    /**
     * 老师发布任务
     * @param taskVO
     * @param projectVO
     * @return
     */
    public TaskVO addTaskToProject(TaskVO taskVO, ProjectVO projectVO){
        if(taskVO==null||projectVO==null){
            System.out.println("ERROR:taskVO/projectVO is null---"+this.getClass()+"---addTaskToProject()");
            return null;
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            ProjectTaskVO projectTaskVO = BeanFactory.getApplicationContext().getBean("projectTaskVO",ProjectTaskVO.class);
            Transaction transaction = session.beginTransaction();
            try{
                session.save(taskVO);
                projectTaskVO.setProjectVO(projectVO);
                projectTaskVO.setTaskVO(taskVO);
                session.save(projectTaskVO);
                transaction.commit();
                return taskVO;
            }catch (Exception e){
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }finally {
                session.close();
            }
        }
    }
}
