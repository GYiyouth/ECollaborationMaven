import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.domain.*;

/**
 * Created by geyao on 2017/2/19.
 */

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        SessionFactory sf = context.getBean("sessionFactory", SessionFactory.class);
        System.out.println("SesssionFactory是   " + sf);
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

        UserVO userVO1 = session.get(UserVO.class, 1);
        UserVO userVO2 = session.get(UserVO.class, 2);
        UserVO userVO17 = session.get(UserVO.class, 17);
        MessageVO messageVO = context.getBean(MessageVO.class);

        messageVO.setSenderUserVO(userVO2);
        messageVO.getReceiverUserVOSetSet().add(userVO1);
        messageVO.getReceiverUserVOSetSet().add(userVO17);
//        UserVO userVO = context.getBean(UserVO.class);
        UserVO userVO3 = context.getBean("userVO", UserVO.class);
        UserVO userVO4 = context.getBean("userVO", UserVO.class);
        session.save(messageVO);
//        System.out.println(session.createCriteria(MessageReceiverVO.class).list());
//        session.createFilter(MessageReceiverVO.class).list()
        transaction.commit();
        session.close();
        sf.close();
    }
}
