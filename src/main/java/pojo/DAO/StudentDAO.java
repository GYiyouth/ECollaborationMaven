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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /**
     * 根据学生github的用户名返回学生信息
     * @param githubLogin
     * @return
     * @throws Exception
     */
    public StudentVO getByGithubLogin(String githubLogin) throws Exception{
        if (githubLogin == null || githubLogin.equals("")) {
            throw new NullPointerException("githubLogin is null---"+this.getClass()+"---getByGithubLogin()" );
        } else {
            List<StudentVO> studentVOS =  (ArrayList<StudentVO>)
                    hibernateTemplate.findByNamedParam(
                            "select s from  StudentVO s where s.githubLogin = :githubLogin", "githubLogin", githubLogin
                    );
            if(!studentVOS.isEmpty()){
                return studentVOS.get(0);
            }else{
                return null;
//                throw new NullPointerException("没找到学生："+this.getClass()+"getByGithubLogin()");
            }
        }
    }

    /**
     * 更新学生个人信息
     * @param studentVO
     * @return
     */
    public StudentVO updateStudentInfo(StudentVO studentVO) throws Exception{
        try {
            hibernateTemplate.update(studentVO);
            return studentVO;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
