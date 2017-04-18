package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GR on 2017/2/26.
 */
@Repository
//@DependsOn(value = "sessionFactory")
public class UserDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    @Autowired
    private HibernateTemplate hibernateTemplate;

//    @Autowired
//    private HibernateTemplate hibernateTemplate;

    public UserVO getUser(int id){
        return sessionFactory.getCurrentSession().get(UserVO.class, 1);
    }

    /**
     * 根据用户名、密码获取登陆者信息
     * @param logName
     * @param passWord
     * @return UserVO/null
     */
    public UserVO getUserInfo(String logName,String passWord) throws Exception{
        List<UserVO> list =
                sessionFactory.getCurrentSession()
                .createQuery("from UserVO as user where user.logName = :logName and passWord = :passWord")
                .setParameter("logName", logName)
                .setParameter("passWord", passWord)
                .list();
        if(list.size() > 0)
            return list.get(0);
        else
            return null;
//        if(logName==null||logName.equals("")||passWord==null||passWord.equals("")){
//            return null;
//        }else {
//
//
//            Session session = sessionFactory.getCurrentSession();
////            Transaction transaction = session.beginTransaction();
//            try{
//                String hql = "from UserVO as user where user.logName = :logName and passWord = :passWord";
//                Query query = session.createQuery(hql);
//                query.setParameter("logName",logName);
//                query.setParameter("passWord",passWord);
//                Iterator iterator = query.iterate();
//                if (iterator.hasNext()){
//                    UserVO userVO = (UserVO)iterator.next();
//                    return userVO;
//                }else{
//                    return null;
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//                throw e;
//            }finally {
////                session.close();
//            }
//        }
    }

    /**
     * 由userVO获取对应的StudentVO
     * @param userVO
     * @return
     */
    public StudentVO getStudentVOByUserVO(UserVO userVO) throws Exception{
        if(userVO == null) {
            return null;
        }else{
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            try{
                StudentVO studentVO = session.get(StudentVO.class,userVO.getId());
                return studentVO;
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }finally {
//                session.close();
            }
        }
    }

    public void update(UserVO userVO) throws Exception{
        if (userVO == null)
            return;
        hibernateTemplate.update(userVO);
    }
}
