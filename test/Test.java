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

        //我取1号老师，新建一个任务，然后加给项目123
        TeacherVO teacherVO = session.get(TeacherVO.class, 1);
        TaskVO taskVO = context.getBean("taskVO", TaskVO.class);
        taskVO.setTitle("testTask");
        taskVO.setContent("我取1号老师，新建一个任务，然后加给项目123");
        taskVO.setCreatorTeacherVO(teacherVO);
        ProjectVO projectVO1 = session.get(ProjectVO.class, 1);
        ProjectVO projectVO2 = session.get(ProjectVO.class, 2);
        ProjectVO projectVO3 = session.get(ProjectVO.class, 3);
        taskVO.getProjectVOSet().add(projectVO1);
        taskVO.getProjectVOSet().add(projectVO2);
        taskVO.getProjectVOSet().add(projectVO3);
        session.save(taskVO);
        transaction.commit();
        session.close();
        sf.close();
    }
}
