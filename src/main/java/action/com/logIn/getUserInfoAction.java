package action.com.logIn;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.UserBO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/4/15.
 */
public class getUserInfoAction  extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;


    @Override
    public String execute() throws Exception {
        UserBO userBO = BeanFactory.getBean("userBO",UserBO.class);
        try {
            jsonObject = userBO.getInfo(session);
            System.out.println("jsonObject = " + jsonObject);
            //43行调用的logIn已经对result赋值了？
//            jsonObject.put("result", "success");

            JSONHandler.sendJSON(jsonObject,response);
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
}
