package action.com.teacher;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.TeacherBO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by geyao on 2017/2/27.
 */
public class ModifyTeacherInfoAction implements ServletRequestAware, ServletResponseAware, SessionAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private String name;
    private Integer sex;        //1:male
    private String email;
    private String phoneNumber;
    private String homePageUrl;
    private Integer needStudentsFlag;
    private Integer id;
    private String schoolId;
    private String role;       //1:manager 2:teacher 3:student
    private String logName;     //默认：email
    private String passWord;
    private String createDate;  //生成
    private String lastLogTime; //生成
    private String activeBefore;//生成
    private Integer newsFlag;   //生成
    private String photo;
    private JSONObject jsonObject;

    @SuppressWarnings("all")
    public String modifyTeacherInfo() throws Exception{
        System.out.println("修改老师信息");
        try {
            TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
            if (teacherVO == null){
                JSONHandler.sendJSON(jsonObject, response);
                return "fail";
            }
//            teacherVO.setId(id);
            teacherVO.setSchoolId(schoolId);
            teacherVO.setName(name);
            teacherVO.setSex(sex);
//            teacherVO.setRole(role);
            teacherVO.setEmail(email);
            teacherVO.setPhoneNumber(phoneNumber);
//            teacherVO.setLogName(logName);
//            teacherVO.setPassWord(passWord);
//            teacherVO.setCreateDate(createDate);
            teacherVO.setPhoto(photo);
//            teacherVO.setLastLogTime(lastLogTime);
//            teacherVO.setActiveBefore(activeBefore);
//            teacherVO.setNewsFlag(newsFlag);
            teacherVO.setHomePageUrl(homePageUrl);
            TeacherBO teacherBO = BeanFactory.getBean("teacherBO", TeacherBO.class);
            jsonObject = teacherBO.updateTeacherInfo(teacherVO);
            System.out.println(teacherVO);
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

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map<String, Object> getSession() {
        return session;
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

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public Integer getNeedStudentsFlag() {
        return needStudentsFlag;
    }

    public void setNeedStudentsFlag(Integer needStudentsFlag) {
        this.needStudentsFlag = needStudentsFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastLogTime() {
        return lastLogTime;
    }

    public void setLastLogTime(String lastLogTime) {
        this.lastLogTime = lastLogTime;
    }

    public String getActiveBefore() {
        return activeBefore;
    }

    public void setActiveBefore(String activeBefore) {
        this.activeBefore = activeBefore;
    }

    public Integer getNewsFlag() {
        return newsFlag;
    }

    public void setNewsFlag(Integer newsFlag) {
        this.newsFlag = newsFlag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}