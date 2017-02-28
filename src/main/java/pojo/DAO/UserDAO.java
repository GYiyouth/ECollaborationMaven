package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.Iterator;

/**
 * Created by GR on 2017/2/26.
 */
public class UserDAO {

    /**
     * 根据用户名、密码获取登陆者信息
     * @param logName
     * @param passWord
     * @return UserVO/null
     */
    public UserVO getUserInfo(String logName,String passWord){
        if(logName==null||logName.equals("")||passWord==null||passWord.equals("")){
            return null;
        }else {
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            try{
                String hql = "from UserVO as user where user.logName = :logName and passWord = :passWord";
                Query query = session.createQuery(hql);
                query.setParameter("logName",logName);
                query.setParameter("passWord",passWord);
                Iterator iterator = query.iterate();
                if (iterator.hasNext()){
                    UserVO userVO = (UserVO)iterator.next();
                    return userVO;
                }else{
                    return null;
                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 由userVO获取对应的StudentVO
     * @param userVO
     * @return
     */
    public StudentVO getStudentVOByUserVO(UserVO userVO){
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
                return null;
            }
        }
    }
}
