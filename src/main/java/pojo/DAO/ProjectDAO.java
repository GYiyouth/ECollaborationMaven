package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.assist.StudentTeamVO;
import pojo.valueObject.assist.TeamProjectVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.TeamVO;
import tool.BeanFactory;

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
}
