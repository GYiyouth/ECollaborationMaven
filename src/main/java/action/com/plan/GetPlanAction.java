package action.com.plan;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.PlanBO;
import pojo.valueObject.DTO.PlanDTO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 根据projectId查询计划
 * 提供planBeanList
 * Created by geyao on 2017/3/2.
 */
public class GetPlanAction implements SessionAware, ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private Integer projectId;

    private ArrayList<PlanDTO> planBeanList;

    @SuppressWarnings("all")
    public String execute() throws Exception {
        JSONObject jsonObject = BeanFactory.getJSONO();
        PlanBO planBO = BeanFactory.getBean("planBO", PlanBO.class);
        StudentVO studentVO = (StudentVO) session.get("studentVO");
        if (studentVO == null){
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        try {
            planBeanList = planBO.getPlanDTO(studentVO, projectId, session);
            jsonObject.put("result", "success");
            jsonObject.put("planBeanList", planBeanList);
            JSONHandler.sendJSON(jsonObject, response);
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            throw e;
        }
        return "fail";
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public ArrayList<PlanDTO> getPlanBeanList() {
        return planBeanList;
    }

    public void setPlanBeanList(ArrayList<PlanDTO> planBeanList) {
        this.planBeanList = planBeanList;
    }
}
