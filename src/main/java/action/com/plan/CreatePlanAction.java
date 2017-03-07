package action.com.plan;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.PlanBO;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;
import tool.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by geyao on 2017/3/1.
 */

public class CreatePlanAction  implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    //jsp获取
    private String title;
    private String content;
    private String targetDate;
    private Integer projectId;
    private String beginDate;
//    private Integer teamId;



    private Integer id;
    private Integer creatorId;
    private String createDate;
    private String finishDate;

    private Integer studentId;
    private Integer planId;
    @SuppressWarnings("all")
    public String execute() throws Exception {
        if (projectId == null || !session.containsKey("studentVO"))
            return "fail";
        JSONObject jsonObject = BeanFactory.getJSONO();
        try {
            PlanVO planVO = BeanFactory.getBean("planVO", PlanVO.class);
            planVO.setTitle(title);
            planVO.setContent(content);
            planVO.setStudentVO((StudentVO) session.get("studentVO"));
            planVO.setCreateDate(Time.getCurrentTime());
            planVO.setTargetDate(targetDate);
            planVO.setBeginDate(beginDate);
            PlanBO planBO = BeanFactory.getBean("planBO", PlanBO.class);
            jsonObject = planBO.savePlan(planVO, projectId, session);

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }finally {
            JSONHandler.sendJSON(jsonObject, response);
        }
    }

    public CreatePlanAction() {
        super();
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

    public Map<String, Object> getSession() {
        return session;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
