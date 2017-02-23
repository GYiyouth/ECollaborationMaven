package pojo.valueObject.assist;

import pojo.valueObject.domain.ECFileVO;
import pojo.valueObject.domain.PlanVO;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "student_project_plan")
public class StudentProjectPlanVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentVO studentVO;

    @OneToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    @OneToOne
    @JoinColumn(name = "planId", referencedColumnName = "id")
    private PlanVO planVO;

    public StudentProjectPlanVO() {
    }

    @Override
    public String toString() {
        return "StudentProjectPlanVO{" +
                "id=" + id +
                ", studentVO=" + studentVO.getId() +
                ", projectVO=" + projectVO.getId() +
                ", planVO=" + planVO.getId() +
                '}';
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public PlanVO getPlanVO() {
        return planVO;
    }

    public void setPlanVO(PlanVO planVO) {
        this.planVO = planVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
