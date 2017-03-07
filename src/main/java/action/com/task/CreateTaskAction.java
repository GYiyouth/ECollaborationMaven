package action.com.task;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.components.Bean;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.TaskBO;
import pojo.valueObject.domain.TaskVO;
import tool.BeanFactory;
import tool.JSONHandler;
import tool.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/3/2.
 */
public class CreateTaskAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

    //jsp获取
    private String title;
    private String content;
    private String createDate;
//    private String modifyDate;
    private String beginDate;
    private String targetDate;
    private Integer projectId;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;


    @Override
    public String execute() throws Exception {
        TaskVO taskVO = BeanFactory.getApplicationContext().getBean("taskVO",TaskVO.class);
        TaskBO taskBO = BeanFactory.getApplicationContext().getBean("taskBO",TaskBO.class);
        taskVO.setTitle(title);
        taskVO.setContent(content);
        taskVO.setBeginDate(beginDate);
        taskVO.setTargetDate(targetDate);
        taskVO.setCreateDate(Time.getCurrentTime());
        try{
            jsonObject = taskBO.addTaskToProject(taskVO,projectId,session);
            JSONHandler.sendJSON(jsonObject,response);
            return "success";
        }catch (Exception e){
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
