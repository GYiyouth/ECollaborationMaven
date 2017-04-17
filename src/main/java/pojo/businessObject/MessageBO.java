package pojo.businessObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.*;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;
import tool.Time;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by geyao on 2017/3/3.
 */
@Transactional
@Service
public class MessageBO {
    @Autowired
    private TeamDAO teamDAO;
    @Resource
    private ProjectDAO projectDAO;
    @Resource
    private TeacherDAO teacherDAO;
    @Resource
    private StudentVO studentVO;
    @Resource
    private MessageDAO messageDAO;
    @Autowired
    private UserDAO userDAO;

    /**
     * 读取信息，在DAO里同时处理了三种表的action
     * @param userVO
     * @return
     * @throws Exception
     */
    public ArrayList<MessageVO> readMessage(UserVO userVO) throws Exception{
        MessageDAO messageDAO = BeanFactory.getBean("messageDAO", MessageDAO.class);
        return messageDAO.getUnreadMessageVO(userVO);
    }

    /**
     * 修改项目，发送消息
     * @param projectVO
     */
    public void updateProjectVO(ProjectVO projectVO, UserVO sender) throws Exception{
        if (projectVO == null){
            return;
        }
        if (sender == null)
            return;

        List<TeamVO> teamVOList =
                teamDAO.getTeamVOByProjectVO(projectVO);

        List<UserVO> receiver = new ArrayList<>();

        for (TeamVO teamVO : teamVOList){
            receiver.addAll(teamDAO.getStudentVOSByTeamId(teamVO.getId()));
        }

        receiver.add(teacherDAO.getTeacherVOByProjectVO(projectVO));

        MessageVO messageVO = BeanFactory.getBean("messageVO", MessageVO.class);

        messageVO.setTitle(projectVO.getName() + "项目信息改动");
        messageVO.setContent(projectVO.getName() + "项目信息发生了变动，请及时查看");
        messageVO.setContent(Time.getCurrentTime());
        messageVO.setSenderUserVO(sender);
        sendMessage(messageVO, receiver);
    }

    /**
     * 发送消息
     * 转成set，设置messageVO的readFlag，设置关系表
     * @param messageVO
     * @param receiverVOList
     */
    public void sendMessage(MessageVO messageVO, List<UserVO> receiverVOList) throws Exception{
        if (messageVO == null || receiverVOList == null)
            return;
        HashSet<UserVO>receiverSet = new HashSet<>();
        receiverSet.addAll(receiverVOList);
        messageVO.setReadFlag(receiverSet.size());
        messageDAO.save(messageVO);
        for (UserVO userVO : receiverSet){
            MessageReceiverVO messageReceiverVO = BeanFactory
                    .getBean("messageReceiverVO", MessageReceiverVO.class);

            messageReceiverVO.setMessageVO(messageVO);
            messageReceiverVO.setReadFlag(false);
            messageReceiverVO.setReceiverUserVO(userVO);
            userVO.setNewsFlag( (userVO.getNewsFlag() + 1) );
            //设置完了关系和修改UserVO的newsFlag
            userDAO.update(userVO);
            messageDAO.save(messageReceiverVO);
        }

    }

    /**
     * 辞退某个团队，给团队成员发送消息
     * @param teamId
     * @param projectId
     * @param sender
     * @throws Exception
     */
    public void fireTeam(Integer teamId, Integer projectId, UserVO sender) throws Exception{
        TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
        ProjectVO projectVO = projectDAO.getProjectVO(projectId);

        MessageVO messageVO = BeanFactory.getBean("messageVO", MessageVO.class);
        messageVO.setSenderUserVO(sender);
        messageVO.setTitle(teamVO.getTeamName() + " 团队已退出 " + projectVO.getName());
        messageVO.setContent("");
        messageVO.setCreateTime(Time.getCurrentTime());
        List<UserVO> receiver = new ArrayList<>();
        receiver.addAll( teamDAO.getStudentVOSByTeamId(teamVO.getId()) );
        receiver.add(sender);
        sendMessage(messageVO, receiver);
    }

    /**
     * 删除项目，复制了修改项目的代码
     * @param projectId
     * @param sender
     * @throws Exception
     */
    public void deleteProject(Integer projectId, UserVO sender) throws Exception{
        ProjectVO projectVO = projectDAO.getProjectVO(projectId);
        if (projectVO == null){
            return;
        }
        if (sender == null)
            return;

        List<TeamVO> teamVOList =
                teamDAO.getTeamVOByProjectVO(projectVO);

        List<UserVO> receiver = new ArrayList<>();

        for (TeamVO teamVO : teamVOList){
            receiver.addAll(teamDAO.getStudentVOSByTeamId(teamVO.getId()));
        }

        receiver.add(teacherDAO.getTeacherVOByProjectVO(projectVO));

        MessageVO messageVO = BeanFactory.getBean("messageVO", MessageVO.class);

        messageVO.setTitle(projectVO.getName() + "项目已被删除");
        messageVO.setContent(projectVO.getName() + "项目已被删除");
        messageVO.setContent(Time.getCurrentTime());
        messageVO.setSenderUserVO(sender);
        sendMessage(messageVO, receiver);
    }

    /**
     * 删除团队
     * @param teamId
     * @param sender
     * @throws Exception
     */
    public void deleteTeam(Integer teamId, UserVO sender) throws Exception{
        TeamVO teamVO = teamDAO.getTeamVOByTeamId(teamId);
        if (teamVO == null)
            return;
        List<UserVO> receiver = new ArrayList<>();
        receiver.addAll(teamDAO.getStudentVOSByTeamId(teamVO.getId()));
        receiver.add(sender);
        MessageVO messageVO = BeanFactory.getBean("messageVO", MessageVO.class);

        messageVO.setTitle(teamVO.getTeamName() + "团队已被删除");
        messageVO.setContent(teamVO.getTeamName() + "团队已被删除");
        messageVO.setContent(Time.getCurrentTime());
        messageVO.setSenderUserVO(sender);
        sendMessage(messageVO, receiver);
    }
}
