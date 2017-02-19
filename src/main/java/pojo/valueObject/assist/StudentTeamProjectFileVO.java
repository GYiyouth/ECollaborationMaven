package pojo.valueObject.assist;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "student_team_project_file")
public class StudentTeamProjectFileVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public StudentTeamProjectFileVO() {
    }

    @Override
    public String toString() {
        return "StudentTeamProjectFileVO{" +
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
