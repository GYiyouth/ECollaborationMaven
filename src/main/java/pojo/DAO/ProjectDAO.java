package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.assist.StudentTeamVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;
import tool.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by geyao on 2017/3/1.
 */
public class ProjectDAO {

    /**
     * 获取项目，根据id
     * @param id
     * @return
     * @throws Exception
     */
    public ProjectVO getProjectVO(Integer id) throws Exception{
        if (id == null || id < 0)
            return null;
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
        try{
//            return session.get(ProjectVO.class, id);
            ProjectVO projectVO = session.get(ProjectVO.class, id);
            return projectVO;
        }catch (Exception e){
            e.printStackTrace();
//            transaction.rollback();
            throw e;
        }finally {
//            session.close();
        }
    }

    /**
     * 增加项目，不处理与team的关系表
     * @param projectVO
     * @return
     */
    public ProjectVO addProjectVO(ProjectVO projectVO) throws Exception{
        if (projectVO == null)
            return null;
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(projectVO);
            transaction.commit();
            return projectVO;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 得到teacher的全部project
     * @param teacherVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOList(TeacherVO teacherVO) throws Exception{
        return null;
    }

    /**
     * 得到teacher某一特定年份的project
     * @param teacherVO
     * @param grade
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getTeacherProjectVOListByGrade (TeacherVO teacherVO, Integer grade) throws Exception{
        return null;
    }

    /**
     * 根据学生VO查询projectVOList
     * @param studentVO
     * @return
     * @throws Exception
     */
    public ArrayList<ProjectVO> getStudentProjectVOList(StudentVO studentVO) throws Exception {
        if (studentVO == null)
            throw new NullPointerException("studentVO == null");

        ArrayList<ProjectVO> arrayList = new ArrayList<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<StudentTeamVO> STlist = session.createCriteria(StudentTeamVO.class)
                    .add(Restrictions.eq("studentVO", studentVO))
                    .list();
            Iterator sTiterator = STlist.iterator();
            //放置TeamVO
            ArrayList<TeamVO> teamVOArrayList = new ArrayList<>();
            while (sTiterator.hasNext()){
                StudentTeamVO studentTeamVO = (StudentTeamVO)sTiterator.next();
                teamVOArrayList.add(studentTeamVO.getTeamVO());
            }
            List<TeamProjectVO> TPList = session.createCriteria(TeamProjectVO.class)
                    .add(Restrictions.in("teamVO", teamVOArrayList))
                    .list();
            transaction.commit();
            System.out.println(STlist);
            System.out.println(TPList);
            Iterator iterator = TPList.iterator();
            while (iterator.hasNext()) {
                TeamProjectVO teamProjectVO = (TeamProjectVO) iterator.next();

                ProjectVO projectVO = teamProjectVO.getProjectVO();
                arrayList.add(projectVO);
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 根据年纪获取所有项目
     * @param grade
     * @return
     * @throws Exception
     */
    public HashMap<Integer,ProjectVO> getAllProject(int grade) throws Exception{
        HashMap<Integer, ProjectVO> hashMap = new HashMap<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List projectVOList = session.createCriteria(ProjectVO.class)
                    .add(Restrictions.eq("grade", grade))
                    .list();
            Iterator iterator = projectVOList.iterator();
            transaction.commit();
            while (iterator.hasNext()){
                ProjectVO projectVO = (ProjectVO) iterator.next();
                hashMap.put(projectVO.getId(), projectVO);
            }
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取所有项目
     * @return
     * @throws Exception
     */
    public HashMap<Integer,ProjectVO> getAllProject() throws Exception{
        HashMap<Integer, ProjectVO> hashMap = new HashMap<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List projectVOList = session.createCriteria(ProjectVO.class)
                    .list();
            Iterator iterator = projectVOList.iterator();
            transaction.commit();
            while (iterator.hasNext()){
                ProjectVO projectVO = (ProjectVO) iterator.next();
                hashMap.put(projectVO.getId(), projectVO);
            }
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 申请项目
     * 添加application
     * 添加message
     * 改变相关人员的NewsFlag
     * @param teamId
     * @param projectId
     * @throws Exception
     */
    public void applyProject(Integer teamId, Integer projectId) throws Exception{

        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            TeamVO teamVO = session.get(TeamVO.class, teamId);
            ProjectVO projectVO = session.get(ProjectVO.class, projectId);
            List oldList = session.createCriteria(ApplicationVO.class)
                    .add(Restrictions.eq("teamVO", teamVO))
                    .add(Restrictions.eq("projectVO", projectVO))
                    .list();
            if (oldList.size() >=1 )
                throw new Exception("已有存在的申请");

            //处理人优先让老师申请，没有老师，则是学生申请
            UserVO handleUserVO = null;
            handleUserVO = projectVO.getTeacherVO();
            if (handleUserVO == null){
                handleUserVO = projectVO.getCreatorUserVO();
            }
            StudentVO teamLeader = teamVO.getCreatorStudentVO();

            ArrayList<StudentVO> affectedStudentVOList = new ArrayList<>();
            affectedStudentVOList.addAll(teamVO.getStudentVOSet());

            if (teamVO == null || projectVO == null)
                throw new NullPointerException("teamVO == null || projectVO == null");
            ApplicationVO applicationVO = BeanFactory.getBean("application", ApplicationVO.class);
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
            session.save(messageVO);

            Integer newsFlag = handleUserVO.getNewsFlag();
            if (newsFlag != null )
                newsFlag ++;
            else
                newsFlag = 1;
            handleUserVO.setNewsFlag(newsFlag);
            session.update(handleUserVO);

            session.save(messageVO);

            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            session.close();
        }
    }
}
