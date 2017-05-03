package action.com.access;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.businessObject.AccessScoreBO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * 新增对个人的评价标准
 * Created by geyao on 2017/4/19.
 */
@Controller
public class CreateAccessTypeAction extends AbstractAction{
    //前端提供
    private Integer[] projectIds;
    private String[] typeNames;

    @Autowired
    private AccessScoreBO accessScoreBO;

    public String execute() throws Exception{
        if (typeNames.length == 0)
            return null;
        System.out.println(typeNames);
        JSONObject jsonObject = BeanFactory.getJSONO();


        ArrayList<Integer> projectIds = new ArrayList<>();
        for (Integer id : this.getProjectIds()){
            projectIds.add(id);
        }
        ArrayList<String> typeNames = new ArrayList<>();
        for (String names : this.getTypeNames()){
            typeNames.add(names);
        }
        accessScoreBO.addProjectAccessType(typeNames, projectIds);
        jsonObject.put("result", "success");
        JSONHandler.sendJSON(jsonObject, response);
        System.out.println("创建评价标准");
        System.out.println(jsonObject);
        return "success";

    }

    public Integer[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Integer[] projectIds) {
        this.projectIds = projectIds;
    }

    public String[] getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String[] typeNames) {
        this.typeNames = typeNames;
    }
}
