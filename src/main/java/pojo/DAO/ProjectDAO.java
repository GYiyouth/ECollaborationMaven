package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.assist.*;
import org.hibernate.query.Query;
import pojo.valueObject.domain.*;
//import sun.plugin2.message.Message;
import tool.BeanFactory;
import tool.Time;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by geyao on 2017/3/1.
 */
@Repository
public class ProjectDAO {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 获取项目，根据id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ProjectVO getProjectVO(Integer id) throws Exception {
        if (id == null || id < 0)
            throw new SQLException("projectId输入异常");
        return hibernateTemplate.get(ProjectVO.class, id);
    }

    /**
     * 增加项目，不处理与team的关系表
     *
     * @param projectVO
     * @return
     */
    public ProjectVO addProjectVO(ProjectVO projectVO) throws Exception {
        if (projectVO == null)
            throw new SQLException("projectVO输入异常");
        hibernateTemplate.save(projectVO);
        return projectVO;
//        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
//        try{
//            session.save(projectVO);
//            transaction.commit();
//            return projectVO;
//        }catch (Exception e){
//            transaction.rollback();
//            e.printStackTrace();
//            throw e;
//        }
    }

    /**
     * 得到teacher的全部project
     *
     * @param teacherVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOList(TeacherVO teacherVO) throws Exception {
        if (teacherVO == null) {
            throw new SQLException("teacherVO输入异常");
        }
        return (ArrayList<ProjectVO>)
                hibernateTemplate.findByNamedParam(
                        "select p from  ProjectVO p where p.teacherVO.id = :id", "id", teacherVO.getId()
                );
    }

    /**
     * 得到teacher某一特定年份的project
     *
     * @param teacherVO
     * @param grade
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOListByGrade(TeacherVO teacherVO, Integer grade) throws Exception {
        return null;
    }

    /**
     * 根据学生VO查询projectVOList
     *
     * @param studentVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getStudentProjectVOList(StudentVO studentVO) throws Exception {
        if (studentVO == null)
            throw new NullPointerException("studentVO == null");

        return (ArrayList<ProjectVO>)
                hibernateTemplate.find(
                        "select tp.projectVO from  TeamProjectVO  tp " +
                                " where tp.teamVO in (" +
                                " select st.teamVO from StudentTeamVO st " +
                                " where st.studentVO.id = " + studentVO.getId() + ")"
                );

//        ArrayList<ProjectVO> arrayList = new ArrayList<>();
//        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            List<StudentTeamVO> STlist = session.createCriteria(StudentTeamVO.class)
//                    .add(Restrictions.eq("studentVO", studentVO))
//                    .list();
//            Iterator sTiterator = STlist.iterator();
//            //放置TeamVO
//            ArrayList<TeamVO> teamVOArrayList = new ArrayList<>();
//            while (sTiterator.hasNext()){
//                StudentTeamVO studentTeamVO = (StudentTeamVO)sTiterator.next();
//                teamVOArrayList.add(studentTeamVO.getTeamVO());
//            }
//            List<TeamProjectVO> TPList = session.createCriteria(TeamProjectVO.class)
//                    .add(Restrictions.in("teamVO", teamVOArrayList))
//                    .list();
//            transaction.commit();
//            System.out.println(STlist);
//            System.out.println(TPList);
//            Iterator iterator = TPList.iterator();
//            while (iterator.hasNext()) {
//                TeamProjectVO teamProjectVO = (TeamProjectVO) iterator.next();
//
//                ProjectVO projectVO = teamProjectVO.getProjectVO();
//                arrayList.add(projectVO);
//            }
//            return arrayList;
//        }catch (Exception e){
//            e.printStackTrace();
//            throw e;
//        }
    }

    /**
     * 根据年级获取所有项目
     *
     * @param grade
     * @return
     * @throws Exception
     */
    public List<ProjectVO> getAllProject(int grade) throws Exception {
        return (List<ProjectVO>)
                hibernateTemplate.findByNamedParam(
                        "select p from ProjectVO p where p.grade = :grade", "grade", grade
                );
    }

