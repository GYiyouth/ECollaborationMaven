package action.com.project;

import action.com.AbstractAction;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.businessObject.MessageBO;
import pojo.businessObject.ProjectBO;
import pojo.businessObject.TeamBO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

/**
 * 辞退某个团队，需要修改team_project表
 * 仅返回结果
 * Created by geyao on 2017/4/16.
 */
@Controller
public class FireTeamFromProjectAction extends AbstractAction {
    //JSP提供团队id和项目id
    private Integer teamId;
    private Integer projectId;


    private ProjectBO projectBO;

    @Autowired
    private MessageBO messageBO;

    private TeamBO teamBO;

    public String execute() throws Exception{
        String role = session.get("role").toString();
        JSONObject jsonObject = BeanFactory.getJSONO();
        if (role != null) {
            try {
                UserVO userVO = (UserVO) session.get(role + "VO");
                if (projectBO.fireTeam(userVO, teamId, projectId)){
                    messageBO.fireTeam(teamId, projectId, userVO);
                    jsonObject.put("result", "success");
                    JSONHandler.sendJSON(jsonObject, response);
                    return "success";
                }else {
                    JSONHandler.sendJSON(jsonObject, response);
                    return "fail";
                }
            }catch (Exception e){
                e.printStackTrace();
                JSONHandler.sendJSON(jsonObject, response);
                return "fail";
            }

        }
        JSONHandler.sendJSON(jsonObject, response);
        return "fail";
    }


    public ProjectBO getProjectBO() {
        return projectBO;
    }

    @Autowired
    public void setProjectBO(ProjectBO projectBO) {
        this.projectBO = projectBO;
    }

    public TeamBO getTeamBO() {
        return teamBO;
    }

    @Autowired
    public void setTeamBO(TeamBO teamBO) {
        this.teamBO = teamBO;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
