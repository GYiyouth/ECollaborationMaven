package action.com.student;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.StudentBO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 修改学生个人信息
 * Created by GR on 2017/4/16.
 */
public class ModifyStudentInfoAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private  JSONObject jsonObject;

    //jsp
    private String schoolId;
    private String name;
    private Integer sex;        //1:male
    private String email;
    private String phoneNumber;
    private String photo;
    private int grade;
    private String graduatedSchool;
    private String tecKeyWord;
    private String homePageUrl;
    private String githubLogin;

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("...");
//            StudentVO studentVO = BeanFactory.getBean("studentVO", StudentVO.class);
            StudentVO studentVO = (StudentVO) session.get("studentVO");
            studentVO.setSchoolId(schoolId);
            studentVO.setName(name);
            studentVO.setSex(sex);
            studentVO.setEmail(email);
            studentVO.setPhoneNumber(phoneNumber);
            studentVO.setPhoto(photo);
            studentVO.setGrade(grade);
            studentVO.setGraduatedSchool(graduatedSchool);
            studentVO.setTecKeyWord(tecKeyWord);
            studentVO.setHomePageUrl(homePageUrl);
            studentVO.setGithubLogin(githubLogin);
            System.out.println("hhhhh"+studentVO);
            StudentBO studentBO = BeanFactory.getBean("studentBO", StudentBO.class);
            JSONObject jsonObject = studentBO.updateTeacherInfo(studentVO);
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "fail";
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
        this.response.setCharacterEncoding("UTF-8");
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getTecKeyWord() {
        return tecKeyWord;
    }

    public void setTecKeyWord(String tecKeyWord) {
        this.tecKeyWord = tecKeyWord;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getGithubLogin() {
        return githubLogin;
    }

    public void setGithubLogin(String githubLogin) {
        this.githubLogin = githubLogin;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
