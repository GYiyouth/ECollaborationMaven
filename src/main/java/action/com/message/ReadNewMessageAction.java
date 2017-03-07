package action.com.message;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import pojo.businessObject.MessageBO;
import pojo.valueObject.DTO.MessageDTO;
import pojo.valueObject.domain.*;
import tool.BeanFactory;
import tool.JSONHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * 根据message_receiver表，仅获取未读的消息
 * 读完会处理三张表的readFlag
 * Created by geyao on 2017/3/3.
 */
public class ReadNewMessageAction implements SessionAware, ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map session;


    private ArrayList<MessageDTO> messageDTOArrayList = new ArrayList<>();

    @SuppressWarnings("all")
    public String execute() throws Exception {
        UserVO userVO = null;
        JSONObject jsonObject = BeanFactory.getJSONO();
        if (session.containsKey("teacherVO")){
            userVO = (TeacherVO)session.get("teacherVO");
        }else if (session.containsKey("studentVO")){
            userVO = (StudentVO) session.get("studentVO");
        }else if ((session.containsKey("manager"))){
            userVO = (ManagerVO) session.get("manager");
        }else {
            JSONHandler.sendJSON(jsonObject, response);
            return "fail";
        }

        MessageBO messageBO = BeanFactory.getBean("messageBO", MessageBO.class);
        try {
            ArrayList<MessageVO> messageVOArrayList = null;
            messageVOArrayList = messageBO.readMessage(userVO);
            Iterator iterator = messageVOArrayList.iterator();
            while (iterator.hasNext()){
                MessageVO messageVO = (MessageVO)iterator.next();
                MessageDTO messageDTO = BeanFactory.getBean("messageDTO", MessageDTO.class);
                messageDTO.clone(messageVO);
                messageDTOArrayList.add(messageDTO);
            }
            String jsonProperty = "unreadMessageDTOList";
            jsonObject.put(jsonProperty, messageDTOArrayList);
            jsonObject.put("result", "success");
            JSONHandler.sendJSON(jsonObject, response);
            return "success";
        }catch (Exception e){
            JSONHandler.sendJSON(jsonObject, response);
            e.printStackTrace();
            return "fail";
        }
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        try {
            this.request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
        this.response.setCharacterEncoding("UTF-8");
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map getSession() {
        return session;
    }


    public ArrayList<MessageDTO> getMessageDTOArrayList() {
        return messageDTOArrayList;
    }

    public void setMessageDTOArrayList(ArrayList<MessageDTO> messageDTOArrayList) {
        this.messageDTOArrayList = messageDTOArrayList;
    }
}
