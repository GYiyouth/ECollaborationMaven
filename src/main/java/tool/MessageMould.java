package tool;

import org.springframework.context.ApplicationContext;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;

/**
 * Created by GR on 2017/2/27.
 */
public class MessageMould {

    /**
     * 申请团队的消息模板
     * @param userVO
     * @return messageVO/null
     */
    public MessageVO applyJoinTeamMessageVOMould(UserVO userVO){
        if(userVO==null){
            System.out.println("userVO为空！！---tool/MessageMould/applyJoinTeamMould()");
            return null;
        }else{
            ApplicationContext context = BeanFactory.getApplicationContext();
            MessageVO messageVO = context.getBean("messageVO",MessageVO.class);
            //设置消息内容
            messageVO.setTitle("加入团队申请！");
            messageVO.setContent("又有新人社区宁加入您的团队！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("1999-01-01 00:00:00");
//            messageVO.setReadFlag(0);
            return messageVO;
        }
    }

//    public MessageVO applyJoinTeamMessageVOMould(Integer senderId, Integer teamId, Integer handlerId, Integer affectedId){
//        if(senderId==null||senderId.equals("")||teamId==null||teamId.equals("")||handlerId==null||handlerId.equals("")||affectedId==null||affectedId.equals("")){
//            System.out.println("senderId/teamId/handlerId/affectedId 为空！！---tool/MessageMould/applyJoinTeamMould()");
//            return null;
//        }else{
//
//        }
//        return null;
//    }
}