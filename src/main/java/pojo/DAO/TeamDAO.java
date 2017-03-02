package pojo.DAO;

import org.apache.struts2.components.Bean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import pojo.valueObject.DTO.TeamDTO;
import pojo.valueObject.assist.MessageReceiverVO;
import pojo.valueObject.assist.StudentTeamVO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;
import tool.MapSort;
import tool.MessageMould;
import tool.Time;

import javax.persistence.MapsId;
import java.util.*;

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
    public String applyJoinTeam(UserVO senderUserVO, StudentVO receiverUserVO, TeamVO teamVO) throws Exception{
        ApplicationContext context = BeanFactory.getApplicationContext();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        MessageMould messageMould = context.getBean("messageMould",MessageMould.class);
        MessageReceiverVO messageReceiverVO = context.getBean("messageReceiverVO",MessageReceiverVO.class);
        ApplicationVO applicationVO = context.getBean("applicationVO",ApplicationVO.class);
        Transaction transaction = session.beginTransaction();
        try{
            teamVO = session.get(TeamVO.class,teamVO.getId());
            receiverUserVO = session.get(StudentVO.class,receiverUserVO.getId());
            if(teamVO.getMemberMax()>teamVO.getStudentVOSet().size()) {
//            TeamVO teamVO1
                MessageVO messageVO = messageMould.applyJoinTeamMessageVOMould(senderUserVO);
//            1.user中NewsFlag++
                Integer newsFlag = receiverUserVO.getNewsFlag();
                if (newsFlag == null) {
                    newsFlag = 1;
                } else {
                    newsFlag++;
                }
                receiverUserVO.setNewsFlag(newsFlag);
                session.update(receiverUserVO);
//            2.保存进message表
                session.save(messageVO);
//            3.保存进message_receiver表
                messageReceiverVO.setMessageVO(messageVO);
                messageReceiverVO.setReceiverUserVO(receiverUserVO);
                session.save(messageReceiverVO);
//            4.保存进application表
                applicationVO.setMessageVO(messageVO);
                applicationVO.setAffectedUserVO(senderUserVO);
                applicationVO.setCreatedTime(Time.getCurrentTime());
                applicationVO.setHandlerUserVO(receiverUserVO);
                applicationVO.setType("team");
                applicationVO.setTeamVO(teamVO);
                session.save(applicationVO);
                transaction.commit();
                return "success";
            }else{
                return "full";
            }
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }finally {
            session.close();
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
            }finally {
                session.close();
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
                String hql = "select studentVO from StudentTeamVO as studentTeam where studentTeam.leaderFlag = true and studentTeam.teamVO.id =:teamId";
                Query query = session.createQuery(hql);
                query.setParameter("teamId",teamId);
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
            }finally {
                session.close();
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
    public String acceptJoinApplication(Integer applicationId,StudentVO handlerStudentVO) throws Exception{
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
                        teamVO = session.get(TeamVO.class,teamVO.getId());
                        MessageVO messageVO = tool.MessageMould.acceptJoinTeamMessageVOMould(handlerStudentVO,teamVO);
                        MessageReceiverVO messageReceiverVO = BeanFactory.getApplicationContext().getBean("messageReceiverVO",MessageReceiverVO.class);
//                        1.保存进message表
                        session.save(messageVO);
//                        2.保存进message_receiver表
                        messageReceiverVO.setMessageVO(messageVO);
                        messageReceiverVO.setReceiverUserVO(studentVO);
                        session.save(messageReceiverVO);
                        studentVO = session.get(StudentVO.class,studentVO.getId());
                        Integer newsFlag = studentVO.getNewsFlag();
                        if (newsFlag == null) {
                            newsFlag = 1;
                        } else {
                            newsFlag++;
                        }
                        studentVO.setNewsFlag(newsFlag);
                        session.update(studentVO);
                        if(teamVO.getMemberMax()-teamVO.getStudentVOSet().size()<=1){
                            String hql = "select id from ApplicationVO as app where app.result is null and app.id != :applicationId and app.teamVO.id = :teamId";
                            Query query = session.createQuery(hql);
                            query.setParameter("applicationId",applicationId);
                            query.setParameter("teamId",teamVO.getId());
                            Iterator iterator = query.iterate();
                            //其他的拒绝
                            while (iterator.hasNext()) {
                                refuseJoinApplication((Integer) iterator.next(),handlerStudentVO);
                            }

                        }
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
    public String refuseJoinApplication(Integer applicationId,StudentVO handlerStudentVO) throws Exception{
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
                        MessageVO messageVO = tool.MessageMould.refuseJoinTeamMessageVOMould(handlerStudentVO,teamVO);
                        MessageReceiverVO messageReceiverVO = BeanFactory.getApplicationContext().getBean("messageReceiverVO",MessageReceiverVO.class);
//                        1.保存进message表
                        session.save(messageVO);
//                        2.保存进message_receiver表
                        messageReceiverVO.setMessageVO(messageVO);
                        messageReceiverVO.setReceiverUserVO(applicationVO.getAffectedUserVO());
                        session.save(messageReceiverVO);
                        applicationVO.setResult("已处理");
                        applicationVO.setHandleTime(Time.getCurrentTime());
                        session.update(applicationVO);
//                        studentVO = session.get(StudentVO.class,studentVO.getId());
                        Integer newsFlag = applicationVO.getAffectedUserVO().getNewsFlag();
                        if (newsFlag == null) {
                            newsFlag = 1;
                        } else {
                            newsFlag++;
                        }
                        applicationVO.getAffectedUserVO().setNewsFlag(newsFlag);
                        session.update(applicationVO.getAffectedUserVO());
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
     * 搜索团队
     * @param searchTeamInfo
     * @return
     * @throws Exception
     */
    public ArrayList<TeamVO> searchTeam(String searchTeamInfo) throws Exception{
        if(searchTeamInfo==null){
            throw new NullPointerException("ERROR:searchTeamInfo is null!!!---"+this.getClass()+"---searchTeam()");
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            TeamDAO teamDAO = BeanFactory.getApplicationContext().getBean("teamDAO",TeamDAO.class);
            ArrayList<TeamVO> teamVOS = new ArrayList<>();
            Map<Integer,Integer> teamSearchResult = new HashMap<>();
            //团队名类似的
            String hql1 = "from TeamVO as team where team.teamName like :searchTeamInfo";
            Query query = session.createQuery(hql1);
            query.setParameter("searchTeamInfo","%"+searchTeamInfo+"%");
            Iterator iterator = query.iterate();
            while (iterator.hasNext()){
                TeamVO teamVO = (TeamVO) iterator.next();
                if(teamSearchResult.containsKey(teamVO.getId())){
                    teamSearchResult.put(teamVO.getId(),teamSearchResult.get(teamVO.getId())+1);
                }else{
                    teamSearchResult.put(teamVO.getId(),1);
                }
            }
            //项目描述类似的
            String hql4 = "from TeamVO as team where team.description like :searchTeamInfo";
            query = session.createQuery(hql4);
            query.setParameter("searchTeamInfo","%"+searchTeamInfo+"%");
            iterator = query.iterate();
            while (iterator.hasNext()){
                TeamVO teamVO = (TeamVO) iterator.next();
                if(teamSearchResult.containsKey(teamVO.getId())){
                    teamSearchResult.put(teamVO.getId(),teamSearchResult.get(teamVO.getId())+1);
                }else{
                    teamSearchResult.put(teamVO.getId(),1);
                }
            }
            //项目名类似的
            String hql2 = "select teamVO from TeamProjectVO as tp where tp.projectVO.name like :searchTeamInfo";
            query = session.createQuery(hql2);
            query.setParameter("searchTeamInfo","%"+searchTeamInfo+"%");
            iterator = query.iterate();
            while (iterator.hasNext()){
                TeamVO teamVO = (TeamVO) iterator.next();
                if(teamSearchResult.containsKey(teamVO.getId())){
                    teamSearchResult.put(teamVO.getId(),teamSearchResult.get(teamVO.getId())+1);
                }else{
                    teamSearchResult.put(teamVO.getId(),1);
                }
            }
            //组员名字类似
            String hql3 = "select teamVO from StudentTeamVO as st where st.studentVO.name like :searchTeamInfo";
            query = session.createQuery(hql3);
            query.setParameter("searchTeamInfo","%"+searchTeamInfo+"%");
            iterator = query.iterate();
            while (iterator.hasNext()){
                TeamVO teamVO = (TeamVO) iterator.next();
                if(teamSearchResult.containsKey(teamVO.getId())){
                    teamSearchResult.put(teamVO.getId(),teamSearchResult.get(teamVO.getId())+1);
                }else{
                    teamSearchResult.put(teamVO.getId(),1);
                }
            }

            //排序
            teamSearchResult = MapSort.sortMap(teamSearchResult);
            for (Integer key : teamSearchResult.keySet()) {
                TeamVO teamVO = teamDAO.getTeamVOByTeamId(key);
                teamVOS.add(teamVO);
            }
            Set<TeamVO> teamVOSet = new HashSet<>();
            teamVOSet.addAll(teamVOS);
            ArrayList<TeamVO> teamVOS2 = new ArrayList<>();
            teamVOS2.addAll(teamVOSet);
            return teamVOS2;
        }
    }
}
