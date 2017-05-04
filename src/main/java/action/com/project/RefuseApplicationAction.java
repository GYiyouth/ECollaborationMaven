package action.com.project;

import action.com.Base.BaseAction;
import net.sf.json.JSONObject;
import pojo.businessObject.ProjectBO;
import tool.BeanFactory;
import tool.JSONHandler;

/**
 * Created by GR on 2017/4/18.
 */
public class RefuseApplicationAction extends BaseAction{

    //jsp
    private Integer applicationId;


    @Override
    public String execute() throws Exception {
        try{
            System.out.println(this.getClass());
            ProjectBO projectBO = BeanFactory.getBean("projectBO",ProjectBO.class);
            System.out.println("applicationId"+applicationId);
            projectBO.refuseApplyProjectApplication(applicationId);
            jsonObject.put("result", "success");
            JSONHandler.sendJSON(jsonObject, response);
            System.out.println("拒绝申请");
            System.out.println(jsonObject);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
