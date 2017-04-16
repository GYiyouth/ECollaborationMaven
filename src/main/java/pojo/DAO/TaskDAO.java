package pojo.DAO;

import org.apache.struts2.components.Bean;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pojo.valueObject.assist.ProjectTaskVO;
import pojo.valueObject.assist.TeamProjectAccessVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.ContributionsVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyao on 2017/3/2.
 */
@Repository
public class TaskDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

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
//            Session session = BeanFactory.getSessionFactory().getCurrentSession();
//            ProjectTaskVO projectTaskVO = BeanFactory.getApplicationContext().getBean("projectTaskVO",ProjectTaskVO.class);
//            Transaction transaction = session.beginTransaction();
//            try{
//                session.save(taskVO);
//                projectTaskVO.setProjectVO(projectVO);
//                projectTaskVO.setTaskVO(taskVO);
//                session.save(projectTaskVO);
//                transaction.commit();
//                return taskVO;
//            }catch (Exception e){
//                e.printStackTrace();
//                transaction.rollback();
//                throw e;
//            }finally {
//
//            }

            hibernateTemplate.save(taskVO);
//            taskVO.getProjectVOSet().add(projectVO);
            ProjectTaskVO projectTaskVO = BeanFactory.getBean("projectTaskVO", ProjectTaskVO.class);
            projectTaskVO.setTaskVO(taskVO);
            projectTaskVO.setProjectVO(projectVO);
            hibernateTemplate.save(projectTaskVO);
            return taskVO;
        }
    }

    /**
     * 老师对任务评价
     * @return
     */
    public String addTeamProjectAccess(TeamProjectVO teamProjectVO,TaskVO taskVO, Integer access) throws Exception{
        if(teamProjectVO==null||taskVO == null||access == null){
            throw new NullPointerException("ERROR:teamProjectVO==null||taskVO == null||access == null--"+this.getClass()+"---addTeamProjectAccess()");
        }else{
//            Session session = BeanFactory.getSessionFactory().getCurrentSession();
//            Transaction transaction = session.beginTransaction();
//            try{
                TeamProjectAccessVO teamProjectAccessVO = BeanFactory.getApplicationContext().getBean("teamProjectAccessVO", TeamProjectAccessVO.class);
                teamProjectAccessVO.setTaskVO(taskVO);
                teamProjectAccessVO.setTeam_project_id(teamProjectVO.getId());
                teamProjectAccessVO.setAccess(access);
                hibernateTemplate.save(teamProjectAccessVO);
                return "success";
//                session.save(teamProjectAccessVO);
//                transaction.commit();
//                return "success";
//            }catch (Exception e){
//                e.printStackTrace();
//                transaction.rollback();
//                throw e;
//            }finally {
////                session.close();
//            }
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
//            Session session = BeanFactory.getSessionFactory().getCurrentSession();
            try {
                TaskVO taskVO = hibernateTemplate.get(TaskVO.class, taskId);
                return taskVO;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
//                session.close();
            }
        }
    }

    /**
     * 根据项目获取任务列表
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ArrayList<TaskVO> getTasksByProject(ProjectVO projectVO) throws Exception{
        if(projectVO==null){
            throw  new NullPointerException("projectVO是空的---"+this.getClass()+"---getTaskByProject()");
        }else{
            String hql = "select pt.taskVO from ProjectTaskVO pt where pt.projectVO = ?";
            List<TaskVO> list = (List<TaskVO>) hibernateTemplate.find(hql, projectVO);
            return (ArrayList<TaskVO>) list;
        }
    }
}
