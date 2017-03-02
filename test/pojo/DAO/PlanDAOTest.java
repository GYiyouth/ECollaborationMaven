package pojo.DAO;

import org.junit.Test;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import static org.junit.Assert.*;

/**
 * Created by geyao on 2017/3/2.
 */
public class PlanDAOTest {
    @Test
    public void getPlanVOList() throws Exception {
        PlanDAO planDAO = BeanFactory.getBean("planDAO", PlanDAO.class);
        StudentDAO studentDAO = BeanFactory.getBean("studentDAO", StudentDAO.class);
        StudentVO studentVO = studentDAO.getStudentInfoByStudentId(2);
        PlanVO planVO = BeanFactory.getBean("planVO", PlanVO.class);
        planVO.setTitle("这是一个测试的计划6");
        planVO.setContent("测试，新建一个plan，然后加给2项目，2学生");
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        ProjectVO projectVO = projectDAO.getProjectVO(2);
        planVO.setStudentVO(studentVO);
        planDAO.addPlan(planVO, projectVO);

        System.out.println(planDAO.getPlanVOList(studentVO, 2).size());
    }

    @Test
    public void addPlan() throws Exception {

    }

}