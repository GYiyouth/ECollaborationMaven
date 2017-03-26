package pojo.businessObject;

import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.ManagerDAO;
import pojo.DAO.StudentDAO;
import pojo.DAO.TeacherDAO;
import pojo.DAO.UserDAO;
import pojo.valueObject.DTO.ManagerDTO;
import pojo.valueObject.DTO.StudentDTO;
import pojo.valueObject.DTO.TeacherDTO;
import pojo.valueObject.DTO.UserDTO;
import pojo.valueObject.domain.ManagerVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.Map;

/**
 * Created by GR on 2017/2/26.
 */

public class UserBO {

    /**
     * 登录
     * @param logName
     * @param passWord
     * @param session
     * @return student、manager、teacher、fail
     */
    public JSONObject logIn(String logName, String passWord, Map<String , Object> session) throws Exception{
        if(logName==null||logName.equals("")||passWord==null||passWord.equals("")){
            System.out.println("用户名或者密码为空---"+this.getClass()+"logIn()");
            return null;
        }else {
            JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
            UserDAO userDAO = BeanFactory.getApplicationContext().getBean("userDAO", UserDAO.class);
            UserDTO userDTO = BeanFactory.getApplicationContext().getBean("userDTO",UserDTO.class);
            UserVO userVO = userDAO.getUserInfo(logName,passWord);
            if(userVO==null){
                System.out.println("没有这个用户user---"+this.getClass()+"logIn()");
                return null;
            }else{
                session.clear();
//                userDTO.clone(userVO);
//                session.put("userVO", userVO);
//                jsonObject.put("userBean",userDTO);
                if(userVO.getRole().equals("manager")){
                    ManagerDAO managerDAO = BeanFactory.getApplicationContext().getBean("managerDAO",ManagerDAO.class);
                    ManagerVO managerVO = managerDAO.getManagerInfo(userVO.getId());
                    if(managerVO != null) {
                        ManagerDTO managerDTO = BeanFactory.getApplicationContext().getBean("managerDTO",ManagerDTO.class);
                        managerDTO.clone(managerVO);
                        session.put("managerVO", managerVO);
                        jsonObject.put("managerBean", managerDTO);
                        jsonObject.put("role", "manager");
                        return jsonObject;
                    }else{
                        System.out.println("获取管理员的信息为空---"+this.getClass()+"logIn()");
                        return null;
                    }
                }else if(userVO.getRole().equals("teacher")){
                    TeacherDAO teacherDAO = BeanFactory.getApplicationContext().getBean("teacherDAO",TeacherDAO.class);
                    TeacherVO teacherVO = teacherDAO.getTeacherInfo(userVO.getId());
                    if(teacherVO != null) {
                        TeacherDTO teacherDTO = BeanFactory.getApplicationContext().getBean("teacherDTO",TeacherDTO.class);
                        teacherDTO.clone(teacherVO);
                        session.put("teacherVO", teacherVO);
                        jsonObject.put("teacherBean", teacherDTO);
                        jsonObject.put("role", "teacher");
                        return jsonObject;
                    }else{
                        System.out.println("获取老师的信息为空---"+this.getClass()+"logIn()");
                        return null;
                    }
                }else if(userVO.getRole().equals("student")){
                    //你写的什么玩意儿耿瑞这尼玛登录还分2次拿数据的我操
//                    StudentDAO studentDAO = BeanFactory.getApplicationContext().getBean("studentDAO",StudentDAO.class);
                    StudentDAO studentDAO = BeanFactory.getBean("studentDAO", StudentDAO.class);
                    StudentVO studentVO = studentDAO.getStudentInfoByStudentId(userVO.getId());

                    if(studentVO != null) {
                        StudentDTO studentDTO = BeanFactory.getApplicationContext().getBean("studentDTO",StudentDTO.class);
                        studentDTO.clone(studentVO);
                        session.put("studentVO", studentVO);
                        jsonObject.put("studentBean",studentDTO);
                        jsonObject.put("role", "student");
                        return jsonObject;
                    }else{
                        System.out.println("获取学生的信息为空---"+this.getClass()+"logIn()");
                        return null;
                    }
                }else{
                    System.out.println("没有这种角色---"+this.getClass()+"logIn()");
                    return null;
                }
            }
        }
    }

    /**
     * 登出
     * @param session
     * @return
     */
    public JSONObject logOut(Map<String, Object> session){
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject",JSONObject.class);
        session.clear();
        if(session.isEmpty()){
            jsonObject.put("result","success");
            return jsonObject;
        }else{
            return null;
        }
    }
}
