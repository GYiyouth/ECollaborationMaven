package pojo.businessObject;

import pojo.DAO.ApplicationDAO;
import pojo.valueObject.domain.ApplicationVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by geyao on 2017/3/2.
 */
public class ApplicationBO {

    /**
     * 获取需要处理的申请，session里添加applicationVOList
     * @param userVO
     * @param session
     * @return
     * @throws Exception
     */
    public ArrayList<ApplicationVO> getHandlingApplication(UserVO userVO, Map session) throws Exception{
        if (userVO == null || session== null )
            return null;
        try {
            //存放在session中的key
            String sessionProperty = "applicationVOList";
            ArrayList<ApplicationVO> arrayList;
            ApplicationDAO applicationDAO = BeanFactory.getBean("applicationDAO", ApplicationDAO.class);
            arrayList = applicationDAO.getApplicationVOListByHandlerVO(userVO);
            if (session.containsKey(sessionProperty))
                session.remove(sessionProperty);
            session.put(sessionProperty, arrayList);
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
