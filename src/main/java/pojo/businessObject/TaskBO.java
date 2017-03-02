package pojo.businessObject;

import net.sf.json.JSONObject;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TaskDAO;
import pojo.valueObject.DTO.TaskDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by GR on 2017/3/2.
 */
public class TaskBO {

    public JSONObject addTaskToProject(TaskVO taskVO, Integer projectId, Map<String, Object> session) throws Exception{
        if(projectId==null){
            throw new NullPointerException("ERROR:projectId is null---"+this.getClass()+"---addTaskToProject()");
        }
        TaskDAO taskDAO = BeanFactory.getApplicationContext().getBean("taskDAO",TaskDAO.class);
        ProjectDAO projectDAO = BeanFactory.getApplicationContext().getBean("projectDAO",ProjectDAO.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
//        TaskVO taskVO = BeanFactory.getApplicationContext().getBean("taskVO",TaskVO.class);
        try{
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            taskVO.setCreatorTeacherVO(teacherVO);
            ProjectVO projectVO = projectDAO.getProjectVO(projectId);
            if(projectVO==null){
                throw new NullPointerException("ERROR:projectVO is null---"+this.getClass()+"---addTaskToProject()");
            }else{
                taskVO = taskDAO.addTaskToProject(taskVO,projectVO);
                TaskDTO taskDTO = BeanFactory.getApplicationContext().getBean("taskDTO", TaskDTO.class);
                taskDTO.clone(taskVO);
                jsonObject.put("result", "success");
                jsonObject.put("taskBean", taskDTO);
                return jsonObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }
}
