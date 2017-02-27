package action.com.team;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.BO.TeamBO;
import tool.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/2/27.
 */
public class ApplyJoinTeamAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

    //jsp获取：
    private Integer teamId;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        ApplicationContext context = BeanFactory.getApplicationContext();
        TeamBO teamBO = context.getBean("teamBO",TeamBO.class);
        return teamBO.applyJoinTeam(teamId,session);
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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
