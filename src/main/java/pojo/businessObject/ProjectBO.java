package pojo.businessObject;

//import com.sun.istack.internal.Nullable;
import javafx.application.Application;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.*;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.assist.ProjectAccessTypeVO;
import pojo.valueObject.assist.TeamProjectAccessVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
@Service
@Transactional
public class ProjectBO {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private ApplicationDAO applicationDAO;

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    /**
     * 添加任务，在session里添加projectVO，如果是老师新建，则还会改动教师VO中的projectSet
     * @param projectVO
     * @param session
     * @return
     * @throws Exception
     */
    public ProjectVO addProject(ProjectVO projectVO, Map session) throws Exception{
        if (projectVO == null || session ==null){
            return null;
        }

        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        try {
            projectDAO.addProjectVO(projectVO);
            if (session.containsKey("projectVO"))
                session.remove("projectVO");
            session.put("projectVO", projectVO);

            //老师新建项目以后，要把它的ProjectSet也动一下
            if (session.containsKey("teacherVO")){
                TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
                teacherVO.getProjectVOSet().add(projectVO);
            }
            return projectVO;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询项目
     * 如果是学生，会先查询他的team，再根据team来完成找项目
     * 如果是教师，会返回teacherVO中的projectSet
     * session里放置projectVOList
     * @param userVO
     * @param grade
     * @param session
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getMyProjectVOList(
            UserVO userVO, Integer grade,  Map session) throws Exception{
        if (userVO == null || session == null)
            throw new NullPointerException("userVO == null || session == null");
        if (userVO.getRole().equals("teacher")){
            return getTeacherProjectVOList((TeacherVO)userVO, session, grade);
        }

        ArrayList<ProjectVO> arrayList = new ArrayList<>();
//        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        StudentVO studentVO = (StudentVO) userVO;
        System.out.println(studentVO);
        arrayList = projectDAO.getStudentProjectVOList(studentVO);

        String sessionProperty = "projectVOList";
        if (session.containsKey(sessionProperty))
            session.remove(sessionProperty);
        session.put(sessionProperty, arrayList);

        return arrayList;
    }

    /**
     * 根据TeacherVO里的ProjectSet得到projectList
     * 加入session：projectVOList
     * @param teacherVO
     * @param session
     * @param grade
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOList(TeacherVO teacherVO,
                                                       Map session, Integer grade)throws Exception{
        if (teacherVO == null || session == null)
            throw new  NullPointerException("teacherVO == null || session == null");
        ArrayList<ProjectVO> arrayList = new ArrayList<>();
        Iterator iterator = teacherVO.getProjectVOSet().iterator();
        String sessionProperty = "projectVOList";
        if ( grade == null){
            while (iterator.hasNext()){
                arrayList.add( (ProjectVO) iterator.next() );
            }
            return arrayList;
        }else {
            ProjectVO projectVO = (ProjectVO) iterator.next();
            if (projectVO.getGrade() != null
                    && projectVO.getGrade().equals(grade)){
                arrayList.add(projectVO);
            }
        }

        if (session.containsKey(sessionProperty)){
            session.remove(sessionProperty);
        }
        session.put(sessionProperty, arrayList);

        return arrayList;
    }

    /**
     * 团队申请项目
     * 消息会涉及，团队中的所有人，项目的老师
     * @param teamId
     * @param projectId
     * @throws Exception
     */
    public void applyProject(Integer teamId, Integer projectId) throws Exception{

//        ApplicationVO applicationVO = BeanFactory.getBean("applicationVO", ApplicationVO.class);
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        projectDAO.applyProject(teamId, projectId);
    }


    /**
     * 根据Id查询ProjectVO
     * @param projectId
     * @return
     * @throws Exception
     */
    public ProjectVO getProjectVO(Integer projectId) throws Exception{
        if (projectId != null && projectId > 0 )
                return projectDAO.getProjectVO(projectId);
        else
            throw new NullPointerException("projectId为空");
    }

    /**
     * 更新ProjectVO
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ProjectVO updateProjectVO(ProjectVO projectVO) throws Exception{
        if (projectVO != null)
            return projectDAO.updateProjectVO(projectVO);
        else
            throw new NullPointerException("ProjectVO为空");
    }

    /**
     * 辞退某个团队，工程实践项目，操作人仅可为老师，个人兴趣项目，操作人可为创建人
     * @param userVO
     * @param teamId
     * @param projectId
     * @return
     */
    public boolean fireTeam(UserVO userVO, Integer teamId, Integer projectId) throws Exception{
        ProjectVO projectVO = projectDAO.getProjectVO(projectId);
        if (projectVO == null) return false;
        TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
        if (teamVO == null) return false;
        TeamProjectVO teamProjectVO = projectDAO.getTeamProjectVO(teamVO, projectVO);
        if (teamProjectVO == null) return false;
        List<TeamProjectAccessVO> teamProjectAccessVOList = projectDAO.getTeamProjectAccessVO(teamVO, projectVO);
        if (projectVO.getPriority().equals(1)){
            //工程实践项目
            if (projectVO.getTeacherVO().getId().equals(userVO.getId())){
                //操作人必须是老师
                //删除已有评价，删除项目团队
                projectDAO.delete(teamProjectAccessVOList);
                projectDAO.delete(teamProjectVO);
                return true;
            }
        }else {
            if (projectVO.getPriority().equals(2)){ // 是学生兴趣项目
                if (projectVO.getCreatorUserVO().getId().equals(userVO.getId())){
                    //操作人是学生创始人
                    projectDAO.delete(teamProjectAccessVOList);
                    projectDAO.delete(teamProjectVO);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除项目，要涉及到以下关系表：
     * project_accessType
     * project_task
     * student_project_file
     * student_project_plan
     * team_project
     * team_project_access
     * @param projectId
     * @throws Exception
     */
    public void deleteProjectVO(Integer projectId) throws Exception{
        if (projectId == null)
            throw new NullPointerException("projectId为空");
        ProjectVO projectVO = projectDAO.getProjectVO(projectId);
        if (projectVO == null)
            throw new NullPointerException("projectId为空");

        List<TeamVO> teamVOList = teamDAO.getTeamVOByProjectVO(projectVO);
        Iterator teamVOIterator = teamVOList.iterator();
        while (teamVOIterator.hasNext()) {
            TeamVO teamVO = (TeamVO) teamVOIterator.next();
            projectDAO.delete( projectDAO.getTeamProjectAccessVO(teamVO, projectVO) );
        }
        projectDAO.deleteTeamProjectAccess(projectVO);
        projectDAO.deleteProjectAccessType(projectVO);
        projectDAO.deleteFileByProjectVO(projectVO);
        projectDAO.deletePlanByProjectVO(projectVO);
        projectDAO.deleteTaskByProjectVO(projectVO);
        projectDAO.deleteTeamProjectByProject(projectVO);
        projectDAO.delete(projectVO);
    }

    /**
     * 接受团队对项目的申请
     * @param applicationId
     * @throws Exception
     */
    public void acceptApplyProjectApplication(Integer applicationId) throws Exception{

        if(applicationId == null ){
            throw new NullPointerException(" applicationId is null---"+this.getClass()+"----acceptApplyProjectApplication()");
        }else{
            MessageDAO messageDAO = BeanFactory.getBean("messageDAO",MessageDAO.class);
            ApplicationVO applicationVO = applicationDAO.getApplicationVOById(applicationId);
            MessageReceiverVO messageReceiverVO = messageDAO.getMessageReceiverVOByMessageVOAndReceiverVO(applicationVO.getMessageVO(),applicationVO.getHandlerUserVO());
            if(messageReceiverVO!=null){
                // 1. 删除消息-接受表中记录
                messageDAO.deleteMessageReceiver(messageReceiverVO);
            }
            // 2. 删除申请表记录
            applicationDAO.deleteApplication(applicationVO);
            // 3. 删除申请的消息
            messageDAO.deleteMessage(applicationVO.getMessageVO());

            // 4. 增加接受之后发给申请者的消息
            MessageVO messageVO = tool.MessageMould.acceptJoinProjectMessageVOMould(applicationVO.getAffectedUserVO(),applicationVO.getProjectVO());
            messageDAO.save(messageVO);

            // 5. 保存消息-接受者记录
            MessageReceiverVO messageReceiverVO1 = BeanFactory.getBean("messageReceiverVO",MessageReceiverVO.class);
            messageReceiverVO1.setMessageVO(messageVO);
            messageReceiverVO1.setReceiverUserVO(applicationVO.getAffectedUserVO());
            messageReceiverVO1.setReadFlag(false);
            messageDAO.save(messageReceiverVO1);

            // 6. 保存进团队-项目表
            TeamProjectVO teamProjectVO = BeanFactory.getBean("teamProjectVO",TeamProjectVO.class);
            teamProjectVO.setTeamVO(applicationVO.getTeamVO());
            teamProjectVO.setProjectVO(applicationVO.getProjectVO());
            teamDAO.addTeamProject(teamProjectVO);
        }

    }

    /**
     * 拒绝团队对项目的申请
     * @param applicationId
     * @throws Exception
     */
    public void refuseApplyProjectApplication(Integer applicationId) throws Exception{

        if(applicationId == null ){
            throw new NullPointerException("applicationId is null---"+this.getClass()+"----acceptApplyProjectApplication()");
        }else{
            MessageDAO messageDAO = BeanFactory.getBean("messageDAO",MessageDAO.class);
            ApplicationVO applicationVO = applicationDAO.getApplicationVOById(applicationId);
            MessageReceiverVO messageReceiverVO = messageDAO.getMessageReceiverVOByMessageVOAndReceiverVO(applicationVO.getMessageVO(),applicationVO.getHandlerUserVO());
            if(messageReceiverVO!=null){
                // 1. 删除消息-接受表中记录
                messageDAO.deleteMessageReceiver(messageReceiverVO);
            }
            // 2. 删除申请表记录
            applicationDAO.deleteApplication(applicationVO);
            System.out.println("删除了");
            // 3. 删除申请的消息
            messageDAO.deleteMessage(applicationVO.getMessageVO());


            // 4. 增加拒绝之后发给申请者的消息
            MessageVO messageVO = tool.MessageMould.refuseJoinProjectMessageVOMould(applicationVO.getAffectedUserVO(),applicationVO.getProjectVO());
            messageDAO.save(messageVO);

            // 5. 保存消息-接受者记录
            MessageReceiverVO messageReceiverVO1 = BeanFactory.getBean("messageReceiverVO",MessageReceiverVO.class);
            messageReceiverVO1.setMessageVO(messageVO);
            messageReceiverVO1.setReceiverUserVO(applicationVO.getAffectedUserVO());
            messageReceiverVO1.setReadFlag(false);
            messageDAO.save(messageReceiverVO1);
//            TeamProjectVO teamProjectVO = BeanFactory.getBean("teamProjectVO",TeamProjectVO.class);
//            teamProjectVO.setTeamVO(applicationVO.getTeamVO());
//            teamProjectVO.setProjectVO(applicationVO.getProjectVO());
//            teamDAO.addTeamProject(teamProjectVO);
        }

    }


//    public TeamProjectVO getTeamProjectByTeamAndProject(TeamVO teamVO, ProjectVO projectVO){
//        if(teamVO == null || projectVO == null){
//            throw new NullPointerException("projectVO/teamVO is null ---"+this.getClass().getName() +"----getTeamProjectByTeamAndProject()");
//        }else{
//            List<TeamProjectVO> list =
//        }
//        return null;
//    }


    public ArrayList<ProjectVO> getTeacherProjectList(UserVO teacherVO) throws Exception {
        if(teacherVO == null){
            throw new NullPointerException("teacherVO is null ---"+this.getClass().getName()+"----getTeacherProjectList()");
        }else{
            return projectDAO.getTeacherProjectVOList(teacherVO);
        }

    }


}
