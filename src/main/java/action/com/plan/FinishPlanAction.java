package action.com.plan;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.DAO.PlanDAO;
import pojo.businessObject.PlanBO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 完成任务的action
 * Created by geyao on 2017/4/2.
 */
public class FinishPlanAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;
    @Autowired
    private PlanBO planBO;

    //以下是JSP中获取
    private Integer planId;


    public String execute() throws Exception {
        JSONObject jsonObject = BeanFactory.getJSONO();
        StudentVO studentVO = (StudentVO) session.get("studentVO");
        if (studentVO == null) {
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        if (planBO.finishAction(studentVO, planId) )
            jsonObject.put("result", "success");
        JSONHandler.sendJSON(jsonObject, response);
        return "success";
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

    public PlanBO getPlanBO() {
        return planBO;
    }

    public void setPlanBO(PlanBO planBO) {
        this.planBO = planBO;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
