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

        MessageReceiverVO messageReceiverVO = session.get(MessageReceiverVO.class, 11);
        MessageVO messageVO = session.get(MessageVO.class, 6);
        UserVO userVO = session.get(UserVO.class, 13);

        userVO.getMessageVOSet().add(messageVO);
        System.out.println(messageReceiverVO);
        //这里其实不需要session.save什么的，就可以保存到连接表中了。
//        System.out.println(session.createCriteria(MessageReceiverVO.class).list());
//        session.createFilter(MessageReceiverVO.class).list()
        transaction.commit();
        session.close();
        sf.close();
    }
}
