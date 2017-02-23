package pojo.valueObject.assist;

import pojo.valueObject.domain.ProjectVO;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/23.
 */
@Entity
@Table(name = "project_accessType")
public class ProjectAccessTypeVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = ProjectVO.class)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    private String type;

    public ProjectAccessTypeVO() {
    }

    @Override
    public String toString() {
        return "ProjectAccessTypeVO{" +
                "id=" + id +
                ", projectVO=" + projectVO.getId() +
                ", type='" + type + '\'' +
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
