package action.com.access;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.businessObject.AccessScoreBO;
import pojo.valueObject.DTO.ProjectAccessTypeDTO;
import pojo.valueObject.DTO.StudentDTO;
import pojo.valueObject.DTO.StudentScoreDTO;
import pojo.valueObject.assist.ProjectAccessTypeVO;
import pojo.valueObject.assist.StudentScoreVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;
import tool.JSONHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取评分标准及对应分数
 * Created by geyao on 2017/4/19.
 */
@Controller
public class GetAccessAction extends AbstractAction {
    //前端获取
    private Integer teamId;
    private Integer projectId;

    @Autowired
    private AccessScoreBO accessScoreBO;


    public String execute() throws Exception{
        JSONObject jsonObject = BeanFactory.getJSONO();
        ArrayList<List> rowData = accessScoreBO.getAccess(projectId, teamId);
        ArrayList<ProjectAccessTypeDTO> projectAccessTypeDTOS = new ArrayList<>();
        //评分的条目
        for (ProjectAccessTypeVO projectAccessTypeVO :
                (ArrayList<ProjectAccessTypeVO>)rowData.get(0)){
            ProjectAccessTypeDTO projectAccessTypeDTO = new ProjectAccessTypeDTO();
            projectAccessTypeDTO.clone(projectAccessTypeVO);
            projectAccessTypeDTOS.add(projectAccessTypeDTO);
        }
        jsonObject.put("accessTypeDTOList", projectAccessTypeDTOS);

        //学生DTOList
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        for (StudentVO studentVO :
                (ArrayList<StudentVO>) rowData.get(1)){
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.clone(studentVO);
            studentDTOS.add(studentDTO);
        }
        jsonObject.put("studentDTOList", studentDTOS);

        //学生数量
        int studentNum = rowData.size() - 2;
        jsonObject.put("studentNum", studentNum);

        ArrayList<StudentScoreDTO> totalScore = new ArrayList<>();

        //评价分数
        //分别处理每个学生
        for (int i = 0 ; i < studentNum; i++ ){
            ArrayList<StudentScoreDTO> studentScoreDTOS = new ArrayList<>();
            //分别处理每个分数
            for (StudentScoreVO studentScoreVO :
                    (ArrayList<StudentScoreVO>) rowData.get(2 + i) ){
                StudentScoreDTO studentScoreDTO = new StudentScoreDTO();
                studentScoreDTO.clone(studentScoreVO);
                studentScoreDTOS.add(studentScoreDTO);
            }
            jsonObject.put("studentScore" + i, studentScoreDTOS);
            totalScore.addAll(studentScoreDTOS);
        }

        jsonObject.put("result", "success");
        jsonObject.put("studentScore", studentDTOS);
        JSONHandler.sendJSON(jsonObject, response);
        System.out.println("获取评价");
        System.out.println(jsonObject);
        return "success";
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
