package action.com.project;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;
import tool.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 提供给Web-projectDTO
 * 提供给json里的-projectBean
 * Created by geyao on 2017/3/2.
 */
public class AddProjectAction implements SessionAware, ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    //jsp提交
    private String name;
    private String applyBeforeDate;     //申请项目截止时间
    private String finishDate;          //项目截止时间
    private String survivalDate;        //项目有效期？
    private Integer teamMax;            //最多接受多少个团队
    private Integer memberMax;          //每组最多多少人
    private String keyWord;
    private String info;
    private String requirement;
    private String gain;
    private Integer priority = 0;           //区分是那种项目 0：工程实践 1：个人兴趣 2比赛

    private String accessJSON;

    //非jsp提交
    private String createDate;
    private Integer teamNumber;
    private Integer grade = Integer.parseInt(Time.getGrade());              //年份

    private Integer status;             //状态   0：失效 1：申请 2：执行 3：完成
    private Integer creatorId;
    private Integer teacherId;

    //提供给Web
    private ProjectDTO projectDTO;

    public String execute() throws Exception {
        JSONObject jsonObject = BeanFactory.getJSONO();
        UserVO creator;
        boolean isteacher = false;      //创始人就是老师
        if (session.containsKey("studentVO")){
            creator = (StudentVO) session.get("studentVO");
        }else if (session.containsKey("teacherVO")){
            creator = (TeacherVO) session.get("teacherVO");
            isteacher = true;
        }else {
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        try {
            ProjectVO projectVO = BeanFactory.getBean("projectVO", ProjectVO.class);
            projectVO.setName(name);
            projectVO.setApplyBeforeDate(applyBeforeDate);
            projectVO.setFinishDate(finishDate);
            projectVO.setSurvivalDate(survivalDate);
            projectVO.setTeamMax( teamMax);
            projectVO.setMemberMax(memberMax);
            projectVO.setKeyWord(keyWord);
            projectVO.setInfo(info);
            projectVO.setRequirement(requirement);
            projectVO.setGain(gain);
            projectVO.setPriority(priority);
            projectVO.setCreateDate(Time.getCurrentTime());
            projectVO.setGrade(grade);
            projectVO.setTeamNumber(0);
            projectVO.setStatus(1);
            projectVO.setCreatorUserVO(creator);
            if (isteacher)
                projectVO.setTeacherVO((TeacherVO) creator);

            ProjectBO projectBO = BeanFactory.getBean("projectBO", ProjectBO.class);
            projectBO.addProject(projectVO, session);



            projectDTO.clone(projectVO);
            jsonObject.put("projectBean", projectDTO);
            jsonObject.put("result", "success");
            JSONHandler.sendJSON(jsonObject, response);
            System.out.println("创建项目");
            System.out.println(jsonObject);
            return "success";
        }catch (Exception e){
            JSONHandler.sendJSON(jsonObject, response);
            e.printStackTrace();
            return "fail";
        }

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

    public String getApplyBeforeDate() {
        return applyBeforeDate;
    }

    public void setApplyBeforeDate(String applyBeforeDate) {
        this.applyBeforeDate = applyBeforeDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getSurvivalDate() {
        return survivalDate;
    }

    public void setSurvivalDate(String survivalDate) {
        this.survivalDate = survivalDate;
    }

    public Integer getTeamMax() {
        return teamMax;
    }

    public void setTeamMax(Integer teamMax) {
        this.teamMax = teamMax;
    }

    public Integer getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(Integer memberMax) {
        this.memberMax = memberMax;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public void setProjectDTO(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

    public String getAccessJSON() {
        return accessJSON;
    }

    public void setAccessJSON(String accessJSON) {
        this.accessJSON = accessJSON;
    }
}
