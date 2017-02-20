package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "code")
public class CodeVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
	private String codeName = null;
	private Integer row = null;
	private String createDate = null;
	private String deadDate = null;
	private Integer downLoadTimes = null;
	private Integer score = null;
	private String path = null;

    @ManyToOne(targetEntity = StudentVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
	private StudentVO studentVO;

    @ManyToOne(targetEntity = TeamVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "teamId", referencedColumnName = "id")
	private TeamVO teamVO;

    @ManyToOne(targetEntity = ProjectVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "projectId", referencedColumnName = "id")
	private ProjectVO projectVO;

	public CodeVO() {
		super();
	}

    @Override
    public String toString() {
        return "CodeVO{" +
                "id=" + id +
                ", codeName='" + codeName + '\'' +
                ", row=" + row +
                ", createDate='" + createDate + '\'' +
                ", deadDate='" + deadDate + '\'' +
                ", downLoadTimes=" + downLoadTimes +
                ", score=" + score +
                ", path='" + path + '\'' +
                ", studentVO=" + studentVO +
                ", teamVO=" + teamVO +
                ", projectVO=" + projectVO +
                '}';
    }

    public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(String deadDate) {
		this.deadDate = deadDate;
	}

	public Integer getDownLoadTimes() {
		return downLoadTimes;
	}

	public void setDownLoadTimes(Integer downLoadTimes) {
		this.downLoadTimes = downLoadTimes;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
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

}
