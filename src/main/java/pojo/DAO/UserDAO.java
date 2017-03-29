package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
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

/**
 * Created by GR on 2017/2/26.
 */
@Repository
@Transactional

//@DependsOn(value = "sessionFactory")
public class UserDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

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
        return  (UserVO)
                sessionFactory.getCurrentSession()
                .createQuery("from UserVO as user where user.logName = :logName and passWord = :passWord")
                .setParameter("logName", logName)
                .setParameter("passWord", passWord)
                .list().get(0);
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
            Session session = sessionFactory.openSession();
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
}
