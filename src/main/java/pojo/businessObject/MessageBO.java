package pojo.businessObject;

import pojo.DAO.MessageDAO;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.util.ArrayList;

/**
 * Created by geyao on 2017/3/3.
 */
public class MessageBO {

    /**
     * 读取信息，在DAO里同时处理了三种表的action
     * @param userVO
     * @return
     * @throws Exception
     */
    public ArrayList<MessageVO> readMessage(UserVO userVO) throws Exception{
        MessageDAO messageDAO = BeanFactory.getBean("messageDAO", MessageDAO.class);
        return messageDAO.getUnreadMessageVO(userVO);
    }
}
