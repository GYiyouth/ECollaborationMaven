package pojo.businessObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.ApplicationDAO;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TeamDAO;
import pojo.DAO.UserDAO;
import pojo.valueObject.DTO.ApplicationDTO;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
@Transactional
@Service
public class ApplicationBO {
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ApplicationDAO applicationDAO;

    /**
     * 获取需要处理的申请，session里添加applicationVOList
     *
     * @param userVO
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getHandlingApplication(UserVO userVO) throws Exception {

        try {
            //存放在session中的key
            String sessionProperty = "applicationVOList";
            ArrayList<ApplicationVO> arrayList;


            return applicationDAO.getApplicationVOListByHandlerVO(userVO);
//
//            if (session.containsKey(sessionProperty))
//                session.remove(sessionProperty);
//            session.put(sessionProperty, arrayList);
//            System.out.println(this.getClass());


        } catch (Exception e) {
            System.out.println(this.getClass() + "123");
            e.printStackTrace();
            throw e;
        }
    }



    public void getApplication(
            List<ApplicationVO> applicationVOList, List<ApplicationDTO> applicationDTOList,
            List<List> name, UserVO userVO
    ) throws Exception {
        for (ApplicationVO applicationVO : applicationVOList) {
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.clone(applicationVO);
            applicationDTOList.add(applicationDTO);
            List<String> names = new ArrayList<>();
            //名字的顺序为 项目 团队 处理人 申请人
            switch (applicationDTO.getType()) {
                case "team": {
                    TeamVO teamVO = teamDAO.getTeamVOByTeamId(applicationDTO.getTeamId());
                    names.add("0");
                    names.add(teamVO.getTeamName());
                    UserVO handlerId = userDAO.getUser(applicationDTO.getHandlerId());
                    names.add(handlerId.getName());
                    names.add(userVO.getName());
                }
                break;
                case "project": {
                    ProjectVO projectVO = projectDAO.getProjectVO(applicationDTO.getProjectId());

                    names.add(projectVO.getName());
                    names.add("0");
                    UserVO handlerId = userDAO.getUser(applicationDTO.getHandlerId());
                    names.add(handlerId.getName());
                    names.add(userVO.getName());
                }
                break;
                default:
                    break;
            }

            name.add(names);
        }
        System.out.println(applicationDTOList);
        System.out.println(name);
    }


}
