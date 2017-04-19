import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.DAO.UserDAO;
import pojo.valueObject.domain.TeacherVO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

/**
 * Created by geyao on 2017/3/27.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String json = "{\"accessTypeDTOList\":[],\"studentDTOList\":[{\"activeBefore\":\"2016-12-21 13:13:13.0\",\"codeScore1\":0,\"codeScore2\":0,\"createDate\":\"2016-12-21 13:13:13.0\",\"email\":\"gr2016@mail.ustc.edu.cn\",\"finalScore\":0,\"githubLogin\":\"\",\"grade\":2017,\"graduatedSchool\":\"\",\"homePageUrl\":\"\",\"id\":13,\"isNeedProject\":0,\"isOnProject\":0,\"lastLogTime\":\"2016-12-21 13:13:13.0\",\"logName\":\"gr2016\",\"messageVOIdSet\":[54,55,58],\"name\":\"gengrui\",\"newsFlag\":3,\"passWord\":\"123\",\"phoneNumber\":\"18896987820\",\"photo\":\"\",\"presentationScore\":0,\"role\":\"student\",\"schoolId\":\"SA16225071\",\"sex\":1,\"tecKeyWord\":\"\"}],\"studentNum\":1,\"studentScore0\":[],\"result\":\"success\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        System.out.println(jsonObject.get("result"));
        int i = JSONArray.fromObject(jsonObject.get("studentDTOList")).size();
        System.out.println(i);
    }

    public static void foo(Test2 test2){

    }
}
