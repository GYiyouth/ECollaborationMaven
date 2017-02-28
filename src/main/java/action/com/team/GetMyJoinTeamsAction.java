package action.com.team;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.components.Bean;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.TeacherBO;
import pojo.businessObject.TeamBO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 获取我加入的所有团队信息
 * Created by GR on 2017/2/28.
 */
public class GetMyJoinTeamsAction extends ActionSupport implements ServletRequestAware, ServletResponseAware,SessionAware {

    //不需要jsp提供数据

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;
    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        TeamBO teamBO = BeanFactory.getApplicationContext().getBean("teamBO",TeamBO.class);
        jsonObject = teamBO.getMyJoinTeams(session);
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
}
