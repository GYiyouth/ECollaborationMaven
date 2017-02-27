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
    @JoinColumn(name = "teamId", referencedColumnName = "id")
    private TeamVO teamVO;
    @ManyToOne(targetEntity = ProjectVO.class)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;
    @ManyToOne(targetEntity = UserVO.class)
    @JoinColumn(name = "handlerId", referencedColumnName = "id")
    private UserVO handlerUserVO;
    @ManyToOne(targetEntity = UserVO.class)
    @JoinColumn(name = "affectedId", referencedColumnName = "id")
    private UserVO affectedUserVO;
    private String createdTime;
    private String result;
    @OneToOne(targetEntity = MessageVO.class)
    @JoinColumn(name = "messageId", referencedColumnName = "id")
    private MessageVO messageVO;

    public ApplicationVO() {
    }

    @Override
    public String toString() {
        return "ApplicationVO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", teamVO=" + teamVO.getId() +
                ", projectVO=" + projectVO.getId() +
                ", handlerUserVO=" + handlerUserVO.getId() +
                ", affectedUserVO=" + affectedUserVO.getId() +
                ", createdTime='" + createdTime + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }

    public void setMessageVO(MessageVO messageVO) {
        this.messageVO = messageVO;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
