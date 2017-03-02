package pojo.businessObject;

import pojo.DAO.PlanDAO;
import pojo.DAO.ProjectDAO;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;

import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
public class ProjectBO {

    /**
     * 添加任务，在session里添加projectDAO
     * @param projectVO
     * @param session
     * @return
     * @throws Exception
     */
    public ProjectVO addProject(ProjectVO projectVO, Map session) throws Exception{
        if (projectVO == null || session ==null){
            return null;
        }

        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        try {
            projectDAO.addProjectVO(projectVO);
            if (session.containsKey("projectVO"))
                session.remove("projectVO");
            session.put("projectVO", projectVO);

            return projectVO;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
