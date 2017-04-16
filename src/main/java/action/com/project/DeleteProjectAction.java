package action.com.project;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

/**
 *
 * 删除项目
 * 需要projectId
 * Created by geyao on 2017/4/16.
 */
@Controller
public class DeleteProjectAction extends AbstractAction {
    //JSP需要
    private Integer projectId;

    @Autowired
    private ProjectBO projectBO;

    public String execute() throws Exception{
        String role = session.get("role").toString();
        UserVO userVO = (UserVO) session.get(role + "VO");
        JSONObject jsonObject = BeanFactory.getJSONO();
        if (role == null || userVO == null){
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
        try {
            projectBO.deleteProjectVO(projectId);
            jsonObject.put("result", "success");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }finally {
            JSONHandler.sendJSON(jsonObject, response);
        }

    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}