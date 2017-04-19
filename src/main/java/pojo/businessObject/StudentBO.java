package pojo.businessObject;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.StudentDAO;
import pojo.DAO.TeacherDAO;
import pojo.valueObject.DTO.StudentDTO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import javax.jws.Oneway;

/**
 * Created by GR on 2017/4/16.
 */
@Repository
@Transactional
public class StudentBO {

    @Autowired
    private StudentDAO studentDAO;

    /**
     * 修改学生个人信息
     * @param newStudentVO
     * @return
     * @throws Exception
     */
    public JSONObject updateTeacherInfo(StudentVO newStudentVO) throws Exception{
        StudentDAO studentDAO = BeanFactory.getApplicationContext().getBean("studentDAO", StudentDAO.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
        StudentVO studentVO = studentDAO.updateStudentInfo(newStudentVO);
        if (studentVO != null){
            StudentDTO studentDTO = BeanFactory.getApplicationContext().getBean("studentDTO", StudentDTO.class);
            studentDTO.clone(studentVO);
            jsonObject.put("result", "success");
            jsonObject.put("studentBean", studentDTO);
        }
        return jsonObject;
    }


    public StudentVO getStudentById(Integer studetId) throws Exception{
        if(studetId == null){
            throw new NullPointerException("studentId is null ---"+this.getClass().getName() +"---getStudentById()");
        }else{
            return studentDAO.getStudentInfoByStudentId(studetId);
        }
    }
}
