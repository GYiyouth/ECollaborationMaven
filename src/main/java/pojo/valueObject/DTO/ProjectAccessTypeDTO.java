package pojo.valueObject.DTO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pojo.valueObject.assist.ProjectAccessTypeVO;

/**
 * Created by geyao on 2017/4/19.
 */
@Component
@Scope(value = "prototype")
public class ProjectAccessTypeDTO {
    private Integer id;
    private Integer projectId;
    private String type;

    public ProjectAccessTypeDTO() {
        super();
    }

    @Override
    public String toString() {
        return "ProjectAccessTypeDTO{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void clone(ProjectAccessTypeVO projectAccessTypeVO){
        if (projectAccessTypeVO == null)
            return;
        this.id = projectAccessTypeVO.getId();
        this.projectId = projectAccessTypeVO.getProjectVO().getId();
        this.type = projectAccessTypeVO.getType();
    }
}
