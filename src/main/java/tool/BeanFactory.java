package tool;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by GR on 2017/2/26.
 */
public class BeanFactory {
    public static ApplicationContext getApplicationContext(){
        return new ClassPathXmlApplicationContext("ApplicationContext.xml");
    }
    public static SessionFactory getSessionFactory(){
        return getApplicationContext().getBean("sessionFactory", SessionFactory.class);
    }
}
