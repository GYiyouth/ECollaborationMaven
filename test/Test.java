import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.domain.*;

import java.util.List;

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

        //我用2号学生新建一个team，然后让这个team承接第1，2号project

        StudentVO userVO = session.get(StudentVO.class, 2);
        TeamVO teamVO = context.getBean("teamVO", TeamVO.class);
        teamVO.setCreatorStudentVO(userVO);
        ProjectVO projectVO1 = session.get(ProjectVO.class, 1);
        ProjectVO projectVO2 = session.get(ProjectVO.class, 2);

        teamVO.getProjectVOSet().add(projectVO1);
        teamVO.getProjectVOSet().add(projectVO2);

        session.save(teamVO);
        //通过测试
        transaction.commit();
        session.close();
        sf.close();
    }
}
