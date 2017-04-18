package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GR on 2017/2/27.
 */
public class MessageDAO {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 获取未读消息VOList
     * 将3个readFlag处理了（）
     * @param userVO
     * @return
     */
    public ArrayList<MessageVO> getUnreadMessageVO(UserVO userVO){
        ArrayList<MessageVO> arrayList = new ArrayList<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try{
            List mrList = session.createCriteria(MessageReceiverVO.class)
                    .add(Restrictions.eq("receiverUserVO", userVO))
                    .add(Restrictions.eq("readFlag", false))
                    .list();
            Iterator iterator = mrList.iterator();
            while (iterator.hasNext()){
                MessageReceiverVO messageReceiverVO = (MessageReceiverVO) iterator.next();
                //处理message_receiver的flag
                messageReceiverVO.setReadFlag(true);
                //还有message的，跟user的
                MessageVO messageVO = session.get(MessageVO.class,
                        messageReceiverVO.getMessageVO().getId());
                UserVO receiverUserVO = session.get(UserVO.class,
                        messageReceiverVO.getReceiverUserVO().getId());
                //message
                Integer messageReadFlag = messageVO.getReadFlag();
                if (messageReadFlag != null || messageReadFlag >0)
                    messageReadFlag--;
                else {
                    messageReadFlag = 0;
                }
                messageVO.setReadFlag(messageReadFlag);
                //user
                Integer receiverVO = receiverUserVO.getNewsFlag();
                if (receiverVO != null || receiverVO >0)
                    receiverVO--;
                else {
                    receiverVO = 0;
                }
                receiverUserVO.setNewsFlag(receiverVO);

                session.update(messageVO);
                session.update(receiverUserVO);
                session.update(messageReceiverVO);

                arrayList.add(messageVO);
            }
            transaction.commit();
            return arrayList;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 存储消息-接受者
     * @param messageReceiverVO
     * @throws Exception
     */
    public void save(MessageReceiverVO messageReceiverVO) throws Exception{
        if (messageReceiverVO == null)
            return;
        hibernateTemplate.save(messageReceiverVO);
    }

    /**
     * 存储消息
     * @param messageVO
     * @throws Exception
     */
    public void save(MessageVO messageVO) throws Exception{
        if (messageVO == null)
            return;
        hibernateTemplate.save(messageVO);
    }

    /**
     * 根据id获取MessageVO
     * @param messageId
     * @return
     * @throws Exception
     */
    public MessageVO getMessageVOByMessageId(Integer messageId) throws Exception{
        if(messageId == null){
            throw new NullPointerException("messageId is null---"+this.getClass()+"---getMessageVOByMessageId()");
        }else{
            return hibernateTemplate.get(MessageVO.class,messageId);
        }
    }





    /**
     * 删除消息表中记录
     * @param messageVO
     * @throws Exception
     */
    public void deleteMessage(MessageVO messageVO) throws  Exception{
        if(messageVO == null){
            throw new NullPointerException("messageVO is null---"+this.getClass()+"----deleteMessage()");
        }else{
            hibernateTemplate.delete(messageVO);
        }
    }

    /**
     * 从消息-接受者表中删除
     * @param messageReceiverVO
     * @throws Exception
     */
    public void deleteMessageReceiver(MessageReceiverVO messageReceiverVO) throws Exception{
        if(messageReceiverVO == null){
            throw new NullPointerException("messageReceiverVO is null---"+this.getClass()+"----deleteMessageReceiver()");
        }else{
            hibernateTemplate.delete(messageReceiverVO);
        }
    }


    /**
     * 根据接受者、消息号，获取消息-接受记录
     * @param messageVO
     * @param receiverVO
     * @return
     * @throws Exception
     */
    public MessageReceiverVO getMessageReceiverVOByMessageVOAndReceiverVO(MessageVO messageVO, UserVO receiverVO) throws  Exception{
        if(messageVO==null || receiverVO == null){
            throw new NullPointerException("messageVO/receiverVO is null---"+this.getClass()+"----getMessageReceiverVOByMessageVOAndReceiverVO()");
        }else{
            List list = hibernateTemplate.find("select mr from MessageReceiverVO mr where mr.messageVO = ? and mr.receiverUserVO = ?",messageVO,receiverVO);
            if(list.size()>=1){
                return (MessageReceiverVO) list.get(0);
            }else{
                return null;
            }
        }
    }
}
