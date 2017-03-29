import pojo.DAO.UserDAO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

/**
 * Created by geyao on 2017/3/27.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        UserDAO userDAO = BeanFactory.getBean("userDAO", UserDAO.class);
//        System.out.println(userDAO.getUser(1));
        TeacherVO teacherVO = (TeacherVO) userDAO.getUser(1);
        System.out.println(teacherVO);
        System.out.println("================");
        System.out.println(userDAO.getUserInfo("1","123"));

    }

    public static void foo(Test2 test2){
        test2.a = 2;
        test2.b = 3;
        test2.s = "123";
    }
}
