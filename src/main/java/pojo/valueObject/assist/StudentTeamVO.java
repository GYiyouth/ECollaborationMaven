package pojo.valueObject.assist;

import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeamVO;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "student_team")
public class StudentTeamVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(targetEntity = StudentVO.class)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentVO studentVO;

    @ManyToOne(targetEntity = TeamVO.class)
    @JoinColumn(name = "teamId", referencedColumnName = "id")
    private TeamVO teamVO;

    private Boolean leaderFlag;

    public StudentTeamVO() {
        super();
    }


    @Override
    public String toString() {
        return "StudentTeamVO{" +
                "id=" + id +
                ", studentVO=" + studentVO.getId() +
                ", teamVO=" + teamVO.getId() +
                ", leaderFlag=" + leaderFlag +
                '}';
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public TeamVO getTeamVO() {
        return teamVO;
    }

    public void setTeamVO(TeamVO teamVO) {
        this.teamVO = teamVO;
    }

    public Boolean getLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(Boolean leaderFlag) {
        this.leaderFlag = leaderFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
