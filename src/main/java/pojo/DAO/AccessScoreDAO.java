package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.valueObject.assist.ProjectAccessTypeVO;
import pojo.valueObject.domain.ProjectVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 打分／评价相关的方法
 * Created by geyao on 2017/3/2.
 */
public class AccessScoreDAO {



    /**
     * 添加评价种类
     * 忘了考虑百分比
     * @param typeNames
     * @param projectVOList
     * @throws Exception
     */
    public void addProjectAccessType(
            ArrayList<String>typeNames, ArrayList<ProjectVO> projectVOList
            ) throws Exception{

        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try{
            Iterator iterator = projectVOList.iterator();

            //嵌套遍历完成插入
            int i = 0; // 20个写入一次
            for (String typeName: typeNames
                 ) {
                while (iterator.hasNext()){
                    ProjectVO projectVO = (ProjectVO) iterator.next();
                    ProjectAccessTypeVO projectAccessTypeVO =
                            BeanFactory.getBean("projectAccessTypeVO", ProjectAccessTypeVO.class);
                    projectAccessTypeVO.setProjectVO(projectVO);
                    projectAccessTypeVO.setType(typeName);
                    session.save(projectAccessTypeVO);
                    i++;
                    if (i >= 20){
                        session.flush();
                        i = 0;
                    }
                }
            }

            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据project查询种类
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ArrayList<String> getTypeListByProject(ProjectVO projectVO) throws Exception{
        if (projectVO == null){
            throw new NullPointerException("projectVO == null");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        SessionFactory sessionFactory = BeanFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try{
            List list = session.createCriteria(ProjectAccessTypeVO.class)
                    .add(Restrictions.eq("projectVO", projectVO))
                    .list();
            transaction.commit();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                ProjectAccessTypeVO projectAccessTypeVO = (ProjectAccessTypeVO) iterator.next();
                arrayList.add(projectAccessTypeVO.getType());
            }
            return arrayList;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
