package pojo.DAO;

import org.junit.Test;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import static org.junit.Assert.*;

/**
 * Created by geyao on 2017/3/2.
 */
public class ProjectDAOTest {
//    @Test
//    public void getProjectVO() throws Exception {
//
//    }

    @Test
    public void addProjectVO() throws Exception {
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        ProjectVO projectVO = BeanFactory.getBean("projectVO", ProjectVO.class);
        TeacherDAO teacherDAO = BeanFactory.getBean("teacherDAO", TeacherDAO.class);
        TeacherVO teacherVO= teacherDAO.getTeacherInfo(1);
        StudentDAO studentDAO = BeanFactory.getBean("studentDAO", StudentDAO.class);
        StudentVO studentVO = studentDAO.getStudentInfoByStudentId(2);

        projectVO.setTeacherVO(teacherVO);
        projectVO.setCreatorUserVO(studentVO);
        projectVO.setName("20170302测试");

        projectDAO.addProjectVO(projectVO);

        System.out.println(projectDAO.getProjectVO(projectVO.getId()));
    }

}