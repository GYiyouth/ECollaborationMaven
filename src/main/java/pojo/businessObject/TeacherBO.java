package pojo.businessObject;

import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import pojo.DAO.TeacherDAO;
import pojo.valueObject.DTO.TeacherDTO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by geyao on 2017/2/27.
 */
@Transactional
public class TeacherBO {

    /**
     * 更新教师信息，返回json对象中带有"teacherBean"
     * @param newTeacherVO
     * @return
     */
    public JSONObject updateTeacherInfo(TeacherVO newTeacherVO) throws Exception{
        TeacherDAO teacherDAO = BeanFactory.getApplicationContext().getBean("teacherDAO", TeacherDAO.class);
        JSONObject jsonObject = BeanFactory.getApplicationContext().getBean("jsonObject", JSONObject.class);
        TeacherVO vo = teacherDAO.updateTeacherInfo(newTeacherVO);
        if (vo != null){
            TeacherDTO teacherDTO = BeanFactory.getApplicationContext().getBean("teacherDTO", TeacherDTO.class);
            teacherDTO.clone(vo);
            jsonObject.put("result", "success");
            jsonObject.put("teacherBean", teacherDTO);
        }
        return jsonObject;
    }
}
