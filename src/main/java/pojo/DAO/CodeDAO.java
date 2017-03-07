package pojo.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import tool.BeanFactory;

import java.util.Iterator;

/**
 * Created by GR on 2017/3/1.
 */
public class CodeDAO {

    /**
     * 根据学生id,项目id获取到代码总行数
     * @param studentId
     * @return
     */
    public Integer getCodeSumByStudentIdProjectId(Integer studentId, Integer projectId){
        if(studentId == null||studentId.equals("")||projectId == null||projectId.equals("")) {
            System.out.println("ERROR:studentId is null---"+this.getClass()+"---getCodeSumByStudentId()");
            return null;
        }else{
            Session session = BeanFactory.getSessionFactory().openSession();
            try{
                String hql = "select sum(c.row) from CodeVO as c where c.studentVO.id = :studentId and c.projectVO.id = :projectId";
                Query query = session.createQuery(hql);
                query.setParameter("studentId",studentId);
                query.setParameter("projectId",projectId);
                Integer sum = Integer.valueOf(query.uniqueResult().toString());
                return sum;
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                session.close();
            }
        }
    }
}
