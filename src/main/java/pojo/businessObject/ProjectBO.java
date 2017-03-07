package pojo.businessObject;

//import com.sun.istack.internal.Nullable;
import pojo.DAO.PlanDAO;
import pojo.DAO.ProjectDAO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
public class ProjectBO {

    /**
     * 添加任务，在session里添加projectVO，如果是老师新建，则还会改动教师VO中的projectSet
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

            //老师新建项目以后，要把它的ProjectSet也动一下
            if (session.containsKey("teacherVO")){
                TeacherVO teacherVO = (TeacherVO) session.get("teacherVO");
                teacherVO.getProjectVOSet().add(projectVO);
            }
            return projectVO;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询项目
     * 如果是学生，会先查询他的team，再根据team来完成找项目
     * 如果是教师，会返回teacherVO中的projectSet
     * session里放置projectVOList
     * @param userVO
     * @param grade
     * @param session
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getMyProjectVOList(
            UserVO userVO, Integer grade,  Map session) throws Exception{
        if (userVO == null || session == null)
            throw new NullPointerException("userVO == null || session == null");
        if (userVO.getRole().equals("teacher")){
            return getTeacherProjectVOList((TeacherVO)userVO, session, grade);
        }

        ArrayList<ProjectVO> arrayList = new ArrayList<>();
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        arrayList = projectDAO.getStudentProjectVOList((StudentVO)userVO);

        String sessionProperty = "projectVOList";
        if (session.containsKey(sessionProperty))
            session.remove(sessionProperty);
        session.put(sessionProperty, arrayList);

        return arrayList;
    }

    /**
     * 根据TeacherVO里的ProjectSet得到projectList
     * 加入session：projectVOList
     * @param teacherVO
     * @param session
     * @param grade
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOList(TeacherVO teacherVO,
                                                       Map session, Integer grade)throws Exception{
        if (teacherVO == null || session == null)
            throw new  NullPointerException("teacherVO == null || session == null");
        ArrayList<ProjectVO> arrayList = new ArrayList<>();
        Iterator iterator = teacherVO.getProjectVOSet().iterator();
        String sessionProperty = "projectVOList";
        if ( grade == null){
            while (iterator.hasNext()){
                arrayList.add( (ProjectVO) iterator.next() );
            }
            return arrayList;
        }else {
            ProjectVO projectVO = (ProjectVO) iterator.next();
            if (projectVO.getGrade() != null
                    && projectVO.getGrade().equals(grade)){
                arrayList.add(projectVO);
            }
        }

        if (session.containsKey(sessionProperty)){
            session.remove(sessionProperty);
        }
        session.put(sessionProperty, arrayList);

        return arrayList;
    }

    /**
     * 团队申请项目
     * 消息会涉及，团队中的所有人，项目的老师
     * @param teamId
     * @param projectId
     * @throws Exception
     */
    public void applyProject(Integer teamId, Integer projectId) throws Exception{

//        ApplicationVO applicationVO = BeanFactory.getBean("applicationVO", ApplicationVO.class);
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        projectDAO.applyProject(teamId, projectId);
    }

}
