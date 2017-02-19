import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;

/**
 * Created by geyao on 2017/2/19.
 */

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        SessionFactory sf = context.getBean("sessionFactory", SessionFactory.class);
        System.out.println("SesssionFactory是" + sf);
        Session session = sf.openSession();
//        pojo.valueObject.domain.Test test = context.getBean("test", pojo.valueObject.domain.Test.class);
        Transaction transaction = session.beginTransaction();
        pojo.valueObject.domain.Test test = session.get(pojo.valueObject.domain.Test.class, 17);
        StudentVO studentVO = session.get(StudentVO.class, 17);
        UserVO userVO = session.get(UserVO.class, 17);


        userVO.setEmail("1111");
        studentVO.setEmail("2222");
        test.getStudentVO().setEmail("3333");
//        session.delete(userVO);
//        userVO.setId(20);
        transaction.commit();
        session.close();
        sf.close();
    }
}
