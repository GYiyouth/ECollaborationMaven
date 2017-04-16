package action.com.team;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.DAO.TeamDAO;
import pojo.businessObject.TeamBO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by GR on 2017/4/16.
 */
public class ModifyTeamInfoAction extends ActionSupport implements ServletRequestAware, ServletResponseAware,SessionAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    //jsp
    private Integer id;
    private String teamName;
    private int memberMax;
    private String description;

    @Override
    public String execute() throws Exception {
        try {
//            StudentVO studentVO = BeanFactory.getBean("studentVO", StudentVO.class);
            TeamDAO teamDAO = BeanFactory.getBean("teamDAO",TeamDAO.class);
            TeamVO teamVO = teamDAO.getTeamVOByTeamId(id);
            teamVO.setTeamName(teamName);
            teamVO.setMemberMax(memberMax);
            teamVO.setDescription(description);
            TeamBO teamBO = BeanFactory.getBean("teamBO", TeamBO.class);
            JSONObject jsonObject = teamBO.modifyTeamInfo(teamVO);
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "fail";
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(int memberMax) {
        this.memberMax = memberMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
