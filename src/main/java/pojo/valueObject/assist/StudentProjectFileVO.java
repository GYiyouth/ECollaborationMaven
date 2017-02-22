package pojo.valueObject.assist;

import pojo.valueObject.domain.ECFileVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.UserVO;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "student_project_file")
public class StudentProjectFileVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    private UserVO userVO;

    @OneToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    @OneToOne
    @JoinColumn(name = "fileId", referencedColumnName = "id")
    private ECFileVO fileVO;

    public StudentProjectFileVO() {
    }

    @Override
    public String toString() {
        return "StudentProjectFileVO{" +
                "id=" + id +
                '}';
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public ECFileVO getFileVO() {
        return fileVO;
    }

    public void setFileVO(ECFileVO fileVO) {
        this.fileVO = fileVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
