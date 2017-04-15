package pojo.DAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.valueObject.domain.ContributionsVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import tool.BeanFactory;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GR on 2017/4/12.
 */
@Repository
@Transactional
public class ContributionsDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    public void updateContributions(String githubURL) throws Exception {

        //外接口路径
        if (githubURL == null || githubURL.equals("")) {
            throw new NullPointerException("githubURL 空的" + this.getClass() + "updateContributions()");
        } else {
            ProjectDAO projectDAO = BeanFactory.getBean("projectDAO", ProjectDAO.class);
            StudentDAO studentDAO = BeanFactory.getBean("studentDAO", StudentDAO.class);

            ProjectVO projectVO = projectDAO.getByGithubURL(githubURL);
            if (projectVO == null) {
                throw new NullPointerException("projectVO 空的" + this.getClass() + "updateContributions()");
            } else {

                githubURL += "/contributors";
                System.out.println(githubURL);
                //链接URL
                URL url = new URL(githubURL);
                //返回结果集
                StringBuffer document = new StringBuffer();
                //创建链接
                URLConnection conn = url.openConnection();
                //读取返回结果集
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    document.append(line);
                }
                reader.close();
                System.out.println(document);
                JSONArray jsonArray = JSONArray.fromObject(document.toString());
                Map<String, Integer> contributions = new HashMap<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String githubLogin = (String) jsonObject.get("login");
                    int contribution = (int) jsonObject.get("contributions");
                    contributions.put(githubLogin, contribution);
                }
                if (!contributions.isEmpty()) {
                    for (String githubLogin : contributions.keySet()) {
                        StudentVO studentVO = studentDAO.getByGithubLogin(githubLogin);
                        ContributionsVO contributionsVO = BeanFactory.getBean("contributionsVO", ContributionsVO.class);
                        contributionsVO.setProjectVO(projectVO);
                        if (studentVO != null) {
                            contributionsVO.setStudentVO(studentVO);
                            int contribution = contributions.get(githubLogin);

                            if (getByStudentAndProject(studentVO, projectVO) == null) {
                                System.out.println(contributionsVO+"插入");
                                //插入
                                contributionsVO.setContributions(contribution);
                                hibernateTemplate.save(contributionsVO);
                            } else {
                                //修改
                                contributionsVO = getByStudentAndProject(studentVO, projectVO);
                                contributionsVO.setContributions(contribution);
                                System.out.println(contributionsVO+"修改");
                                System.out.println(contributionsVO+"修改2");
                                hibernateTemplate.save(contributionsVO);
                            }
                            System.out.println("githubLogin:" + githubLogin + "; contributions:" + contribution);
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据项目id、学生id获取贡献信息
     *
     * @param studentVO
     * @param projectVO
     * @return
     * @throws Exception
     */
    public ContributionsVO getByStudentAndProject(StudentVO studentVO, ProjectVO projectVO) throws Exception {
        if (studentVO == null || projectVO == null) {
            throw new NullPointerException("studentVO、projectVO是空的" + this.getClass() + "getByStudentAndProject()");
        } else {
            String hql = "select c from ContributionsVO c where c.studentVO = ? and c.projectVO = ?";
            List<ContributionsVO> list = (List<ContributionsVO>) hibernateTemplate.find(hql, studentVO, projectVO);
            if (!list.isEmpty()) {
                ContributionsVO contributionsVO = list.get(0);
                System.out.println(contributionsVO.getId()+"id!");
                return list.get(0);
            } else {
                return null;
            }
        }
    }


}
