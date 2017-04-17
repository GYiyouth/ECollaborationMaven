package action.com.application;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pojo.businessObject.ApplicationBO;
import pojo.valueObject.DTO.ApplicationDTO;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyao on 2017/4/17.
 */
@Controller
public class GetApplicationAction extends AbstractAction {
    @Autowired
    private ApplicationBO applicationBO;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public String execute() throws Exception{
        JSONObject jsonObject = BeanFactory.getJSONO();
        String role = session.get("role").toString();
        UserVO userVO = (UserVO) session.get(role + "VO");
        List<ApplicationVO> applicationVOList = applicationBO.
                getHandlingApplication(userVO, session);
        //application和各种名字要一一对应
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        List<List> name = new ArrayList<>();

        for (ApplicationVO applicationVO : applicationVOList){
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.clone(applicationVO);
            applicationDTOList.add(applicationDTO);
            List<String> names = new ArrayList<>();
            //名字的顺序为 项目 团队 处理人 申请人
            switch (applicationDTO.getType()){
                case "team":{
                    TeamVO teamVO = hibernateTemplate
                            .get(TeamVO.class, applicationDTO.getTeamId());
                    names.add("");
                    names.add(teamVO.getTeamName());
                    UserVO handlerId = hibernateTemplate
                            .get(UserVO.class, applicationDTO.getHandlerId());
                    names.add(handlerId.getName());
                    names.add(userVO.getName());
                }break;
                case "project":{
                    ProjectVO projectVO = hibernateTemplate.
                            get(ProjectVO.class, applicationDTO.getProjectId());

                    names.add(projectVO.getName());
                    names.add("");
                    UserVO handlerId = hibernateTemplate
                            .get(UserVO.class, applicationDTO.getHandlerId());
                    names.add(handlerId.getName());
                    names.add(userVO.getName());
                }break;
                default:break;
            }

            name.add(names);


        }
        jsonObject.put("result", "success");
        jsonObject.put("applicationDTOList", applicationDTOList);
        jsonObject.put("name", name);
        JSONHandler.sendJSON(jsonObject, response);
        return "success";
    }
}
