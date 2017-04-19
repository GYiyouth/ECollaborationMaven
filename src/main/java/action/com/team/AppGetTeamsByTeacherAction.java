package action.com.team;

import action.com.Base.BaseAction;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TeamDAO;
import pojo.valueObject.DTO.StudentDTO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/19.
 */
public class AppGetTeamsByTeacherAction extends BaseAction{
    @Override
    public String execute() throws Exception {
        try{
            ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            TeamDAO teamDAO = BeanFactory.getBean("teamDAO", TeamDAO.class);
            ArrayList<ProjectVO> projectVOS = projectDAO.getTeacherProjectVOList(teacherVO);
            ArrayList<TeamDTO> teamDTOS = BeanFactory.getBean("arrayList", ArrayList.class);
            ArrayList teamStudentList = BeanFactory.getBean("arrayList",ArrayList.class);
            if (projectVOS != null)  {
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
                                ArrayList<StudentVO> studentVOS = teamDAO.getStudentVOSByTeamId(teamVO.getId());
                                ArrayList<StudentDTO> studentDTOS = BeanFactory.getBean("arrayList",ArrayList.class);
                                if(studentVOS != null){
                                    for(StudentVO studentVO:studentVOS){
                                        StudentDTO studentDTO = BeanFactory.getBean("studentDTO",StudentDTO.class);
                                        studentDTO.clone(studentVO);
                                        studentDTOS.add(studentDTO);
                                    }
                                }
                                teamDTO.clone(teamVO);
                                teamDTOS.add(teamDTO);
                                teamStudentList.add(teamDTO);
                                teamStudentList.add(studentDTOS);
                            }
                        }

                    }

                }
            }
            jsonObject.put("result", "success");
            jsonObject.put("teamBeans", teamStudentList);
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
