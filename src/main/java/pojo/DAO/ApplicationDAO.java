package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyao on 2017/3/2.
 */
public class ApplicationDAO {

    /**
     * 添加计划
     * @param applicationVO
     * @return
     * @throws Exception
     */
    public ApplicationVO addApplication(ApplicationVO applicationVO) throws Exception{
        if (applicationVO == null)
            return null;
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(applicationVO);
            return applicationVO;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }

    /**
     * 获取一个UserVO要处理的计划
     * @param handlerVO
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getApplicationVOListByHandlerVO(UserVO handlerVO) throws Exception{
        if (handlerVO == null){
            return null;
        }
        return comGetApplicationVOList("handlerUserVO", handlerVO);
    }

    /**
     * 获取被影响的人的application，大多是申请人
     * @param userVO
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getApplicationVOListByAffctedVO(UserVO userVO) throws Exception{
        if (userVO == null){
            return null;
        }
        return comGetApplicationVOList("affectedUserVO", userVO);
    }

    /**
     * 获取application的列表，以teamVO为条件
     * @param teamVO
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getApplicationVOListByTeamVO(TeamVO teamVO) throws Exception{
        if (teamVO == null)
            return null;
        return comGetApplicationVOList("teamVO", teamVO);
    }

    /**
     * 获取application的列表，以projectVO为条件
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getApplicationVOListByProjecVO(ProjectVO projectVO) throws Exception{
        if (projectVO == null)
            return null;
        return comGetApplicationVOList("projectVO", projectVO);
    }



    /**
     * 通用的查询方法，按createdTime生序降序
     * @param type, o
     * @return
     * @throws Exception
     */
    private ArrayList<ApplicationVO> comGetApplicationVOList(String type, Object o) throws Exception{
        if (o == null){
            return null;
        }
        ArrayList<ApplicationVO> arrayList = new ArrayList<>();
        SessionFactory sessionFactor = BeanFactory.getSessionFactory();
        Session session = sessionFactor.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try{
            List list;
            list = session.createCriteria(ApplicationVO.class)
                        .add(Restrictions.eq(type, o))
                        .addOrder(Order.desc("createdTime"))
                        .list();
            transaction.commit();
            arrayList.addAll(list);
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
    }
}
