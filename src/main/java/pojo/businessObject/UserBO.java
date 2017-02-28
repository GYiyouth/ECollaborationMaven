package pojo.businessObject;

import org.springframework.context.ApplicationContext;
import pojo.DAO.ManagerDAO;
import pojo.DAO.StudentDAO;
import pojo.DAO.TeacherDAO;
import pojo.DAO.UserDAO;
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
    public String logIn(String logName, String passWord, Map<String , Object> session){
        if(logName==null||logName.equals("")||passWord==null||passWord.equals("")){
            System.out.println("用户名或者密码为空---"+this.getClass()+"logIn()");
            return "fail";
        }else {
            ApplicationContext context = BeanFactory.getApplicationContext();
            UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
            UserVO userVO = userDAO.getUserInfo(logName,passWord);
            if(userVO==null){
                System.out.println("没有这个用户user---"+this.getClass()+"logIn()");
                return "fail";
            }else{
                session.clear();
                session.put("userVO", userVO);
                if(userVO.getRole().equals("manager")){
                    ManagerDAO managerDAO = context.getBean("managerDAO",ManagerDAO.class);
                    ManagerVO managerVO = managerDAO.getManagerInfo(userVO.getId());
                    if(managerVO != null) {
                        session.put("managerVO", managerVO);
                        return "manager";
                    }else{
                        System.out.println("获取管理员的信息为空---"+this.getClass()+"logIn()");
                        return "fail";
                    }
                }else if(userVO.getRole().equals("teacher")){
                    TeacherDAO teacherDAO = context.getBean("teacherDAO",TeacherDAO.class);
                    TeacherVO teacherVO = teacherDAO.getTeacherInfo(userVO.getId());
                    if(teacherVO != null) {
                        session.put("teacherVO", teacherVO);
                        return "teacher";
                    }else{
                        System.out.println("获取老师的信息为空---"+this.getClass()+"logIn()");
                        return "fail";
                    }
                }else if(userVO.getRole().equals("student")){
                    StudentDAO studentDAO = context.getBean("studentDAO",StudentDAO.class);
                    StudentVO studentVO = studentDAO.getStudentInfoByStudentId(userVO.getId());
                    if(studentVO != null) {
                        session.put("studentVO", studentVO);
                        return "student";
                    }else{
                        System.out.println("获取学生的信息为空---"+this.getClass()+"logIn()");
                        return "fail";
                    }
                }else{
                    System.out.println("没有这种角色---"+this.getClass()+"logIn()");
                    return "fail";
                }
            }
        }
    }

    /**
     * 登出
     * @param session
     * @return
     */
    public String logOut(Map<String, Object> session){
        session.clear();
        return "success";
    }
}
