package pojo.businessObject;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.PlanDAO;
import pojo.DAO.ProjectDAO;
import pojo.valueObject.DTO.PlanDTO;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.Time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by geyao on 2017/3/1.
 */
@Transactional
public class PlanBO {
    @Autowired
    private PlanDAO planDAO;
    /**
     * 新增计划,并往session里添加planVO与projectVO
     * @param planVO
     * @return jsonObject
     * @throws Exception
     */
    public JSONObject savePlan(PlanVO planVO, Integer projectId, Map session) throws Exception{

        JSONObject jsonObject = BeanFactory.getBean("jsonObject", JSONObject.class);
        PlanDTO planDTO = BeanFactory.getBean("planDTO", PlanDTO.class);
        if (planVO == null || projectId == null)
            return jsonObject;
        try {
//            PlanDAO planDAO = BeanFactory.getBean("planDAO", PlanDAO.class);
            ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
            ProjectVO projectVO = projectDAO.getProjectVO(projectId);
            planVO = planDAO.addPlan(planVO, projectVO);
            planDTO.clone(planVO);
            jsonObject.put("result", "success");
            jsonObject.put("planBean", planDTO);
            if (session.containsKey("planVO")){
                session.replace("planVO", planVO);
            }else {
                session.put("planVO", planVO);
            }
            if (session.containsKey("projectVO")){
                session.replace("projectVO", projectVO);
            }else {
                session.put("projectVO", projectVO);
            }
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 返回PlanDTO的list，在session里放置planVOList
     * @param studentVO
     * @param projectId
     * @param session
     * @return
     */
    public ArrayList<PlanDTO> getPlanDTO(StudentVO studentVO, Integer projectId, Map session) throws Exception{
        ArrayList<PlanDTO> planDTOList = new ArrayList<>();
        ArrayList<PlanVO> planVOList = new ArrayList<>();
        try {
//            PlanDAO planDAO = new PlanDAO();
            planVOList = planDAO.getPlanVOList(studentVO, projectId);
            if (session.containsKey("planVOList"))
                session.remove("planVOList");
            session.put("planVOList", planVOList);

            Iterator iterator = planVOList.iterator();
            while (iterator.hasNext()){
                PlanDTO planDTO = new PlanDTO();
                planDTO.clone( (PlanVO)iterator.next() );
                planDTOList.add(planDTO);
            }
            return planDTOList;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public boolean finishAction(StudentVO studentVO, Integer planId) throws Exception{
        if (planId == null)
            throw new NullPointerException("参数为空异常");
        PlanVO planVO = planDAO.getPlanById(planId);
        if (planVO == null)
            throw new NullPointerException("该plan不存在");
        if (!planVO.getStudentVO().getId().equals( studentVO.getId() ) )
            return false;
        planVO.setFinishDate(Time.getCurrentTime());
        planDAO.updatePlanVO(planVO);
        return true;
    }
}
