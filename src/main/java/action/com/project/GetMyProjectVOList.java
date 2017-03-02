package action.com.project;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.DAO.ProjectDAO;
import pojo.businessObject.ProjectBO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * 提供schoolProjectDTOList interestProjectDTOList , otherProjectDTOList
 * Created by geyao on 2017/3/2.
 */
public class GetMyProjectVOList implements SessionAware, ServletRequestAware, ServletResponseAware {
    private Map<String,Object> session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private Integer grade;

    private ArrayList<ProjectDTO> otherProjectDTOList = new ArrayList<>();
    private ArrayList<ProjectDTO> schoolProjectDTOList = new ArrayList<>();
    private ArrayList<ProjectDTO> interestProjectDTOList = new ArrayList<>();

    @SuppressWarnings("all")
    public String execute() throws Exception {
        //session错误
        JSONObject jsonObject = BeanFactory.getJSONO();
        if (session == null || (!session.containsKey("teacherVO")
                && !session.containsKey("studentVO"))){
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }


        UserVO userVO;
        if (session.containsKey("teacherVO")){
            userVO = (TeacherVO) session.get("teacherVO");
        }else if (session.containsKey("studentVO")){
            userVO = (StudentVO) session.get("studentVO");
        }
        if (userVO == null)
            return "fail";


        try {
            ProjectBO projectBO = BeanFactory.getBean("projectBO", ProjectBO.class);
            if (grade != null && (grade < 1))
                grade = null;
            ArrayList<ProjectVO> projectVOList = projectBO.getMyProjectVOList(userVO, grade, session);

            Iterator iterator = projectVOList.iterator();
            while (iterator.hasNext()){
                ProjectVO projectVO = (ProjectVO) iterator.next();
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.clone(projectVO);
                switch (projectDTO.getPriority()){
                    case 1:{
                        //工程实践
                        schoolProjectDTOList.add(projectDTO);
                    } break;
                    case 2:{
                        //个人兴趣
                        interestProjectDTOList.add(projectDTO);
                    } break;
                    default:{
                        //比赛，或者未分类的脏项目
                        otherProjectDTOList.add(projectDTO);
                    }
                }//switch
            }

            String[] jsonProperty = {
                    "schoolProjectDTOList",
                    "interestProjectDTOList",
                    "otherProjectDTOList"
            };
            jsonObject.put("result", "success");
            jsonObject.put(jsonProperty[0], schoolProjectDTOList);
            jsonObject.put(jsonProperty[1], interestProjectDTOList);
            jsonObject.put(jsonProperty[2], interestProjectDTOList);

            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }
    }



    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
            this.request = request;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        this.response = response;
    }
}
