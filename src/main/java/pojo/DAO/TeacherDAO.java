package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

/**
 * Created by GR on 2017/2/26.
 */
public class TeacherDAO {

    /**
     * 根据id获取老师信息
     * @param id
     * @return
     */
    public TeacherVO getTeacherInfo(Integer id){
        if(id==null){
            return null;
        }else {
            ApplicationContext context = BeanFactory.getApplicationContext();
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            return session.get(TeacherVO.class, id);
        }
    }

    /**
     * 更新教师信息
     * @param teacherVO
     * @return
     */
    public TeacherVO updateTeacherInfo(TeacherVO teacherVO) throws Exception{
        try {
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.update(teacherVO);
            return session.get(TeacherVO.class, teacherVO.getId());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
