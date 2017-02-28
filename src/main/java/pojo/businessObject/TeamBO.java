package pojo.businessObject;

import net.sf.json.JSONObject;
import org.apache.struts2.components.Bean;
import org.springframework.context.ApplicationContext;
import pojo.DAO.TeamDAO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.Time;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by GR on 2017/2/26.
 */
public class TeamBO {

    /**
     * 创建团队
     *
     * @param teamName
     * @param description
     * @param memberMax
     * @param session
     * @return TeamVO
     */
    public TeamVO createTeam(String teamName, String description, Integer memberMax, Map<String, Object> session) {
        //考虑到创建团队可以什么信息都不填，故不作判空
        ApplicationContext context = BeanFactory.getApplicationContext();
        TeamDAO teamDAO = context.getBean("teamDAO", TeamDAO.class);
        TeamVO teamVO = context.getBean("teamVO", TeamVO.class);
        StudentVO studentVO = (StudentVO) session.get("studentVO");
        Time time = context.getBean("time", Time.class);
        teamVO.setCreateDate(time.getCurrentTime());
        teamVO.setCreatorStudentVO(studentVO);
        teamVO.setTeamName(teamName);
        teamVO.setDescription(description);
        teamVO.setMemberMax(memberMax);

        return teamDAO.createTeam(teamVO, studentVO);
    }

    /**
     * 申请加入团队
     *
     * @param teamId
     * @param session
     * @return
     */
    public String applyJoinTeam(Integer teamId, Map<String, Object> session) {
        if (teamId == null || teamId.equals("")) {
            System.out.println("teamId is  null---" + this.getClass() + "applyJoinTeam()");
            return "fail";
        } else {
            ApplicationContext context = BeanFactory.getApplicationContext();
            TeamDAO teamDAO = context.getBean("teamDAO", TeamDAO.class);
            UserVO senderUserVO = (UserVO) session.get("userVO");
            UserVO receiverUserVO = teamDAO.getLeaderStudentVOByTeamId(teamId);
            TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
            return teamDAO.applyJoinTeam(senderUserVO, receiverUserVO, teamVO);
        }
    }

    /**
     * 获取我加入的团队
     * @param session
     * @return
     */
    public JSONObject getMyJoinTeams(Map<String, Object> session) {
        TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO", TeamDAO.class);
        ArrayList<TeamDTO> teamDTOS = BeanFactory.getApplicationContext().getBean("arrayList", ArrayList.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
        UserVO userVO = (UserVO) session.get("userVO");
        if (userVO != null) {
            ArrayList<TeamVO> teamVOS = teamDAO.getMyJoinTeamsByStudentId(userVO.getId());
            if (teamVOS == null) {
                System.out.println("teamVOS==null||teamVOS.size()==0---" + this.getClass() + "---getMyJoinTeams()");
                return null;
            } else {
                if(teamVOS.size() != 0){
                    //已经加入团队
                    TeamDTO teamDTO = BeanFactory.getApplicationContext().getBean("teamDTO", TeamDTO.class);
                    for (TeamVO teamVO : teamVOS) {
                        teamDTO.clone(teamVO);
                        teamDTOS.add(teamDTO);
                    }
                }
                jsonObject.put("result", "success");
                jsonObject.put("teamBeans", teamDTOS);
                return jsonObject;
            }
        }else{
            System.out.println("ERROR:userVO == null---"+this.getClass()+"---getMyJoinTeams()");
            return null;
        }
    }
}
