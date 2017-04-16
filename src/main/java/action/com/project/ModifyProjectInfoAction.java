package action.com.project;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.DAO.ProjectDAO;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 修改项目信息
 * 返回的字符串中有 projectBean
 * Created by geyao on 2017/4/16.
 */
public class ModifyProjectInfoAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    @Autowired
    private ProjectBO projectBO;

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
            jsonObject.put("result", "success");
            ProjectDTO projectDTO = BeanFactory.getBean("projectDTO", ProjectDTO.class);
            projectDTO.clone(projectVO);
            jsonObject.put("projectBean", projectDTO);
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }


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
