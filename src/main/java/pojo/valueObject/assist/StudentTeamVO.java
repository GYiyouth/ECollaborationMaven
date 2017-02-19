package pojo.valueObject.assist;

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

    public StudentTeamVO() {
    }


    @Override
    public String toString() {
        return "StudentTeamVO{" +
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
