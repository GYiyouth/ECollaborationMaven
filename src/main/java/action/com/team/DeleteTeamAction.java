package action.com.team;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.businessObject.MessageBO;
import pojo.businessObject.TeamBO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

/**
 * 删除团队的action
 * Created by geyao on 2017/4/17.
 */
@Controller
public class DeleteTeamAction extends AbstractAction {

    private Integer teamId;
    @Autowired
    private TeamBO teamBO;
    @Autowired
    private MessageBO messageBO;

    public String execute() throws Exception{
        JSONObject jsonObject = BeanFactory.getJSONO();
        String role = session.get("role").toString();
        if ( !role.equals("student") || !role.equals("manager")){
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        try {
            UserVO userVO = (UserVO) session.get(role + "VO");
            messageBO.deleteTeam(teamId, userVO);
            teamBO.deleteTeam(teamId);
            jsonObject.put("result", "success");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }finally {
            JSONHandler.sendJSON(jsonObject, response);
        }


    }
}