    /**
     * 获取所有项目
     *
     * @return
     * @throws Exception
     */
    public HashMap<Integer, ProjectVO> getAllProject() throws Exception {
        HashMap<Integer, ProjectVO> hashMap = new HashMap<>();

        try {
            List projectVOList = hibernateTemplate.find("select p from ProjectVO p");
            Iterator iterator = projectVOList.iterator();
            while (iterator.hasNext()) {
                ProjectVO projectVO = (ProjectVO) iterator.next();
                hashMap.put(projectVO.getId(), projectVO);
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 申请项目
     * 添加application
     * 添加message
     * 改变相关人员的NewsFlag
     *
     * @param teamId
     * @param projectId
     * @throws Exception
     */
    public void applyProject(Integer teamId, Integer projectId) throws Exception {

//        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
//        try{
        TeamVO teamVO = hibernateTemplate.get(TeamVO.class, teamId);
        ProjectVO projectVO = hibernateTemplate.get(ProjectVO.class, projectId);
//        List oldList = hibernateTemplate.find("select a from ApplicationVO a where a.projectVO = ? and a.teamVO = ?", projectVO, teamVO);
//        if (oldList.size() >= 1)
//            throw new Exception("已有存在的申请");

        //处理人优先让老师申请，没有老师，则是学生申请
        UserVO handleUserVO = null;
        handleUserVO = projectVO.getTeacherVO();
        if (handleUserVO == null) {
            handleUserVO = projectVO.getCreatorUserVO();
        }
        StudentVO teamLeader = teamVO.getCreatorStudentVO();

        System.out.println("处理人 = " + handleUserVO.getName());
        System.out.println("团队队长 = " + teamLeader.getName());
//            ArrayList<StudentVO> affectedStudentVOList = new ArrayList<>();
//            affectedStudentVOList.addAll(teamVO.getStudentVOSet());

        if (teamVO == null || projectVO == null)
            throw new NullPointerException("teamVO == null || projectVO == null");
        ApplicationVO applicationVO = BeanFactory.getBean("applicationVO", ApplicationVO.class);
        applicationVO.setType("project");
        applicationVO.setTeamVO(teamVO);
        applicationVO.setProjectVO(projectVO);
        applicationVO.setHandlerUserVO(handleUserVO);
        applicationVO.setAffectedUserVO(teamLeader);
        applicationVO.setCreatedTime(Time.getCurrentTime());
        //接下来处理message，最后寸application

        MessageVO messageVO = BeanFactory.getBean("messageVO", MessageVO.class);
        messageVO.setTitle(projectVO.getName() + "有新的申请消息");
        messageVO.setContent(teamVO.getTeamName() + "团队申请加入您的项目【" +
                projectVO.getName() + "】\n团队情况如下：\n队长：" + teamLeader.getName() +
                "\n小队人员数：" + teamVO.getStudentVOSet().size() +
                "\n目前项目已有多少 " + projectVO.getTeamVOSet().size() + " 个团队在做" +
                "\n项目设定的最大承接团队数目是：" + projectVO.getTeamMax()
        );
        messageVO.setCreateTime(Time.getCurrentTime());
        messageVO.setSenderUserVO(teamLeader);
        messageVO.setReadFlag(1);
        messageVO.setDeadDate(Time.getDeadTime());
        hibernateTemplate.save(messageVO);

        MessageReceiverVO messageReceiverVO = BeanFactory.getBean("messageReceiverVO", MessageReceiverVO.class);
        messageReceiverVO.setMessageVO(messageVO);
        messageReceiverVO.setReceiverUserVO(handleUserVO);
        messageReceiverVO.setReadFlag(false);
        hibernateTemplate.save(messageReceiverVO);


        Integer newsFlag = handleUserVO.getNewsFlag();
        if (newsFlag != null)
            newsFlag++;
        else
            newsFlag = 1;
        handleUserVO.setNewsFlag(newsFlag);

        applicationVO.setMessageVO(messageVO);
        hibernateTemplate.update(handleUserVO);
        hibernateTemplate.save(applicationVO);

//            transaction.commit();
//
//        }catch (Exception e){
//            transaction.rollback();
//            e.printStackTrace();
//            throw e;
//        }
    }

    /**
     * 根据teamVO，projectVO获取TeamProjectVO对象
     *
     * @param teamVO
     * @param projectVO
     * @return
     * @throws Exception
     */
    public TeamProjectVO getTeamProjectVO(TeamVO teamVO, ProjectVO projectVO) throws Exception {
        if (teamVO == null || projectVO == null) {
            throw new NullPointerException("ERROR:teamVO==null||projectVO==null---" + this.getClass() + "---getTeamProjectVO()");
        } else {
            Session session = BeanFactory.getSessionFactory().getCurrentSession();
            String hql = "from TeamProjectVO as tp where tp.teamVO.id = :teamId and tp.projectVO.id = :projectId";
            Query query = session.createQuery(hql);
            query.setParameter("teamId", teamVO.getId());
            query.setParameter("projectId", projectVO.getId());
            Iterator iterator = query.iterate();
            if (iterator.hasNext()) {
                TeamProjectVO teamProjectVO = (TeamProjectVO) iterator.next();
                return teamProjectVO;
            } else {
                return null;
            }
        }
    }

    /**
     * 获取项目的github地址
     * @param githubURL
     * @return
     * @throws Exception
     */
    public ProjectVO getByGithubURL(String githubURL) throws Exception {
        if (githubURL == null || githubURL.equals("")) {
            throw new SQLException("githubURL输入异常" + this.getClass() + "getByGithubURL()");
        } else {
            List<ProjectVO> projectVOS = (ArrayList<ProjectVO>)
                    hibernateTemplate.findByNamedParam(
                            "select p from  ProjectVO p where p.githubURL = :githubURL", "githubURL", githubURL
                    );
            if (!projectVOS.isEmpty()) {
                return projectVOS.get(0);
            } else {
                throw new NullPointerException("没找到项目：" + this.getClass() + "getByGithubURL()");
            }
        }
    }

    /**
     * 更新项目信息
     *
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ProjectVO updateProjectVO(ProjectVO projectVO) throws Exception {
        if (projectVO != null) {
            hibernateTemplate.update(projectVO);
            return projectVO;
        } else {
            throw new NullPointerException("projectVO为空");
        }
    }

    public void delete(ProjectVO projectVO) throws Exception {
        hibernateTemplate.delete(projectVO);
    }

    public void delete(TeamProjectVO teamProjectVO) throws Exception {
        hibernateTemplate.delete(teamProjectVO);
    }

    public void delete(TeamProjectAccessVO teamProjectAccessVO) throws Exception {
        hibernateTemplate.delete(teamProjectAccessVO);
    }

    /**
     * 获取一个项目团队所有的任务评价
     *
     * @param teamVO
     * @param projectVO
     * @return
     * @throws Exception
     */
    public List<TeamProjectAccessVO> getTeamProjectAccessVO(TeamVO teamVO, ProjectVO projectVO) throws Exception {
        TeamProjectVO teamProjectVO = getTeamProjectVO(teamVO, projectVO);
        List<TeamProjectAccessVO> teamProjectAccessVOList = (List<TeamProjectAccessVO>)
                hibernateTemplate.find("select tpa from TeamProjectAccessVO tpa " +
                                "where tpa.team_project_id = ?"
                        , teamProjectVO.getId());
        if (teamProjectAccessVOList == null)
            teamProjectAccessVOList = new ArrayList<>();
        return teamProjectAccessVOList;
    }

    public void delete(List<TeamProjectAccessVO> list) throws Exception {
        if (list == null || list.size() < 1)
            hibernateTemplate.deleteAll(list);
    }

    /**
     * 删除一个项目下所有的任务
     *
     * @param projectVO
     * @throws Exception
     */
    public void deleteTaskByProjectVO(ProjectVO projectVO) throws Exception {
        if (projectVO == null) {
            throw new NullPointerException("projectVO为空");
        }
        hibernateTemplate.deleteAll(
                hibernateTemplate.find("from ProjectTaskVO pt where pt.projectVO.id = ?", projectVO.getId())
        );
    }

    /**
     * 删除一个项目下的所有计划
     *
     * @param projectVO
     * @throws Exception
     */
    public void deletePlanByProjectVO(ProjectVO projectVO) throws Exception {
        if (projectVO == null) {
            throw new NullPointerException("projectVO为空");
        }
        hibernateTemplate.deleteAll(
                hibernateTemplate.find("from StudentProjectPlanVO spt where spt.projectVO.id = ?", projectVO.getId())
        );
    }

    /**
     * 删除一个项目下的所有文件
     *
     * @param projectVO
     * @throws Exception
     */
    public void deleteFileByProjectVO(ProjectVO projectVO) throws Exception {
        if (projectVO == null) {
            throw new NullPointerException("projectVO为空");
        }
        hibernateTemplate.deleteAll(
                hibernateTemplate.find("from StudentProjectFileVO spf where spf.projectVO.id = ?", projectVO.getId())
        );
    }

    /**
     * 删除一个项目下的所有任务评价——access
     *
     * @param projectVO
     * @throws Exception
     */
    public void deleteTeamProjectAccess(ProjectVO projectVO) throws Exception {
        List<TeamProjectVO> teamProjectVOList = (List<TeamProjectVO>)
                hibernateTemplate.find("from TeamProjectVO tp where tp.projectVO.id = ?", projectVO.getId());
        List<TeamProjectAccessVO> teamProjectAccessVOList = new ArrayList<>();
        for (TeamProjectVO teamProjectVO : teamProjectVOList) {
            teamProjectAccessVOList.addAll(
                    (List<TeamProjectAccessVO>) hibernateTemplate.find("from TeamProjectAccessVO tpa where tpa.team_project_id = ?", teamProjectVO.getId())
            );
        }
        hibernateTemplate.deleteAll(teamProjectAccessVOList);
    }

    /**
     * 删除一个项目下所有评价标准
     *
     * @param projectVO
     * @throws Exception
     */
    public void deleteProjectAccessType(ProjectVO projectVO) throws Exception {
        if (projectVO == null)
            throw new NullPointerException("项目为空");
        List<ProjectAccessTypeVO> projectAccessTypeVOList;
        projectAccessTypeVOList = (List<ProjectAccessTypeVO>)
                hibernateTemplate.find("from ProjectAccessTypeVO pat where pat.projectVO.id = ?", projectVO.getId());
        hibernateTemplate.deleteAll(projectAccessTypeVOList);
    }

    /**
     * 删除一个项目下所有的项目团队
     *
     * @param projectVO
     * @throws Exception
     */
    public void deleteTeamProjectByProject(ProjectVO projectVO) throws Exception {
        if (projectVO == null)
            throw new NullPointerException("项目为空");
        List<TeamProjectVO> teamProjectVOList = (List<TeamProjectVO>)
                hibernateTemplate.find("from TeamProjectVO tp where tp.projectVO.id = ?", projectVO.getId());
        hibernateTemplate.deleteAll(teamProjectVOList);
    }

    /**
     * 获得项目的创建者
     *
     * @param projectVO
     * @return
     * @throws Exception
     */
    public UserVO getCreatorVO(ProjectVO projectVO) throws Exception {
        List<UserVO> userVOS = (List<UserVO>)
                hibernateTemplate.find("select p.creatorUserVO from ProjectVO p " +
                        " where p.id = ? ", projectVO.getId());
        if (userVOS.size() > 0) {
            return userVOS.get(0);
        }
        return null;
    }


    /**
     * 获取团队接的所有项目
     * @param teamVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getProjectByTeam(TeamVO teamVO) throws Exception{
        if(teamVO == null){
            throw new NullPointerException("teamVO is null---"+this.getClass().getName()+"----getProjectByTeam()");
        }else{
            return (ArrayList<ProjectVO>) hibernateTemplate.find("select tp.projectVO from TeamProjectVO tp where tp.teamVO = ?",teamVO);
        }
    }
}
