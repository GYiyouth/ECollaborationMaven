package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import java.util.Iterator;

/**
 * Created by GR on 2017/2/26.
 */
public class StudentDAO {

    /**
     * 根据id获取学生信息
     *
     * @param studentId
     * @return StudentVO/null
     */
    public StudentVO getStudentInfoByStudentId(Integer studentId) {
        if (studentId == null || studentId.equals("")) {
            System.out.println("studentId is null---"+this.getClass()+"---getStudentInfoByStudentId()" );
            return null;
        } else {
            ApplicationContext context = BeanFactory.getApplicationContext();
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            //如果没有这个学生信息  返回null;
            StudentVO studentVO = session.get(StudentVO.class, studentId);
            if(studentVO==null){
                return null;
            }else{
                return studentVO;
            }
        }
    }
}
