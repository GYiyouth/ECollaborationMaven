package action.com.project;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.DAO.ProjectDAO;
import pojo.businessObject.MessageBO;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 修改项目信息
 * 给相关团队的学生、老师发送消息
 * 返回的字符串中有 projectBean
 * Created by geyao on 2017/4/16.
 */
@Controller
public class ModifyProjectInfoAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;


    private ProjectBO projectBO;
    @Autowired
    private MessageBO messageBO;

    //jsp提交
    private Integer id;
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


    public String execute() throws Exception {
        JSONObject jsonObject = BeanFactory.getJSONO();
        try {
            ProjectVO projectVO = projectBO.getProjectVO(id);
            String role = session.get("role").toString();
            UserVO userVO = (UserVO) session.get(role + "VO");
            //没有做判空处理
            projectVO.setName(name);
            projectVO.setApplyBeforeDate(applyBeforeDate);
            projectVO.setFinishDate(finishDate);
            projectVO.setSurvivalDate(survivalDate);
            projectVO.setTeamMax(teamMax);
            projectVO.setMemberMax(memberMax);
            projectVO.setKeyWord(keyWord);
            projectVO.setInfo(info);
            projectVO.setRequirement(requirement);
            projectVO.setGain(gain);
            projectVO.setPriority(priority);

            projectBO.updateProjectVO(projectVO);
            messageBO.updateProjectVO(projectVO, userVO);

            jsonObject.put("result", "success");
            ProjectDTO projectDTO = BeanFactory.getBean("projectDTO", ProjectDTO.class);
            projectDTO.clone(projectVO);
            jsonObject.put("projectBean", projectDTO);
            JSONHandler.sendJSON(jsonObject, response);
            System.out.println("修改项目信息");
            System.out.println(jsonObject);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }


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


    public ProjectBO getProjectBO() {
        return projectBO;
    }

    @Autowired
    public void setProjectBO(ProjectBO projectBO) {
        this.projectBO = projectBO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        try {
            this.request.setCharacterEncoding("UTF-8");
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


}
