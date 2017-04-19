package action.com.application;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TeamDAO;
import pojo.DAO.UserDAO;
import pojo.businessObject.ApplicationBO;
import pojo.businessObject.TeamBO;
import pojo.businessObject.UserBO;
import pojo.valueObject.DTO.ApplicationDTO;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by geyao on 2017/4/17.
 */

@Controller
public class GetApplicationAction extends AbstractAction {

    private ApplicationBO applicationBO;

    public String execute() throws Exception{
        System.out.println("获取申请");
        JSONObject jsonObject = BeanFactory.getJSONO();
        String role = session.get("role").toString();
        UserVO userVO = (UserVO) session.get(role + "VO");

        List<ApplicationVO> applicationVOList = new ArrayList<>();
        applicationVOList = applicationBO.getHandlingApplication(userVO);


        //application和各种名字要一一对应
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        List<List> name = new ArrayList<>();
        System.out.println(applicationVOList.size());
        applicationBO.getApplication(applicationVOList, applicationDTOList, name, userVO);
        System.out.println("申请获取结束");

        jsonObject.put("result", "success");
        jsonObject.put("applicationDTOList", applicationDTOList);
        jsonObject.put("name", name);
        JSONHandler.sendJSON(jsonObject, response);
        System.out.println("获取申请");
        System.out.println(jsonObject);
        return "success";
    }
    @Autowired
    public void setApplicationBO(ApplicationBO applicationBO) {
        this.applicationBO = applicationBO;
    }
}
