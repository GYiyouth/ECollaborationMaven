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

/**
 * Created by GR on 2017/2/26.
 */
public class TeamDAO {

    /**
     * 创建团队
     * @param teamVO
     * @return TeamVO/null
     */
    public TeamVO createTeam(TeamVO teamVO, StudentVO studentVO){
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
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
        return teamVO;
    }

    /**
     * 申请加入团队
     * @param senderUserVO
     * @param receiverUserVO
     * @param teamVO
     * @return success/fail
     */
    public String applyJoinTeam(UserVO senderUserVO, UserVO receiverUserVO, TeamVO teamVO){
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
            return "fail";
        }
    }

    /**
     * 根据teamId获取teamVO
     * @param teamId
     * @return teamVO/null
     */
    public TeamVO getTeamVOByTeamId(Integer teamId){
        if(teamId==null||teamId.equals("")){
            System.out.println("teamId is null---"+this.getClass()+"---getTeamVOByTeamId()");
            return null;
        }else{
            ApplicationContext context = BeanFactory.getApplicationContext();
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            TeamVO teamVO = session.get(TeamVO.class, teamId);
            if(teamVO==null){
                return null;
            }else{
                return teamVO;
            }
        }
    }

    /**
     * 根据teamId获组长的studentVO
     * @param teamId
     * @return studentVO/null
     */
    public StudentVO getLeaderStudentVOByTeamId(Integer teamId){
        if(teamId==null||teamId.equals("")){
            System.out.println("teamId is null---"+this.getClass()+"---getLeaderStudentVOByTeamId()");
            return null;
        }else {
            SessionFactory sf = BeanFactory.getSessionFactory();
            Session session = sf.openSession();
            String hql = "select studentVO from StudentTeamVO as studentTeam where studentTeam.leaderFlag = true";
            Query query = session.createQuery(hql);
            Iterator iterator = query.iterate();
            if (iterator.hasNext()){
                StudentVO studentVO = (StudentVO)iterator.next();
                return studentVO;
            }else{
                System.out.println("没找到studentVO---"+this.getClass()+"---getLeaderStudentVOByTeamId()");
                return null;
            }
        }
    }

    /**
     * 根据id获取某人加入的所有团队
     * @param id
     * @return ArrayList<TeamVO>:teamVOS / null(其中没查到返回非null，arrayList.size()==0)
     */
    public ArrayList<TeamVO> getMyJoinTeamsByStudentId(Integer id){
        if(id==null||id.equals("")){
            System.out.println("id is null---"+this.getClass()+"---getMyJoinTeamsByStudentId()");
            return null;
        }else {
            ArrayList<TeamVO> teamVOS = BeanFactory.getApplicationContext().getBean("arrayList",ArrayList.class);
            SessionFactory sessionFactory = BeanFactory.getSessionFactory();
            Session session = sessionFactory.openSession();
            String hql = "select teamVO from StudentTeamVO as st where st.studentVO.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id",id);
            Iterator iterator = query.list().iterator();
            while (iterator.hasNext()){
                TeamVO teamVO = (TeamVO) iterator.next();
                teamVOS.add(teamVO);
            }
            return teamVOS;
        }
    }

}
