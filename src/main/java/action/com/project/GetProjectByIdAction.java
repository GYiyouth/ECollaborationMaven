package action.com.project;

import action.com.Base.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import tool.JSONHandler;

import javax.annotation.Resource;

/**
 * 根据id获取项目信息
 * Created by GR on 2017/4/16.
 */
public class GetProjectByIdAction extends BaseAction{

    //jsp
    private Integer projectId;

    @Resource(name = "projectBO")
    private ProjectBO projectBO;
    @Resource(name="jsonObject")
    private JSONObject jsonObject;
    @Resource(name="projectDTO")
    private ProjectDTO projectDTO;

    @Override
    public String execute() throws Exception {
        if(projectId==null){
            throw new NullPointerException("projectId空---"+this.getClass()+"---execute()");
        }else {
            ProjectVO projectVO = projectBO.getProjectVO(projectId);
            if(projectVO==null){
                JSONHandler.sendJSON(jsonObject, response);
                return "fail";
            }else {
                projectDTO.clone(projectVO);
                jsonObject.put("projectBean", projectDTO);
                jsonObject.put("result","success");
                JSONHandler.sendJSON(jsonObject,response);
                return "success";
            }
        }
    }


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
