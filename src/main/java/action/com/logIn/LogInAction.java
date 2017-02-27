package action.com.logIn;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.BO.UserBO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
    private UserVO userVO;

    @Override
    public String execute() throws Exception {
        ApplicationContext context = BeanFactory.getApplicationContext();
        UserBO userBO = context.getBean("userBO",UserBO.class);
        return userBO.logIn(userName,passWord,session);
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

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
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
