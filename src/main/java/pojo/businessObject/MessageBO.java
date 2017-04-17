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
        messageDAO.save(messageVO);
    }
}
