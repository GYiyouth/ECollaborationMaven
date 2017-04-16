package pojo.businessObject;

import net.sf.json.JSONObject;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TaskDAO;
import pojo.valueObject.DTO.TaskDTO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;

import java.util.*;

/**
 * Created by GR on 2017/3/2.
 */

@Repository
@Transactional
public class TaskBO {

    /**
     * 添加任务
     *
     * @param taskVO
     * @param projectId
     * @param session
     * @return
     * @throws Exception
     */
    public JSONObject addTaskToProject(TaskVO taskVO, Integer projectId, Map<String, Object> session) throws Exception {
        if (projectId == null) {
            throw new NullPointerException("ERROR:projectId is null---" + this.getClass() + "---addTaskToProject()");
        }
        TaskDAO taskDAO = BeanFactory.getApplicationContext().getBean("taskDAO", TaskDAO.class);
        ProjectDAO projectDAO = BeanFactory.getApplicationContext().getBean("projectDAO", ProjectDAO.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
//        TaskVO taskVO = BeanFactory.getApplicationContext().getBean("taskVO",TaskVO.class);
        try {
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            taskVO.setCreatorTeacherVO(teacherVO);
            ProjectVO projectVO = projectDAO.getProjectVO(projectId);
            if (projectVO == null) {
                throw new NullPointerException("ERROR:projectVO is null---" + this.getClass() + "---addTaskToProject()");
            } else {
                taskVO = taskDAO.addTaskToProject(taskVO, projectVO);
                TaskDTO taskDTO = BeanFactory.getApplicationContext().getBean("taskDTO", TaskDTO.class);
                taskDTO.clone(taskVO);
                jsonObject.put("result", "success");
                jsonObject.put("taskBean", taskDTO);
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 给团队完成的任务打分
     *
     * @param taskVO
     * @param teamVO
     * @param projectVO
     * @param access
     * @param session
     * @return
     * @throws Exception
     */
    public JSONObject addTeamProjectAccess(TaskVO taskVO, TeamVO teamVO, ProjectVO projectVO, Integer access, Map<String, Object> session) throws Exception {
        if (taskVO == null || teamVO == null || projectVO == null || access == null) {
            throw new NullPointerException("ERROR:taskVO/teamVO/projectVO is null---" + this.getClass() + "---addTeamProjectAccess()");
        } else {
            ProjectDAO projectDAO = BeanFactory.getApplicationContext().getBean("projectDAO", ProjectDAO.class);
            TaskDAO taskDAO = BeanFactory.getApplicationContext().getBean("taskDAO", TaskDAO.class);
            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            try {
                TeamProjectVO teamProjectVO = projectDAO.getTeamProjectVO(teamVO, projectVO);
                if (teamProjectVO == null) {
                    throw new NullPointerException("ERROR:teamProjectVO is null---" + this.getClass() + "---addTeamProjectAccess()");
                } else {
                    String result = taskDAO.addTeamProjectAccess(teamProjectVO, taskVO, access);
                    if (result.equals("success")) {
                        jsonObject.put("result", "success");
                        return jsonObject;
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    public JSONObject getMyTask(ProjectVO projectVO) throws Exception {
        TaskDAO taskDAO = BeanFactory.getBean("taskDAO", TaskDAO.class);
        ArrayList<TaskVO> taskVOs = taskDAO.getTasksByProject(projectVO);
        ArrayList<TaskDTO> taskDTOS = new ArrayList<>();
        JSONObject jsonObject = BeanFactory.getBean("jsonObject", JSONObject.class);
        TaskDTO taskDTO = BeanFactory.getBean("taskDTO", TaskDTO.class);
        if (!taskVOs.isEmpty()) {
            jsonObject.put("result", "success");
            for (TaskVO taskVO:taskVOs){
                taskDTO.clone(taskVO);
                taskDTOS.add(taskDTO);
            }
            jsonObject.put("TaskBeans", taskDTOS);
        }
        return jsonObject;
    }
}
