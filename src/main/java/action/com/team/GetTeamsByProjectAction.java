package action.com.team;

import action.com.Base.BaseAction;
import pojo.businessObject.ProjectBO;
import pojo.businessObject.TeamBO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/19.
 */
public class GetTeamsByProjectAction extends BaseAction{

    //jsp
    private Integer projectId;

    @Override
    public String execute() throws Exception {
        try{
            TeamBO teamBO = BeanFactory.getBean("teamBO", TeamBO.class);
            ProjectBO projectBO = BeanFactory.getBean("projectBO",ProjectBO.class);
            ProjectVO projectVO = projectBO.getProjectVO(projectId);
            ArrayList<TeamDTO> teamDTOS = BeanFactory.getBean("arrayList",ArrayList.class);
            if(projectVO == null){
                jsonObject.put("result","fail");
                JSONHandler.sendJSON(jsonObject,response);
                return "fail";
            }
            ArrayList<TeamVO> teamVOS = teamBO.getTeamsByProject(projectVO);
            if(teamVOS == null){
                jsonObject.put("result","fail");
                JSONHandler.sendJSON(jsonObject,response);
                return "fail";
            }
            for(TeamVO teamVO:teamVOS){
                TeamDTO teamDTO = BeanFactory.getBean("teamDTO",TeamDTO.class);
                teamDTO.clone(teamVO);
                teamDTOS.add(teamDTO);
            }
            jsonObject.put("result","success");
            jsonObject.put("teamBeans",teamDTOS);
            JSONHandler.sendJSON(jsonObject,response);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject,response);
            return "success";
        }
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
