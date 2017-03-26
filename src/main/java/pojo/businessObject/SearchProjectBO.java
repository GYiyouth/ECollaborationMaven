package pojo.businessObject;

import pojo.DAO.TeacherDAO;
import pojo.valueObject.DTO.ProjectDTO;
import pojo.valueObject.DTO.TeacherDTO;
import pojo.valueObject.domain.TeacherVO;
import tool.BeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  搜索项目的BO，功能是输入一个字符串，返回一个ArrayList，有序放置着搜索到的ProjectDTO
 * Created by geyao on 2017/3/11.
 */
public class SearchProjectBO {
    private String rawKeyWord;
    private ArrayList<String> rawKeyWordList = new ArrayList<>();
    private ArrayList<Integer> gradeList = new ArrayList<>();
//    private ArrayList<String> userName = new ArrayList<>();
    private ArrayList<String> tecWordList = new ArrayList<>();
    private ArrayList<String> otherWordList = new ArrayList<>();

    //以下字段是我提前设定好的
    private static ArrayList<TeacherDTO> dbTeacherDTOList = new ArrayList<>();
    private static ArrayList<String> dbTecWordList = new ArrayList<>();

    static {
        TeacherDAO teacherDAO = BeanFactory.getBean("teacherDAO", TeacherDAO.class);
        HashMap<Integer, TeacherVO> dbTeacherIdVOMap = teacherDAO.getAllTeacher();
        //数据库是有老师的
        if (dbTeacherIdVOMap != null && dbTeacherIdVOMap.size() > 0){
            for (TeacherVO teacherVO : dbTeacherIdVOMap.values()){
                TeacherDTO teacherDTO = BeanFactory.getBean("teacherDTO", TeacherDTO.class);
                teacherDTO.clone(teacherVO);
                dbTeacherDTOList.add(teacherDTO);
            }
            System.out.println("dbTeacherDTOList = " + dbTeacherDTOList);
        }

        //关键词的集合
        String[] temp = {"java", "c", "c++", "c#", "go", "ruby", "javascript", "python", "php",
                "mysql", "oracle", "sql", "sqlserver", "mongodb", "db2", "sqlite",
                "机器学习", "深度学习",  "web", "javaweb", "web"
                ,".net", "前端", "前台","后端", "大数据", "数据挖掘", "多媒体", "云计算", "云", "tomcat", "搜索", "信息安全", "it", "浏览器",
                "ios", "苹果", "移动", "android", "安卓", "游戏", "服务器", "linux", "windows", "unix", "network", "网络编程", "unity"
                ,"struts", "struts2", "hibernate","hibernate5", "springmvc", "mybatis", "spring",
                "hadoop", "xml", "html", "html5","网页"};
        for (String a : temp){
            dbTecWordList.add(a);
        }

    }

    public ArrayList<ProjectDTO> search(String keyWord){
        this.rawKeyWord = keyWord;
        splitKorWord();
        return null;
    }

    public void splitKorWord(){
        this.rawKeyWord = this.rawKeyWord.toLowerCase().trim().replaceAll("\\pP|\\pS", " ");
    }

}
