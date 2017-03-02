package pojo.valueObject.assist;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;

import javax.persistence.*;

/**
 * Created by GR on 2017/3/2.
 */
@Entity
@Table(name = "project_task")
public class ProjectTaskVO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = ProjectVO.class)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    @ManyToOne(targetEntity = TaskVO.class)
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    private TaskVO taskVO;

    public ProjectTaskVO() {
    }

    @Override
    public String toString() {
        return "ProjectTaskVO{" +
                "id=" + id +
                ", projectVO=" + projectVO.getId() +
                ", taskVO='" + taskVO.getId() + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public TaskVO getTaskVO() {
        return taskVO;
    }

    public void setTaskVO(TaskVO taskVO) {
        this.taskVO = taskVO;
    }
}

