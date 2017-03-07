package action.com.logIn;

import com.opensymphony.xwork2.ActionSupport;
//import com.sun.deploy.net.HttpResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.businessObject.UserBO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登出
 * Created by GR on 2017/2/26.
 */
public class LogOutAction extends ActionSupport implements ServletResponseAware,SessionAware{

    private HttpServletResponse response;
    private Map<String, Object> session;
    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        UserBO userBO = BeanFactory.getApplicationContext().getBean("userBO",UserBO.class);
        try {
            jsonObject = userBO.logOut(session);
            JSONHandler.sendJSON(jsonObject,response);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        return  null;
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

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map<String, Object> getSession() {
        return session;
    }


}
