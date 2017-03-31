package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import java.util.Iterator;

/**
 * Created by GR on 2017/2/26.
 */
@Repository
public class StudentDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 根据id获取学生信息
     *
     * @param studentId
     * @return StudentVO/null
     */
    public StudentVO getStudentInfoByStudentId(Integer studentId) throws Exception{
        if (studentId == null || studentId.equals("")) {
            System.out.println("studentId is null---"+this.getClass()+"---getStudentInfoByStudentId()" );
            return null;
        } else {
            return hibernateTemplate.get(StudentVO.class, studentId);
        }
    }
}
