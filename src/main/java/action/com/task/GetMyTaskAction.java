package action.com.task;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TaskDAO;
import pojo.businessObject.TaskBO;
import pojo.valueObject.DTO.TaskDTO;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/4/16.
 */
public class GetMyTaskAction  extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    //jsp
    private Integer projectId;

    @Override
    public String execute() throws Exception {
        try {
            ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
            TaskBO taskBO = BeanFactory.getBean("taskBO", TaskBO.class);
            ProjectVO projectVO = projectDAO.getProjectVO(projectId);
            if (projectVO != null) {
                jsonObject = taskBO.getMyTask(projectVO);
                JSONHandler.sendJSON(jsonObject, response);
                System.out.println("获取我的任务");
                System.out.println(jsonObject);
                return "success";
            }else{
                jsonObject.put("result","SQLException");
                JSONHandler.sendJSON(jsonObject, response);
                return "fail";
            }
        }catch(Exception e){
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        try {
            request.setCharacterEncoding("UTF-8");
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

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
