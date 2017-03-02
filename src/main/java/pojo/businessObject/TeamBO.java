package pojo.businessObject;

import net.sf.json.JSONObject;
import org.apache.struts2.components.Bean;
import org.springframework.context.ApplicationContext;
import pojo.DAO.*;
import pojo.valueObject.DTO.ECFileDTO;
import pojo.valueObject.DTO.PlanDTO;
import pojo.valueObject.DTO.StudentDTO;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.domain.PlanVO;
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
    public JSONObject createTeam(String teamName, String description, Integer memberMax, Map<String, Object> session) throws Exception{
        //考虑到创建团队可以什么信息都不填，故不作判空
        ApplicationContext context = BeanFactory.getApplicationContext();
        JSONObject jsonObject = context.getBean("jsonObject",JSONObject.class);
        TeamDTO teamDTO = context.getBean("teamDTO",TeamDTO.class);
        TeamDAO teamDAO = context.getBean("teamDAO", TeamDAO.class);
        TeamVO teamVO = context.getBean("teamVO", TeamVO.class);
        StudentVO studentVO = (StudentVO) session.get("studentVO");
        teamVO.setCreateDate(Time.getCurrentTime());
        teamVO.setCreatorStudentVO(studentVO);
        teamVO.setTeamName(teamName);
        teamVO.setDescription(description);
        teamVO.setMemberMax(memberMax);
        try {
            teamVO = teamDAO.createTeam(teamVO,studentVO);
            if(teamVO == null){
                return null;
            }else{
                teamDTO.clone(teamVO);
                jsonObject.put("result","success");
                jsonObject.put("teamBean",teamDTO);
                return jsonObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 申请加入团队
     *
     * @param teamId
     * @param session
     * @return
     */
    public JSONObject applyJoinTeam(Integer teamId, Map<String, Object> session) throws Exception{
        if (teamId == null || teamId.equals("")) {
            System.out.println("teamId is  null---" + this.getClass() + "applyJoinTeam()");
            return null;
        } else {
            ApplicationContext context = BeanFactory.getApplicationContext();
            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            TeamDAO teamDAO = context.getBean("teamDAO", TeamDAO.class);
            UserVO senderUserVO = (UserVO) session.get("studentVO");
            try {
                UserVO receiverUserVO = teamDAO.getLeaderStudentVOByTeamId(teamId);
                TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
                String result = teamDAO.applyJoinTeam(senderUserVO, receiverUserVO, teamVO);
                if(result.equals("success")){
                    jsonObject.put("result", "success");
                    return jsonObject;
                }else{
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 获取我加入的团队
     * @param session
     * @return
     */
    public JSONObject getMyJoinTeams(Map<String, Object> session) throws Exception{
        TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO", TeamDAO.class);
        ArrayList<TeamDTO> teamDTOS = BeanFactory.getApplicationContext().getBean("arrayList", ArrayList.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
        try {

            System.out.println("session是" + session);


            UserVO userVO = (StudentVO) session.get("studentVO");
            if (userVO != null) {
                ArrayList<TeamVO> teamVOS = teamDAO.getMyJoinTeamsByStudentId(userVO.getId());
                if (teamVOS == null) {
                    System.out.println("teamVOS==null||teamVOS.size()==0---" + this.getClass() + "---getMyJoinTeams()");
                    return null;
                } else {
                    if (teamVOS.size() != 0) {
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
            } else {
                System.out.println("ERROR:userVO == null---" + this.getClass() + "---getMyJoinTeams()");
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据teamId获取team信息
     * @param teamId
     * @return
     */
    public JSONObject getTeamInfoByTeamId(Integer teamId) throws Exception{
        if(teamId==null||teamId.equals("")){
            System.out.println("ERROR:teamId is null---"+this.getClass()+"---getTeamInfoByTeamId()");
            return null;
        }else{
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO", TeamDAO.class);
            try {
                TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
                if (teamVO == null) {
                    System.out.println("Error:teamVO is null---" + this.getClass() + "---getTeamInfoByTeamId()");
                    return null;
                } else {
                    JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
                    TeamDTO teamDTO = BeanFactory.getApplicationContext().getBean("teamDTO", TeamDTO.class);
                    teamDTO.clone(teamVO);
                    jsonObject.put("result", "success");
                    jsonObject.put("teamBean", teamDTO);
                    return jsonObject;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 接受某人的加团申请
     * @param applicationId
     * @return
     */
    public JSONObject acceptJoinApplication(Integer applicationId)throws Exception{
        if(applicationId==null||applicationId.equals("")){
            System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---acceptJoinApplication()");
            return null;
        }else{
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO",TeamDAO.class);
            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            try {
                String result = teamDAO.acceptJoinApplication(applicationId);

                if (result.equals("success")) {
                    jsonObject.put("result", "success");
                    return jsonObject;
                } else {
                    System.out.println("ERROR:result is fail!!!---" + this.getClass() + "---acceptJoinApplication()");
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }


    /**
     * 拒绝某人加入团队
     * @param applicationId
     * @return
     * @throws Exception
     */
    public JSONObject refuseJoinApplication(Integer applicationId) throws Exception{
        if(applicationId==null||applicationId.equals("")){
            System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---refuseJoinApplication()");
            return null;
        }else{
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO",TeamDAO.class);
            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            try {
                String result = teamDAO.refuseJoinApplication(applicationId);
                if (result.equals("success")) {
                    jsonObject.put("result", "success");
                    return jsonObject;
                } else {
                    System.out.println("ERROR:result is fail!!!---" + this.getClass() + "---refuseJoinApplication()");
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 获取team在某个项目的所有信息，包括代码、文件数、学生、计划
     * @param teamId
     * @param projectId
     * @return
     */
    public JSONObject getTeamCodeECFileInfo(Integer teamId, Integer projectId) throws Exception{
        if(teamId==null||teamId.equals("")||projectId==null||projectId.equals("")) {
            System.out.println("ERROR:teamId/projectId is null!!!---" + this.getClass() + "---getTeamCodeECFileInfo()");
        }else{
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO",TeamDAO.class);
//            StudentDAO studentDAO = BeanFactory.getApplicationContext().getBean("studentDAO",StudentDAO.class);
            CodeDAO codeDAO = BeanFactory.getApplicationContext().getBean("codeDAO",CodeDAO.class);
            ECFileDAO ecFileDAO = BeanFactory.getApplicationContext().getBean("ecFileDAO", ECFileDAO.class);
            PlanDAO planDAO = BeanFactory.getApplicationContext().getBean("planDAO",PlanDAO.class);

            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            ArrayList<Integer> codeSums = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);    //代码行数集合
            ArrayList<Integer> fileSums = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
            ArrayList<ArrayList> planBeansAllStudents = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
            try{
                ArrayList<StudentVO> studentVOS = teamDAO.getStudentVOSByTeamId(teamId);
                ArrayList<StudentDTO> studentDTOS = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
                if(studentVOS == null){
                    System.out.println("ERROR:studentVOS is null!!!---" + this.getClass() + "---getTeamCodeECFileInfo()");
                    return null;
                }else{
                    for(StudentVO studentVO:studentVOS){
                        StudentDTO studentDTO = BeanFactory.getApplicationContext().getBean("studentDTO",StudentDTO.class);
                        studentDTO.clone(studentVO);
                        studentDTOS.add(studentDTO);
                        Integer tempCode = codeDAO.getCodeSumByStudentIdProjectId(studentVO.getId(),projectId);
                        if(tempCode==null){
                            codeSums.add(0);
                        }else{
                            codeSums.add(tempCode);
                        }
                        Integer tempFile = ecFileDAO.getECFileSumByStudentId(studentVO.getId());
                        if(tempFile==null){
                            fileSums.add(0);
                        }else{
                            fileSums.add(tempCode);
                        }
                        ArrayList<PlanVO> tempPlanVOS = planDAO.getPlanVOList(studentVO,projectId);
                        ArrayList<PlanDTO> planDTOS = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
                        if(tempPlanVOS == null||tempPlanVOS.isEmpty()){
                            System.out.println("ERROR:teamPlanVOS is null---"+this.getClass()+"---getTeamCodeECFileInfo()");
                            planBeansAllStudents.add(null);
                        }else{
                            for(PlanVO planVO:tempPlanVOS){
                                PlanDTO planDTO = BeanFactory.getApplicationContext().getBean("planDTO",PlanDTO.class);
                                planDTO.clone(planVO);
                                planDTOS.add(planDTO);
                            }
                            planBeansAllStudents.add(planDTOS);
                        }
                    }
                    jsonObject.put("studentBeans", studentDTOS);
                    jsonObject.put("codeSums", codeSums);
                    jsonObject.put("fileSum", fileSums);
                    jsonObject.put("planBeansAllStudents", planBeansAllStudents);
                    jsonObject.put("result", "success");
                }
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        }
        return null;
    }

}
