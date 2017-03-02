package pojo.DAO;

import org.apache.struts2.components.Bean;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.valueObject.assist.ProjectTaskVO;
import pojo.valueObject.assist.TeamProjectAccessVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeamVO;
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

    /**
     * l老师对任务评价
     * @return
     */
    public String addTeamProjectAccess(TeamProjectVO teamProjectVO,TaskVO taskVO, Integer access) throws Exception{
        if(teamProjectVO==null||taskVO == null||access == null){
            throw new NullPointerException("ERROR:teamProjectVO==null||taskVO == null||access == null--"+this.getClass()+"---addTeamProjectAccess()");
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            try{
                TeamProjectAccessVO teamProjectAccessVO = BeanFactory.getApplicationContext().getBean("teamProjectAccessVO", TeamProjectAccessVO.class);
                teamProjectAccessVO.setTaskVO(taskVO);
                teamProjectAccessVO.setTeam_project_id(teamProjectVO.getId());
                teamProjectAccessVO.setAccess(access);
                session.save(teamProjectAccessVO);
                transaction.commit();
                return "success";
            }catch (Exception e){
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }finally {
                session.close();
            }
        }
    }

    /**
     * 根据taskId获取TaskVO
     * @param taskId
     * @return
     * @throws Exception
     */
    public TaskVO getTaskVOByTaskId(Integer taskId)throws Exception{
        if(taskId==null){
            throw new NullPointerException("ERROR:taskId==null---"+this.getClass()+"---getTaskVOByTaskId()");
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            try {
                TaskVO taskVO = session.get(TaskVO.class, taskId);
                return taskVO;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                session.close();
            }
        }
    }
}
