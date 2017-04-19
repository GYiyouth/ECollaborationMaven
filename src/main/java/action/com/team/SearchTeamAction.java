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
 *
 * Created by Administrator on 2017/3/3.
 */
public class SearchTeamAction  extends ActionSupport implements ServletRequestAware, ServletResponseAware,SessionAware {

    //jsp获取
    private String teamSearchInfo;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private JSONObject jsonObject;

    @Override
    public String execute() throws Exception {
        TeamBO teamBO = BeanFactory.getApplicationContext().getBean("teamBO", TeamBO.class);
        try{
            jsonObject = teamBO.searchTeam(teamSearchInfo);
            JSONHandler.sendJSON(jsonObject,response);
            System.out.println("搜索团队");
            System.out.println(jsonObject);
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
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
//        this.response.setCharacterEncoding("UTF-8");
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getTeamSearchInfo() {
        return teamSearchInfo;
    }

    public void setTeamSearchInfo(String teamSearchInfo) {
        this.teamSearchInfo = teamSearchInfo;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
