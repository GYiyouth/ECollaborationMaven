import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.DAO.PlanDAO;
import pojo.valueObject.assist.*;
import pojo.valueObject.domain.*;
import tool.BeanFactory;

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

        //测试，新建一个plan，然后加给1项目，2学生
//        StudentVO studentVO = session.get(StudentVO.class, 2);
//        PlanVO planVO = context.getBean("planVO", PlanVO.class);
//        planVO.setTitle("这是一个测试的计划");
//        planVO.setContent("测试，新建一个plan，然后加给1项目，2学生");
//        planVO.setStudentVO(studentVO);
//        session.save(planVO);
//        ProjectVO projectVO = session.get(ProjectVO.class, 1);
//        StudentProjectPlanVO studentProjectPlanVO = context.getBean("studentProjectPlanVO", StudentProjectPlanVO.class);
//        studentProjectPlanVO.setStudentVO(studentVO);
//        studentProjectPlanVO.setPlanVO(planVO);
//        studentProjectPlanVO.setProjectVO(projectVO);
//
//        session.save(studentProjectPlanVO);
        PlanVO planVO = BeanFactory.getBean("planVO", PlanVO.class);
        planVO.setStudentVO(session.get(StudentVO.class, 2));
        PlanDAO planDAO = BeanFactory.getBean("planDAO", PlanDAO.class);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        //通过测试
        transaction.commit();
        session.close();
        sf.close();
    }
}
