package action.com.logIn;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.businessObject.UserBO;
import tool.BeanFactory;

import java.util.Map;

/**
 * 登出
 * Created by GR on 2017/2/26.
 */
public class LogOutAction extends ActionSupport implements SessionAware{

    public Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        ApplicationContext context = BeanFactory.getApplicationContext();
        UserBO userBO = context.getBean("userBO",UserBO.class);
        return userBO.logOut(session);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
