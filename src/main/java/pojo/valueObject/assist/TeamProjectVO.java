package pojo.valueObject.assist;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2017/2/20.
 */
@Entity
@Table(name = "team_project")
public class TeamProjectVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(targetEntity = TeamVO.class)
    @JoinColumn(name = "teamId", referencedColumnName = "id")
    private TeamVO teamVO;
    @ManyToOne(targetEntity = ProjectVO.class)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
    private ProjectVO projectVO;

    private String showUrl;

    private Integer score;

    private Boolean applyFlag;

    public TeamProjectVO() {
        super();
    }

    @Override
    public String toString() {
        return "TeamProjectVO{" +
                "id=" + id +
                ", teamVO=" + teamVO.getId() +
                ", projectVO=" + projectVO.getId() +
                ", showUrl='" + showUrl + '\'' +
                ", score=" + score +
                ", applyFlag=" + applyFlag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getApplyFlag() {
        return applyFlag;
    }

    public void setApplyFlag(Boolean applyFlag) {
        this.applyFlag = applyFlag;
    }
}
