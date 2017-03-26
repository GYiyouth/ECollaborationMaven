package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            try {
            return session.get(TeacherVO.class, id);

            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                session.close();
            }
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
            Session session = sessionFactory.getCurrentSession();
            session.update(teacherVO);
            return session.get(TeacherVO.class, teacherVO.getId());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取所有教师
     * @return
     */
    public HashMap<Integer, TeacherVO> getAllTeacher(){
        HashMap<Integer, TeacherVO> hashMap = new HashMap<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<TeacherVO> list = session.createCriteria(TeacherVO.class)
                    .list();
            transaction.commit();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                TeacherVO teacherVO = (TeacherVO) iterator.next();
                hashMap.put(teacherVO.getId(), teacherVO);
            }
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
