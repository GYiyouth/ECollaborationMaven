package action.com.access;

import action.com.Base.BaseAction;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.businessObject.AccessScoreBO;
import pojo.businessObject.StudentBO;
import pojo.valueObject.DTO.ProjectAccessTypeDTO;
import pojo.valueObject.assist.ProjectAccessTypeVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;

/**
 * Created by GR on 2017/4/19.
 */
public class AddAccessToStudentAction extends BaseAction{

    //jsp
    private String evaluationResults;

    @Autowired
    private StudentBO studentBO;
    @Autowired
    private AccessScoreBO accessScoreBO;

    @Override
    public String execute() throws Exception {
        try{
            JSONArray jsonArray = JSONArray.fromObject(evaluationResults);
            ArrayList<StudentVO> studentVOS = BeanFactory.getBean("arrayList",ArrayList.class);
            ArrayList<ProjectAccessTypeVO> projectAccessTypeVOS = BeanFactory.getBean("arrayList",ArrayList.class);
            ArrayList<Integer> scores = BeanFactory.getBean("arrayList",ArrayList.class);


            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Integer studentId = Integer.parseInt(jsonObject.getString("studentId"));
                System.out.println(studentId+":?");
                Integer typeId = Integer.parseInt(jsonObject.getString("typeId"));
                Integer score = Integer.parseInt(jsonObject.getString("score"));
                scores.add(score);
                if(studentBO.getStudentById(studentId) != null)
                    studentVOS.add(studentBO.getStudentById(studentId));
                if(accessScoreBO.getProjectAccessTypeVOByTypeId(typeId)!=null){
                    projectAccessTypeVOS.add(accessScoreBO.getProjectAccessTypeVOByTypeId(typeId));
                }
            }
            accessScoreBO.addAccessToStudentAction(studentVOS,projectAccessTypeVOS,scores);
            jsonObject.put("result","success");
            JSONHandler.sendJSON(jsonObject,response);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("result","SQLException");
            JSONHandler.sendJSON(jsonObject,response);
            return "fail";
        }
    }

    public String getEvaluationResults() {
        return evaluationResults;
    }

    public void setEvaluationResults(String evaluationResults) {
        this.evaluationResults = evaluationResults;
    }
}
