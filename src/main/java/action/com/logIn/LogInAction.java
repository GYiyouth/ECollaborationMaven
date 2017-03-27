package action.com.logIn;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.businessObject.UserBO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Handler;

/**
 * 登录
 * Created by geyao on 2017/2/19.
 */
public class LogInAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

    //jsp获取：
    private String userName;
    private String passWord;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        session.clear();
        System.out.println("session已清空 =========  ");
        UserBO userBO = BeanFactory.getBean("userBO",UserBO.class);
        try {
            System.out.println("request" + request);
            jsonObject = userBO.logIn(userName, passWord, session);
            System.out.println("jsonObject = " + jsonObject);
            //43行调用的logIn已经对result赋值了？
//            jsonObject.put("result", "success");

            JSONHandler.sendJSON(jsonObject,response);
            return jsonObject.get("role").toString();
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public Map<String, Object> getSession() {
        return session;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
