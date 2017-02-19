package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2017/2/18.
 */
@Entity
@Table(name = "application")
public class ApplicationVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;
    @ManyToOne(targetEntity = TeamVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "teamId", referencedColumnName = "id")
    private TeamVO teamVO;
    @ManyToOne(targetEntity = ProjectVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;
    @ManyToOne(targetEntity = UserVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "handlerId", referencedColumnName = "id")
    private UserVO handlerUserVO;
    @ManyToOne(targetEntity = UserVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "affectedId", referencedColumnName = "id")
    private UserVO affectedUserVO;
    private String createdTime;
    private String handledTime;
    private String result;

    public ApplicationVO() {
    }

    @Override
    public String toString() {
        return "ApplicationVO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", teamVO=" + teamVO +
                ", projectVO=" + projectVO +
                ", handlerUserVO=" + handlerUserVO +
                ", affectedUserVO=" + affectedUserVO +
                ", createdTime='" + createdTime + '\'' +
                ", handledTime='" + handledTime + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TeamVO getTeamVO() {
        return teamVO;
    }

    public void setTeamVO(TeamVO teamVO) {
        this.teamVO = teamVO;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public UserVO getHandlerUserVO() {
        return handlerUserVO;
    }

    public void setHandlerUserVO(UserVO handlerUserVO) {
        this.handlerUserVO = handlerUserVO;
    }

    public UserVO getAffectedUserVO() {
        return affectedUserVO;
    }

    public void setAffectedUserVO(UserVO affectedUserVO) {
        this.affectedUserVO = affectedUserVO;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(String handledTime) {
        this.handledTime = handledTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
