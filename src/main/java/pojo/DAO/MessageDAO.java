package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GR on 2017/2/27.
 */
public class MessageDAO {

    /**
     * 获取未读消息VOList
     * 将3个readFlag处理了（）
     * @param userVO
     * @return
     */
    public ArrayList<MessageVO> getUnreadMessageVO(UserVO userVO){
        ArrayList<MessageVO> arrayList = new ArrayList<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
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
        }finally {
            session.close();
        }
    }
}
