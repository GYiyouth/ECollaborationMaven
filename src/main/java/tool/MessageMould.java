package tool;

import org.springframework.context.ApplicationContext;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
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
            messageVO.setContent("又有新人申请加入您的团队！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("1999-01-01 00:00:00");
//            messageVO.setReadFlag(0);
            return messageVO;
        }
    }

    public static MessageVO acceptJoinTeamMessageVOMould(UserVO userVO, TeamVO teamVO){
        if(userVO==null||teamVO==null){
            System.out.println("userVO/teamVO为空！！---tool/MessageMould/acceptJoinTeamMessageVOMould()");
            return null;
        }else{
            MessageVO messageVO = BeanFactory.getApplicationContext().getBean("messageVO",MessageVO.class);
            //设置消息内容
            messageVO.setTitle("加入团队申请通过！");
            messageVO.setContent("团队："+teamVO.getTeamName()+"组长通过了您的加团请求！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("2020-01-01 00:00:00");
            messageVO.setReadFlag(0);
            return messageVO;
        }
    }

    public static MessageVO refuseJoinTeamMessageVOMould(UserVO userVO, TeamVO teamVO){
        if(userVO==null||teamVO==null){
            System.out.println("userVO/teamVO为空！！---tool/MessageMould/refuseJoinTeamMessageVOMould()");
            return null;
        }else{
            MessageVO messageVO = BeanFactory.getApplicationContext().getBean("messageVO",MessageVO.class);
            //设置消息内容
            messageVO.setTitle("加入团队申请没有通过！");
            messageVO.setContent("团队："+teamVO.getTeamName()+"组长拒绝了您的加团请求！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("2020-01-01 00:00:00");
            messageVO.setReadFlag(0);
            return messageVO;
        }
    }


    public static MessageVO acceptJoinProjectMessageVOMould(UserVO userVO, ProjectVO projectVO){
        if(userVO==null||projectVO==null){
            System.out.println("userVO/projectVO为空！！---tool/MessageMould/acceptJoinTeamMessageVOMould()");
            return null;
        }else{
            MessageVO messageVO = BeanFactory.getApplicationContext().getBean("messageVO",MessageVO.class);
            //设置消息内容
            messageVO.setTitle("加入项目申请通过！");
            messageVO.setContent("项目所有者："+projectVO.getCreatorUserVO().getName()+"  通过了您的申请项目请求！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("2020-01-01 00:00:00");
            messageVO.setReadFlag(1);
            return messageVO;
        }
    }

    public static MessageVO refuseJoinProjectMessageVOMould(UserVO userVO, ProjectVO projectVO){
        if(userVO==null||projectVO==null){
            System.out.println("userVO/projectVO 为空！！---tool/MessageMould/refuseJoinTeamMessageVOMould()");
            return null;
        }else{
            MessageVO messageVO = BeanFactory.getApplicationContext().getBean("messageVO",MessageVO.class);
            //设置消息内容
            messageVO.setTitle("加入团队申请没有通过！");
            messageVO.setContent("项目所有者："+projectVO.getCreatorUserVO().getName()+"拒绝了您的申请项目请求！");
            messageVO.setCreateTime(Time.getCurrentTime());
            messageVO.setSenderUserVO(userVO);
            messageVO.setDeadDate("2020-01-01 00:00:00");
            messageVO.setReadFlag(1);
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
