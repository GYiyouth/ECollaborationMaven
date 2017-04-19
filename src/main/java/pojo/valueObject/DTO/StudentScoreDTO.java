package pojo.valueObject.DTO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pojo.valueObject.assist.StudentScoreVO;
import pojo.valueObject.domain.StudentVO;

/**
 * Created by geyao on 2017/4/19.
 */
@Component
@Scope("prototype")
public class StudentScoreDTO  {
    private Integer id;
    private Integer typeId;
    private Integer studentId;
    private Integer score;

    public StudentScoreDTO() {
        super();
    }

    @Override
    public String toString() {
        return "StudentScoreDTO{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", studentId=" + studentId +
                ", score=" + score +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void clone(StudentScoreVO studentScoreVO){
        if (studentScoreVO == null)
            return;
        this.setId(studentScoreVO.getId());
        this.setTypeId(studentScoreVO.getProjectAccessTypeVO().getId());
        this.setScore(studentScoreVO.getScore());
    }
}
