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
//        Transaction transaction = session.beginTransaction();
        try{
//            return session.get(ProjectVO.class, id);
            ProjectVO projectVO = session.get(ProjectVO.class, id);
            return projectVO;
        }catch (Exception e){
            e.printStackTrace();
//            transaction.rollback();
            throw e;
        }finally {
//            session.close();
        }
    }

    /**
     * 增加项目，不处理与team的关系表
     * @param projectVO
     * @return
     */
    public ProjectVO addProjectVO(ProjectVO projectVO) throws Exception{
        if (projectVO == null)
            return null;
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(projectVO);
            transaction.commit();
            return projectVO;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }
}
