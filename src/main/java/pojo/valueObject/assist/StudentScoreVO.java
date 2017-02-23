package pojo.valueObject.assist;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/23.
 */
@Entity
@Table(name = "student_score_record")
public class StudentScoreVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @ManyToOne(targetEntity = ProjectVO.class)
//    @JoinColumn( name = "projectId", referencedColumnName = "id")
//    private ProjectVO projectVO;

    @ManyToOne(targetEntity = ProjectAccessTypeVO.class)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private ProjectAccessTypeVO projectAccessTypeVO;

    @ManyToOne(targetEntity = StudentVO.class)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentVO studentVO;

    private Integer score;

    public StudentScoreVO() {
        super();
    }

    @Override
    public String toString() {
        return "StudentScoreVO{" +
                "id=" + id +
                ", projectAccessTypeVO=" + projectAccessTypeVO.getId() +
                ", studentVO=" + studentVO.getId() +
                ", score=" + score +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectAccessTypeVO getProjectAccessTypeVO() {
        return projectAccessTypeVO;
    }

    public void setProjectAccessTypeVO(ProjectAccessTypeVO projectAccessTypeVO) {
        this.projectAccessTypeVO = projectAccessTypeVO;
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private
}
