package action.com.team;

import action.com.Base.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.businessObject.TeamBO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/18.
 */
public class GetMyManageTeamsAction extends BaseAction{

    @Autowired
    private TeamBO teamBO;
    @Override
    public String execute() throws Exception {
        try{
            StudentVO studentVO = (StudentVO) session.get("studentVO");
            ArrayList<TeamDTO> teamDTOS = teamBO.getMyManageTeams(studentVO);
            if(teamDTOS != null){
                jsonObject.put("result","success");
                jsonObject.put("teamBeans",teamDTOS);
                JSONHandler.sendJSON(jsonObject,response);
            }
            System.out.println("获取我管理的团队");
            System.out.println(jsonObject);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }

    }
}
