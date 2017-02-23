package pojo.valueObject.assist;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import pojo.valueObject.domain.TaskVO;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2017/2/21.
 */
@Entity
@Table(name = "team_project_access")
public class TeamProjectAccessVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @ManyToOne(targetEntity = TeamProjectVO.class)
//    @JoinColumn(name = "team_project_id", referencedColumnName = "id")
    private Integer team_project_id;

    @ManyToOne(targetEntity = TaskVO.class)
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    private TaskVO taskVO;

    private Integer access;

    public TeamProjectAccessVO() {
        super();
    }

    @Override
    public String toString() {
        return "TeamProjectAccessVO{" +
                "id=" + id +
                ", team_project_id=" + team_project_id +
                ", taskVO=" + taskVO.getId() +
                ", access=" + access +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTeam_project_id() {
        return team_project_id;
    }

    public void setTeam_project_id(Integer team_project_id) {
        this.team_project_id = team_project_id;
    }

    public TaskVO getTaskVO() {
        return taskVO;
    }

    public void setTaskVO(TaskVO taskVO) {
        this.taskVO = taskVO;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }
}
