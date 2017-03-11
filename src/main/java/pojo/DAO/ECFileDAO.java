package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import tool.BeanFactory;

/**
 * Created by GR on 2017/3/2.
 */
public class ECFileDAO {

    public Integer getECFileSumByStudentId(Integer studentId){
        if(studentId == null||studentId.equals("")) {
            System.out.println("ERROR:studentId is null---"+this.getClass()+"---getCodeSumByStudentId()");
            return null;
        }else{
            Session session = BeanFactory.getSessionFactory().getCurrentSession();
            try{
                String hql = "select count(*) from ECFileVO as ecf where ecf.creatorUserVO.id = :studentId";
                Query query = session.createQuery(hql);
                query.setParameter("studentId",studentId);
                Integer sum = Integer.valueOf(query.uniqueResult().toString());
                return sum;
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }
}
