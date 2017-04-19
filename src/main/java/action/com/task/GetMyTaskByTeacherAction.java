package action.com.task;

import action.com.Base.BaseAction;
import pojo.DAO.TaskDAO;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.DTO.TaskDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/19.
 */
public class GetMyTaskByTeacherAction  extends BaseAction{

    @Override
    public String execute() throws Exception {
        try {
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            ProjectBO projectBO = BeanFactory.getBean("projectBO", ProjectBO.class);
            TaskDAO taskDAO = BeanFactory.getBean("taskDAO", TaskDAO.class);
//            Integer grade = 2016;
            ArrayList allProjectTaskVOS = new ArrayList<>();
            ArrayList<ProjectVO> projectVOS = projectBO.getTeacherProjectList(teacherVO);
            if (projectVOS != null) {
                for (ProjectVO projectVO : projectVOS) {
//                    ProjectDTO projectDTO = BeanFactory.getBean("projectDTO",ProjectDTO.class);
                    ArrayList<TaskVO> taskVOS = taskDAO.getTasksByProject(projectVO);
                    ArrayList<TaskDTO> taskDTOS = new ArrayList<>();
                    if (taskVOS != null) {
                        for(TaskVO taskVO : taskVOS){
                            TaskDTO taskDTO = BeanFactory.getBean("taskDTO",TaskDTO.class);
                            taskDTO.clone(taskVO);
                            taskDTOS.add(taskDTO);
                        }
                    }
//                    projectDTO.clone(projectVO);
//                    allProjectTaskVOS.add(projectDTO);
                    allProjectTaskVOS.add(taskDTOS);
                }
            }
            System.out.println("----");
            jsonObject.put("result","success");
            jsonObject.put("taskBeans",allProjectTaskVOS);//ArrayList<ArrayList<TaskDTO>>
            JSONHandler.sendJSON(jsonObject,response);
            System.out.println("根据老师获取任务");
            System.out.println(jsonObject);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }
}
