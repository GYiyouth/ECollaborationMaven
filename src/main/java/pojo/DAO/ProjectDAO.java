package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;

/**
 * Created by geyao on 2017/3/1.
 */
public class ProjectDAO {

    public ProjectVO getProjectVO(Integer id) throws Exception{
        if (id == null || id < 0)
            return null;
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            return session.get(ProjectVO.class, id);
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
    }
}
