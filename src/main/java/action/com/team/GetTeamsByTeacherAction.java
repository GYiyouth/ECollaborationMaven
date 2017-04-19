package action.com.team;

import action.com.Base.BaseAction;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TeamDAO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/19.
 */
public class GetTeamsByTeacherAction extends BaseAction{



    @Override
    public String execute() throws Exception {
        try {
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
            TeamDAO teamDAO = BeanFactory.getBean("teamDAO", TeamDAO.class);
            ArrayList<ProjectVO> projectVOS = projectDAO.getTeacherProjectVOList(teacherVO);
            ArrayList<TeamDTO> teamDTOS = BeanFactory.getBean("arrayList", ArrayList.class);
            if (projectVOS != null) {
                for (ProjectVO projectVO : projectVOS) {
                    ArrayList<TeamVO> teamVOS = (ArrayList<TeamVO>) teamDAO.getTeamVOByProjectVO(projectVO);
                    if (teamVOS != null) {
                        for (TeamVO teamVO : teamVOS) {
                            boolean isHave = false;
                            for(TeamDTO teamDTO:teamDTOS){
                                if(teamDTO.getId() == teamVO.getId()) {
                                    isHave = true;
                                }
                            }
                            if(isHave == false) {
                                TeamDTO teamDTO = BeanFactory.getBean("teamDTO", TeamDTO.class);
                                teamDTO.clone(teamVO);
                                teamDTOS.add(teamDTO);
                            }
                        }

                    }
                }
            }
            jsonObject.put("result", "success");
            jsonObject.put("teamBeans", teamDTOS);
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject,response);
            return "fail";
        }
    }
}
