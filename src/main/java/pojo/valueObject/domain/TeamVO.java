package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * 团队bean
 * Created by geyao on 2016/11/7.
 */
@Entity
@Table(name = "team")
public class TeamVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
	private String teamName = null;
    @ManyToOne(targetEntity = StudentVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
	private StudentVO creatorStudentVO;
	private String createDate = null;
	private Integer memberMax;
	private String description;

    @ManyToMany(targetEntity = ProjectVO.class)
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "team_project",
            joinColumns = @JoinColumn(name = "teamId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id")
    )
    private Set<ProjectVO> projectVOSet;

    public TeamVO() {
        super();
    }

    @Override
    public String toString() {
        return "TeamVO{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", creatorStudentVO=" + creatorStudentVO +
                ", createDate='" + createDate + '\'' +
                ", memberMax=" + memberMax +
                ", description='" + description + '\'' +
                '}';
    }

    public Set<ProjectVO> getProjectVOSet() {
        return projectVOSet;
    }

    public void setProjectVOSet(Set<ProjectVO> projectVOSet) {
        this.projectVOSet = projectVOSet;
    }

    public Integer getMemberMax() {
		return memberMax;
	}

	public void setMemberMax(int memberMax) {
		this.memberMax = memberMax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    public StudentVO getCreatorStudentVO() {
        return creatorStudentVO;
    }

    public void setCreatorStudentVO(StudentVO creatorStudentVO) {
        this.creatorStudentVO = creatorStudentVO;
    }

    public void setMemberMax(Integer memberMax) {
        this.memberMax = memberMax;
    }

    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}




}
