package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GR on 2017/2/26.
 */
@Repository
public class TeacherDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 根据id获取老师信息
     * @param id
     * @return
     */

    public TeacherVO getTeacherInfo(Integer id){
        if(id==null){
            return null;
        }else {
//            SessionFactory sf = BeanFactory.getSessionFactory();
//            Session session = sf.openSession();
            try {
            return hibernateTemplate.get(TeacherVO.class, id);

            }catch (Exception e){
                e.printStackTrace();
                throw e;
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
//            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
//            Session session = sessionFactory.getCurrentSession();
            hibernateTemplate.update(teacherVO);
            return teacherVO;
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
//        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
        try {
            List<TeacherVO> list = (List<TeacherVO>) hibernateTemplate.find(" from TeacherVO t ");
//            transaction.commit();
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

    /**
     * 查询一个项目的老师
     * @param projectVO
     * @return
     * @throws Exception
     */
    public TeacherVO getTeacherVOByProjectVO(ProjectVO projectVO) throws Exception{
        List<TeacherVO> teacherVOS = (List<TeacherVO>)
                hibernateTemplate.find("select p.teacherVO from ProjectVO p " +
                        "where p.id = ? ", projectVO.getId());
        return teacherVOS.size() > 0 ? teacherVOS.get(0) : null;
    }
}
