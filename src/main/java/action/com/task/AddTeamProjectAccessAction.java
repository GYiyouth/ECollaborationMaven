package action.com.task;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TaskDAO;
import pojo.DAO.TeamDAO;
import pojo.businessObject.TaskBO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 给团队完成的任务打分
 * Created by GR on 2017/3/3.
 */
public class AddTeamProjectAccessAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    //jsp获取
    private Integer access;
    private Integer taskId;
    private Integer teamId;
    private Integer projectId;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        try{
            TaskDAO taskDAO = BeanFactory.getApplicationContext().getBean("taskDAO",TaskDAO.class);
            ProjectDAO projectDAO = BeanFactory.getApplicationContext().getBean("projectDAO",ProjectDAO.class);
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO",TeamDAO.class);
            TaskBO taskBO = BeanFactory.getApplicationContext().getBean("taskBO",TaskBO.class);
            TaskVO taskVO = taskDAO.getTaskVOByTaskId(taskId);
            ProjectVO projectVO = projectDAO.getProjectVO(projectId);
            TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
            jsonObject = taskBO.addTeamProjectAccess(taskVO,teamVO,projectVO,access,session);
            JSONHandler.sendJSON(jsonObject,response);
            System.out.println("添加项目团队评价");
            System.out.println(jsonObject);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
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

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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
