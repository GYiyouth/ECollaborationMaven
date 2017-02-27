package pojo.valueObject.BO;

import org.springframework.context.ApplicationContext;
import pojo.valueObject.DAO.TeamDAO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.Time;

import java.util.Map;

/**
 * Created by GR on 2017/2/26.
 */
public class TeamBO {

    /**
     * 创建团队
     * @param teamName
     * @param description
     * @param memberMax
     * @param session
     * @return TeamVO
     */
    public TeamVO createTeam(String teamName, String description, Integer memberMax, Map<String,Object> session){
        //考虑到创建团队可以什么信息都不填，故不作判空
        ApplicationContext context = BeanFactory.getApplicationContext();
        TeamDAO teamDAO = context.getBean("teamDAO",TeamDAO.class);
        TeamVO teamVO = context.getBean("teamVO", TeamVO.class);
        StudentVO studentVO = (StudentVO) session.get("studentVO");
        Time time = context.getBean("time",Time.class);
        teamVO.setCreateDate(time.getCurrentTime());
        teamVO.setCreatorStudentVO(studentVO);
        teamVO.setTeamName(teamName);
        teamVO.setDescription(description);
        teamVO.setMemberMax(memberMax);

        return teamDAO.createTeam(teamVO,studentVO);
    }

    public String applyJoinTeam(Integer teamId, Map<String, Object> session){
        if(teamId==null||teamId.equals("")){
            System.out.println("teamId is  null---BO/TeamBO/applyJoinTeam()");
            return "fail";
        }else{
            ApplicationContext context = BeanFactory.getApplicationContext();
            TeamDAO teamDAO = context.getBean("teamDAO",TeamDAO.class);
            UserVO senderUserVO = (UserVO) session.get("userVO");
            UserVO receiverUserVO = (UserVO) teamDAO.getLeaderStudentVOByTeamId(teamId);
            TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
            return teamDAO.applyJoinTeam(senderUserVO,receiverUserVO,teamVO);
        }
    }
}
