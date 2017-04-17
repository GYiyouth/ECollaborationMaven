package action.com.project;

import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Controller;
import pojo.DAO.ProjectDAO;
import pojo.DAO.TeacherDAO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.DTO.TeacherDTO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;
import tool.SessionTools;
import tool.StringCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * form里要有keyWord，这个可以是字符串，我会分析
 * 搜索的团队年限是2000～2050年
 * form里只需要有keyWord
 * 返回
 *
 * show1TimesProjectBeans~show4TimesProjectBeans
 * 四个ArrayList表，1表示至少匹配了一次
 * Created by geyao on 2016/12/31.
 * Created by geyao on 2017/3/2.
 */
@Controller
public class SearchProjectAction implements SessionAware, ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map session;


    private String keyWord;

    private HashMap<Integer, ProjectDTO> SearchResultHashMap = new HashMap<>();
    private HashMap<Integer, Integer> accurateResult = new HashMap<>();
    private ArrayList<ProjectDTO> show1TimesProjectDTOs = new ArrayList<>();
    private ArrayList<ProjectDTO> show2TimesProjectDTOs = new ArrayList<>();
    private ArrayList<ProjectDTO> show3TimesProjectDTOs = new ArrayList<>();
    private ArrayList<ProjectDTO> show4TimesProjectDTOs = new ArrayList<>();

    //以下是搜索关键字的列表
    private ArrayList<Integer> gradeList = new ArrayList<>();
    private ArrayList<String> ProNameList = new ArrayList<>();
    private ArrayList<String> keyWordList = new ArrayList<>();
    private ArrayList<TeacherVO> teacherList = new ArrayList<>();
    private ArrayList<String> studentList = new ArrayList<>();
    private ArrayList<String> remainList = new ArrayList<>();
    private boolean gradeFlag = false;
    private boolean proNameFlag = false;
    private boolean keyWordFlag = false;
    private boolean teacherFlag = false;
    private boolean studentFlag = false;


    private static HashMap<Integer, TeacherVO> teacherVOHashMap = new HashMap<>();
    private static ArrayList<String> keyWordListDB = new ArrayList<>();

    static {
        TeacherDAO teacherDAO = BeanFactory.getBean("teacherDAO", TeacherDAO.class);
        try {
            teacherVOHashMap = teacherDAO.getAllTeacher();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关键词的集合
        String[] temp = {"java", "c", "c++", "c#", "机器学习", "深度学习", "python", "php", "web", "javaweb", "web"
                ,".net", "前端", "后端", "大数据", "数据挖掘", "多媒体", "云计算", "云", "tomcat", "搜索", "信息安全", "it", "浏览器",
                "ios", "苹果", "移动", "android", "安卓", "游戏", "服务器", "linux", "windows", "unix", "network", "网络编程", "unity"
                ,"struts", "struts2", "hibernate", "spring", "hadoop", "xml", "html", "html5","网页"};
        for (String a : temp){
            keyWordListDB.add(a);
        }


    }


    //网页端查询
    public String execute() throws Exception {
        System.out.println(keyWord);
        try {
            this.setSearchResultHashMap(analyse(getKeyWord()));
            this.session = SessionTools.removeAttribute(session, "SearchResultHashMap");
            this.session.put("SearchResultHashMap", getSearchResultHashMap());
            System.out.println("搜索结果是"+getSearchResultHashMap());
            this.show1TimesProjectDTOs.removeAll(show2TimesProjectDTOs);
            this.show2TimesProjectDTOs.removeAll(show3TimesProjectDTOs);
            this.show3TimesProjectDTOs.removeAll(show4TimesProjectDTOs);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    public void appExecute() throws Exception{
        JSONObject jsonObject = BeanFactory.getJSONO();
        String result = execute();
        if (result.equals("success")){
            jsonObject.put("result", "success");
//			ArrayList<ProjectRichBean> projectRichBeanArrayList = new ArrayList<>();
//			for (ProjectBean projectBean: getSearchResultHashMap().values()){
//				ProjectRichBean projectRichBean = (ProjectRichBean) projectBean;
//
//			}
            ArrayList<ProjectDTO> arrayList = new ArrayList<>();
            for (ProjectDTO projectBean : getSearchResultHashMap().values()){
                arrayList.add(projectBean);
            }
            ArrayList<ProjectDTO> list = new ArrayList<>();
            list.addAll(show4TimesProjectDTOs);
            list.addAll(show3TimesProjectDTOs);
            list.addAll(show2TimesProjectDTOs);
            list.addAll(show1TimesProjectDTOs);
            jsonObject.put("projectsList", list);
            jsonObject.put("show1TimesProjectBeans", show1TimesProjectDTOs);
            jsonObject.put("show2TimesProjectBeans", show2TimesProjectDTOs);
            jsonObject.put("show3TimesProjectBeans", show3TimesProjectDTOs);
            jsonObject.put("show4TimesProjectBeans", show4TimesProjectDTOs);
            System.out.println("jsonObject" + jsonObject.toString());
            this.response.getWriter().write(jsonObject.toString());
            this.response.getWriter().flush();
            this.response.getWriter().close();
        }else {
            jsonObject.put("result", "fail");
            this.response.getWriter().write(jsonObject.toString());
            this.response.getWriter().flush();
            this.response.getWriter().close();
        }
    }

    private HashMap<Integer, ProjectDTO> analyse(String keyWord) throws Exception {
        if (keyWord == null )
            return new HashMap<>();
        this.keyWord = keyWord.trim().toLowerCase();
        if (this.keyWord.equals(""))
            return new HashMap<>();

        this.keyWord = this.keyWord.replace(",", " ");
        this.keyWord = this.keyWord.replace("，", " ");
        String[] elements = this.keyWord.split(" ");
        for (String a : elements)
            System.out.println("输入的关键词是" + a);
        for (String element : elements){
            if (element.equals(" ") || element.equals("")|| element.equals(","))
                continue;
            boolean tempGrade = analyseGrade(element);
            if (tempGrade)
                continue;
            boolean tempkey = analyseKeyWord(element);
            boolean tempTeacher = analyseTeacher(element);
            boolean tempProName = analyseProjectName(element);
            if (tempGrade || tempkey || tempProName || tempTeacher){
                continue;
            }else{
                remainList.add(element);
            }
        }
        ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
        //仅获取校园工程实践
        return createResult(projectDAO.getAllProject());
    }

    private HashMap<Integer, ProjectDTO> createResult(HashMap<Integer, ProjectVO> db){
        HashMap<Integer, ProjectDTO> result1 = new HashMap<>();
        HashMap<Integer, ProjectDTO> result2 = new HashMap<>();
        HashMap<Integer, ProjectDTO> result3 = new HashMap<>();
        HashMap<Integer, ProjectDTO> result4 = new HashMap<>();
        for ( ProjectVO tempProjectVO :
                db.values() ){
            if (tempProjectVO == null)
                continue;
            //检索年份
            ProjectDTO tempProjectDTO = BeanFactory.getBean("projectDTO", ProjectDTO.class);
            tempProjectDTO.clone(tempProjectVO);
            if (gradeFlag == true){
                for ( int grade : gradeList ){
                    if (tempProjectVO.getGrade()!= null && tempProjectVO.getGrade() == grade)
                        result1.put(tempProjectVO.getId(), tempProjectDTO);
                }
            }
            //检索教师
            if (teacherFlag == true) {
                for (TeacherVO bean : teacherList) {
                    if (tempProjectVO.getTeacherVO()!= null && tempProjectVO.getTeacherVO().getId().equals(bean.getId()))
                        result2.put(tempProjectVO.getId(), tempProjectDTO);
                }
            }
            //检索关键词
            if (keyWordFlag == true){
                for (String key : keyWordList){
                    key = key.toLowerCase();
                    if (       ( tempProjectVO.getRequirement() != null && tempProjectVO.getRequirement().toLowerCase().contains(key) )
                            || (tempProjectVO.getName() != null && tempProjectVO.getName().toLowerCase().contains(key) )
                            || (tempProjectVO.getGain() != null && tempProjectVO.getGain().toLowerCase().contains(key) )
                            || (tempProjectVO.getInfo() != null && tempProjectVO.getInfo().toLowerCase().contains(key) )
                            || (tempProjectVO.getKeyWord() != null && tempProjectVO.getKeyWord().toLowerCase().contains(key)) )
                        result3.put(tempProjectVO.getId(), tempProjectDTO);
                }
            }
            //最后在每个项目的名字里匹配
            if (proNameFlag == true){
                for (String name : this.ProNameList){
                    name = name.toLowerCase();
                    if (tempProjectVO.getName() != null && tempProjectVO.getName().toLowerCase().contains(name)){
                        result4.put(tempProjectVO.getId(), tempProjectDTO);
                    }
                }
            }
        }
        //合并结果
        HashMap<Integer, ProjectDTO> result = new HashMap<>();
        result.putAll(result1);
        result.putAll(result2);
        result.putAll(result3);
        result.putAll(result4);
//		HashMap<Integer, Integer> hashMap = new HashMap<>();
        this.show1TimesProjectDTOs.addAll(result.values());
        createAccurateResult(result1);
        createAccurateResult(result2);
        createAccurateResult(result3);
        createAccurateResult(result4);
        for ( Map.Entry map : this.accurateResult.entrySet()){
            switch ( (Integer) map.getValue() ){
                case 1:
                    break;
                case 2:{
                    int projectId = (Integer) map.getKey();
                    ProjectDTO ProjectDTO = result.get(projectId);
                    this.show2TimesProjectDTOs.add(ProjectDTO);
                }break;
                case 3:{
                    int projectId = (Integer) map.getKey();
                    ProjectDTO ProjectDTO = result.get(projectId);
                    this.show3TimesProjectDTOs.add(ProjectDTO);
                }break;
                case 4:{
                    int projectId = (Integer) map.getKey();
                    ProjectDTO ProjectDTO = result.get(projectId);
                    this.show4TimesProjectDTOs.add(ProjectDTO);
                }break;
            }
        }
        return result;
    }



    //遍历子结果，把分析结果再放在线性表里，key为项目id，value为出现次数，越高，越精确
    private void createAccurateResult(HashMap<Integer, ProjectDTO> result){
        for (Map.Entry map : result.entrySet()){
            int projectId = (Integer) map.getKey();
            if (this.accurateResult.containsKey(projectId)){

                this.accurateResult.replace(projectId,
                        this.accurateResult.get(projectId) +1 );
            }else {
                this.accurateResult.put(projectId, 1);
            }
        }
    }




    private boolean analyseGrade(String element){
        if (StringCheck.isNumeric(element )){
            //如果关键字包含数字
            int grade = Integer.parseInt(element);
            if (grade >= 2050 || grade <= 2000)
                return false;
            gradeFlag = true;
            gradeList.add(grade);
            return true;
        }else
            return false;
    }

    private boolean analyseProjectName(String element){
//		ProjectDAO projectDAO = new ProjectDAOImpl();
        proNameFlag = true;
        ProNameList.add(element);
        return true;
    }

    private boolean analyseKeyWord(String element){
        if (keyWordListDB.contains(element.toLowerCase())) {
            keyWordFlag = true;
            keyWordList.add(element.toLowerCase());
            return true;
        }
        else
            return false;
    }

    private boolean analyseTeacher(String element){
        for (TeacherVO bean : teacherVOHashMap.values()){
            if (bean.getName().contains(element.toLowerCase())){
                teacherFlag = true;
                teacherList.add(bean);
            }else {
                if (StringCheck.isNumeric(element))
                    if (bean.getId() == Integer.parseInt(element)) {
                        teacherFlag = true;
                        teacherList.add(bean);
                    }
            }
        }
        return teacherFlag;
    }

    private boolean analyseStudent(String element){
        return false;
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



    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public HashMap<Integer, ProjectDTO> getSearchResultHashMap() {
        return SearchResultHashMap;
    }

    public void setSearchResultHashMap(HashMap<Integer, ProjectDTO> searchResultHashMap) {
        SearchResultHashMap = searchResultHashMap;
    }

    public HashMap<Integer, Integer> getAccurateResult() {
        return accurateResult;
    }

    public void setAccurateResult(HashMap<Integer, Integer> accurateResult) {
        this.accurateResult = accurateResult;
    }

    public ArrayList<ProjectDTO> getShow1TimesProjectDTOs() {
        return show1TimesProjectDTOs;
    }

    public void setShow1TimesProjectDTOs(ArrayList<ProjectDTO> show1TimesProjectDTOs) {
        this.show1TimesProjectDTOs = show1TimesProjectDTOs;
    }

    public ArrayList<ProjectDTO> getShow2TimesProjectDTOs() {
        return show2TimesProjectDTOs;
    }

    public void setShow2TimesProjectDTOs(ArrayList<ProjectDTO> show2TimesProjectDTOs) {
        this.show2TimesProjectDTOs = show2TimesProjectDTOs;
    }

    public ArrayList<ProjectDTO> getShow3TimesProjectDTOs() {
        return show3TimesProjectDTOs;
    }

    public void setShow3TimesProjectDTOs(ArrayList<ProjectDTO> show3TimesProjectDTOs) {
        this.show3TimesProjectDTOs = show3TimesProjectDTOs;
    }

    public ArrayList<ProjectDTO> getShow4TimesProjectDTOs() {
        return show4TimesProjectDTOs;
    }

    public void setShow4TimesProjectDTOs(ArrayList<ProjectDTO> show4TimesProjectDTOs) {
        this.show4TimesProjectDTOs = show4TimesProjectDTOs;
    }

    public ArrayList<Integer> getGradeList() {
        return gradeList;
    }

    public void setGradeList(ArrayList<Integer> gradeList) {
        this.gradeList = gradeList;
    }

    public ArrayList<String> getProNameList() {
        return ProNameList;
    }

    public void setProNameList(ArrayList<String> proNameList) {
        ProNameList = proNameList;
    }

    public ArrayList<String> getKeyWordList() {
        return keyWordList;
    }

    public void setKeyWordList(ArrayList<String> keyWordList) {
        this.keyWordList = keyWordList;
    }

    public ArrayList<TeacherVO> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<TeacherVO> teacherList) {
        this.teacherList = teacherList;
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<String> getRemainList() {
        return remainList;
    }

    public void setRemainList(ArrayList<String> remainList) {
        this.remainList = remainList;
    }

    public boolean isGradeFlag() {
        return gradeFlag;
    }

    public void setGradeFlag(boolean gradeFlag) {
        this.gradeFlag = gradeFlag;
    }

    public boolean isProNameFlag() {
        return proNameFlag;
    }

    public void setProNameFlag(boolean proNameFlag) {
        this.proNameFlag = proNameFlag;
    }

    public boolean isKeyWordFlag() {
        return keyWordFlag;
    }

    public void setKeyWordFlag(boolean keyWordFlag) {
        this.keyWordFlag = keyWordFlag;
    }

    public boolean isTeacherFlag() {
        return teacherFlag;
    }

    public void setTeacherFlag(boolean teacherFlag) {
        this.teacherFlag = teacherFlag;
    }

    public boolean isStudentFlag() {
        return studentFlag;
    }

    public void setStudentFlag(boolean studentFlag) {
        this.studentFlag = studentFlag;
    }

    public static HashMap<Integer, TeacherVO> getTeacherVOHashMap() {
        return teacherVOHashMap;
    }

    public static void setTeacherVOHashMap(HashMap<Integer, TeacherVO> teacherVOHashMap) {
        SearchProjectAction.teacherVOHashMap = teacherVOHashMap;
    }

    public static ArrayList<String> getKeyWordListDB() {
        return keyWordListDB;
    }

    public static void setKeyWordListDB(ArrayList<String> keyWordListDB) {
        SearchProjectAction.keyWordListDB = keyWordListDB;
    }
}
