package action.com.team;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import pojo.businessObject.TeamBO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 创建团队
 * Created by GR on 2017/2/26.
 */
public class CreateTeamAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

    //jsp获取
    private String teamName;
    private String description;
    private Integer memberMax;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        TeamBO teamBO = BeanFactory.getApplicationContext().getBean("teamBO",TeamBO.class);
        try {
            jsonObject = teamBO.createTeam(teamName, description, memberMax, session);
            JSONHandler.sendJSON(jsonObject,response);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(Integer memberMax) {
        this.memberMax = memberMax;
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

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
