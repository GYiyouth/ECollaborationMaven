package action.com.project;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Service;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;
import tool.JSONHandler;
import tool.Time;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 老师获取项目，分为以往和当下
 * 提供nowProjects，oldProjects
 * Created by geyao on 2017/3/31.
 */
@Service
public class GetTeacherProjectAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private Map<String,Object> session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Resource(name = "projectBO")
    private ProjectBO projectBO;

    public String execute() throws Exception {
        TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
        if (teacherVO == null)
            return "fail";
        JSONObject jsonObject = BeanFactory.getJSONO();
        try {

            Integer grade = Integer.parseInt(Time.getGrade());
            ArrayList<ProjectVO> projectVOs = projectBO.getTeacherProjectVOList(teacherVO, session, null);
            projectVOs = (projectVOs == null)? new ArrayList<>(): projectVOs;
            ArrayList<ProjectDTO> nowProjects = new ArrayList<>();
            ArrayList<ProjectDTO> oldProjects = new ArrayList<>();
            for (ProjectVO projectVO : projectVOs){
                ProjectDTO projectDTO = BeanFactory.getBean("projectDTO", ProjectDTO.class);
                projectDTO.clone(projectVO);
                if (projectVO.getGrade() != null && projectVO.getGrade().equals(grade))
                    nowProjects.add(projectDTO);
                else
                    oldProjects.add(projectDTO);
            }
            jsonObject.put("result", "success");
            jsonObject.put("nowProjects", nowProjects);
            jsonObject.put("oldProjects", oldProjects);
            JSONHandler.sendJSON(jsonObject, response);
            System.out.println("获取老师项目");
            System.out.println(jsonObject);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }



    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
            this.request = request;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        this.response = response;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ProjectBO getProjectBO() {
        return projectBO;
    }

    public void setProjectBO(ProjectBO projectBO) {
        this.projectBO = projectBO;
    }


}
