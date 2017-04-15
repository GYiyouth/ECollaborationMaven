package pojo.valueObject.domain;

import javax.persistence.*;

/**
 * Created by GR on 2017/4/12.
 */
@Entity
@Table(name = "contributions")
public class ContributionsVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = ProjectVO.class)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    @ManyToOne(targetEntity = StudentVO.class)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentVO studentVO;

    private Integer contributions;

    @Override
    public String toString() {
        return "ContributionsVO{" +
                "id=" + id +
                ", studentVO=" + studentVO.getName() +
                ", projectVO=" + projectVO.getName() +
                ", contributions=" + contributions +
                "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }
}
