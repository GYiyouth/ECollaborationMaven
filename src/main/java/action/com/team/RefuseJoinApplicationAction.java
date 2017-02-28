package action.com.team;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.TeamBO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/2/28.
 */
public class RefuseJoinApplicationAction extends ActionSupport implements ServletRequestAware, ServletResponseAware,SessionAware {

    //jsp获取
    private int applicationId;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        TeamBO teamBO = BeanFactory.getApplicationContext().getBean("teamBO",TeamBO.class);
        jsonObject = teamBO.refuseJoinApplication(applicationId);
        if(jsonObject==null){
            System.out.println("ERROR:jsonObject==null---"+this.getClass()+"---execute()");
            return "fail";
        }else{
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
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

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}