package pojo.DAO;

import org.apache.struts2.components.Bean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.assist.StudentTeamVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;
import tool.MessageMould;
import tool.Time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by GR on 2017/2/26.
 */
public class TeamDAO {

    /**
     * 创建团队
     * @param teamVO
     * @return TeamVO/null
     */
    public TeamVO createTeam(TeamVO teamVO, StudentVO studentVO) throws Exception{
        ApplicationContext context = BeanFactory.getApplicationContext();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction =  session.beginTransaction();
        try{
            session.save(teamVO);
            StudentTeamVO studentTeamVO = context.getBean("studentTeamVO",StudentTeamVO.class);
            studentTeamVO.setStudentVO(studentVO);
            studentTeamVO.setTeamVO(teamVO);
            studentTeamVO.setLeaderFlag(true);
            session.save(studentTeamVO);
            transaction.commit();
            teamVO = session.get(TeamVO.class,teamVO.getId());
            return teamVO;
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * 申请加入团队
     * @param senderUserVO
     * @param receiverUserVO
     * @param teamVO
     * @return success/fail
     */
    public String applyJoinTeam(UserVO senderUserVO, UserVO receiverUserVO, TeamVO teamVO) throws Exception{
        ApplicationContext context = BeanFactory.getApplicationContext();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        MessageMould messageMould = context.getBean("messageMould",MessageMould.class);
        MessageReceiverVO messageReceiverVO = context.getBean("messageReceiverVO",MessageReceiverVO.class);
        ApplicationVO applicationVO = context.getBean("applicationVO",ApplicationVO.class);
        Transaction transaction = session.beginTransaction();
        try{
            MessageVO messageVO = messageMould.applyJoinTeamMessageVOMould(senderUserVO);
//            1.保存进message表
            session.save(messageVO);
//            2.保存进message_receiver表
            messageReceiverVO.setMessageVO(messageVO);
            messageReceiverVO.setReceiverUserVO(receiverUserVO);
            session.save(messageReceiverVO);
//            3.保存进application表
            applicationVO.setMessageVO(messageVO);
            applicationVO.setAffectedUserVO(senderUserVO);
            applicationVO.setCreatedTime(Time.getCurrentTime());
            applicationVO.setHandlerUserVO(receiverUserVO);
            applicationVO.setType("team");
            applicationVO.setTeamVO(teamVO);
            session.save(applicationVO);
            transaction.commit();
            return"success";
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * 根据teamId获取teamVO
     * @param teamId
     * @return teamVO/null
     */
    public TeamVO getTeamVOByTeamId(Integer teamId) throws Exception{
        if(teamId==null||teamId.equals("")){
            System.out.println("teamId is null---"+this.getClass()+"---getTeamVOByTeamId()");
            return null;
        }else{
            ApplicationContext context = BeanFactory.getApplicationContext();
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                TeamVO teamVO = session.get(TeamVO.class, teamId);
                if (teamVO == null) {
                    System.out.println("ERROR:teamVO is null---" + this.getClass() + "---getTeamVOByTeamId()");
                    return null;
                } else {
                    return teamVO;
                }
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 根据teamId获组长的studentVO
     * @param teamId
     * @return studentVO/null
     */
    public StudentVO getLeaderStudentVOByTeamId(Integer teamId) throws Exception{
        if(teamId==null||teamId.equals("")){
            System.out.println("teamId is null---"+this.getClass()+"---getLeaderStudentVOByTeamId()");
            return null;
        }else {
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            try {
                String hql = "select studentVO from StudentTeamVO as studentTeam where studentTeam.leaderFlag = true";
                Query query = session.createQuery(hql);
                Iterator iterator = query.iterate();
                if (iterator.hasNext()) {
                    StudentVO studentVO = (StudentVO) iterator.next();
                    return studentVO;
                } else {
                    System.out.println("没找到studentVO---" + this.getClass() + "---getLeaderStudentVOByTeamId()");
                    return null;
                }
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 根据userId获取某人加入的所有团队
     * @param id
     * @return ArrayList<TeamVO>:teamVOS / null(其中没查到返回非null，arrayList.size()==0)
     */
    public ArrayList<TeamVO> getMyJoinTeamsByStudentId(Integer id) throws Exception{
        if(id==null||id.equals("")){
            System.out.println("id is null---"+this.getClass()+"---getMyJoinTeamsByStudentId()");
            return null;
        }else {
            ArrayList<TeamVO> teamVOS = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                String hql = "select teamVO from StudentTeamVO as st where st.studentVO.id = :id";
                Query query = session.createQuery(hql);
                query.setParameter("id", id);
                Iterator iterator = query.list().iterator();
                while (iterator.hasNext()) {
                    TeamVO teamVO = (TeamVO) iterator.next();
                    teamVOS.add(teamVO);
                }
                return teamVOS;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 根据teamId获取团队的所有学生信息集合
     * @return
     */
    public ArrayList<StudentVO> getStudentVOSByTeamId(Integer teamId){
        if(teamId == null||teamId.equals("")){
            return null;
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            try{
                String hql = "select StudentVO from StudentTeamVO as st where st.teamVO.id = :teamId order by leaderFlag DESC";
                Query query = session.createQuery(hql);
                query.setParameter("teamId",teamId);
                Iterator iterator = query.iterate();
                if (iterator.hasNext()){
                    ArrayList<StudentVO> studentVOS = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
                    while(iterator.hasNext()) {
                        StudentVO studentVO = (StudentVO) iterator.next();
                        studentVOS.add(studentVO);
                    }
                    return studentVOS;
                }else{
                    System.out.println("ERROR:查询到的结果 is null!!!---"+this.getClass()+"---getStudentVOSByTeamId()");
                    return null;
                }
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 接受申请加入团队请求
     * @param applicationId
     * @return
     */
    public String acceptJoinApplication(Integer applicationId) throws Exception{
        if(applicationId==null||applicationId.equals("")){
            System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---acceptJoinApplication()");
            return "fail";
        }else{
            StudentTeamVO studentTeamVO = BeanFactory.getApplicationContext().getBean("studentTeamVO",StudentTeamVO.class);
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try{
                ApplicationVO applicationVO = session.get(ApplicationVO.class,applicationId);
                if(applicationVO==null){
                    System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---acceptJoinApplication()");
                    return "fail";
                }else{
                    UserDAO userDAO = BeanFactory.getApplicationContext().getBean("userDAO",UserDAO.class);
                    StudentVO studentVO = userDAO.getStudentVOByUserVO(applicationVO.getAffectedUserVO());
                    TeamVO teamVO = applicationVO.getTeamVO();
                    if(studentVO == null||teamVO == null){
                        System.out.println("ERROR:studentVO/teamVO is null!!!---"+this.getClass()+"---acceptJoinApplication()");
                        return "fail";
                    }else {
                        applicationVO.setResult("已处理");
                        applicationVO.setHandleTime(Time.getCurrentTime());
                        session.update(applicationVO);
                        studentTeamVO.setStudentVO(studentVO);
                        studentTeamVO.setTeamVO(teamVO);
                        studentTeamVO.setLeaderFlag(false);
                        session.save(studentTeamVO);
                        transaction.commit();
                        return "success";
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
    }

    /**
     * 拒绝加入团队请求
     * @param applicationId
     * @return
     */
    public String refuseJoinApplication(Integer applicationId) throws Exception{
        if(applicationId==null||applicationId.equals("")){
            System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---refuseJoinApplication()");
            return "fail";
        }else{
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try{
                ApplicationVO applicationVO = session.get(ApplicationVO.class,applicationId);
                if(applicationVO==null){
                    System.out.println("ERROR:applicationId is null!!!---"+this.getClass()+"---refuseJoinApplication()");
                    return "fail";
                }else{
                    TeamVO teamVO = applicationVO.getTeamVO();
                    if(teamVO == null){
                        System.out.println("ERROR:teamVO is null!!!---"+this.getClass()+"---refuseJoinApplication()");
                        return "fail";
                    }else {
                        applicationVO.setResult("已处理");
                        applicationVO.setHandleTime(Time.getCurrentTime());
                        session.update(applicationVO);
                        transaction.commit();
                        return "success";
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
    }
}
