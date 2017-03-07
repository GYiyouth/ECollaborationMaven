package action.com.project;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.ProjectBO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
public class ApplyProjectAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map session;

    //需求jsp
    private Integer teamId;
    private Integer projectId;

    @SuppressWarnings("all")
    public String execute() throws Exception {
        if (teamId == null || projectId == null)
            return "success";

        JSONObject jsonObject = BeanFactory.getJSONO();
        try {
            ProjectBO projectBO = BeanFactory.getBean("projectBO", ProjectBO.class);
            projectBO.applyProject(teamId, projectId);
            jsonObject.put("result", "success");
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            JSONHandler.sendJSON(jsonObject, response);
            e.printStackTrace();
            return "fail";
        }
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        try {
            this.request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
        this.response.setCharacterEncoding("UTF-8");
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
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

    public Map getSession() {
        return session;
    }


    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
