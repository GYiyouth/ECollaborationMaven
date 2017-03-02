package pojo.businessObject;

import net.sf.json.JSONObject;
import pojo.DAO.TaskDAO;
import pojo.valueObject.DTO.TaskDTO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import java.util.Map;

/**
 * Created by GR on 2017/3/2.
 */
public class TaskBO {

    public JSONObject addTaskToProject(Integer projectId, String title, String content, Map<String, Object> session) throws Exception{
//        TeacherVO teacherVO = BeanFactory.getApplicationContext().getBean("teacherVO",TeacherVO.class);
        if(projectId==null){
            throw new NullPointerException("ERROR:projectId is null---"+this.getClass()+"---addTaskToProject()");
//            return null;
        }
        TaskDAO taskDAO = BeanFactory.getApplicationContext().getBean("taskDAO",TaskDAO.class);
        TaskVO taskVO = BeanFactory.getApplicationContext().getBean("taskVO",TaskVO.class);
        try{
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            taskVO.setCreatorTeacherVO(teacherVO);
            taskVO.setTitle(title);
            taskVO.setContent(content);
//            taskVO.setBeginDate();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }
}
