package pojo.valueObject.assist;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "student_team_project_plan")
public class StudentTeamProjectPlanVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public StudentTeamProjectPlanVO() {
    }

    @Override
    public String toString() {
        return "StudentTeamProjectPlanVO{" +
                "id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
