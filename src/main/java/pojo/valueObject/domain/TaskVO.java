package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * 单端N-N，依赖于projectVO
 * 依赖于teacherVO，注意如果新建一个task，一定要指明创建人，不然会往数据库里加入新的teacher
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "task")
public class TaskVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
	private String title = null;
	private String content = null;
    @ManyToOne(targetEntity = TeacherVO.class)
//    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
	private TeacherVO creatorTeacherVO;

	private String createDate = null;
	private String modifyDate = null;
	private String beginDate = null;
	private String targetDate = null;

    @ManyToMany(targetEntity = ProjectVO.class)
    @JoinTable( name = "project_task",
            joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id")
    )
//    @Cascade(CascadeType.ALL)
    private Set<ProjectVO> projectVOSet;

	public TaskVO() {
		super();
	}

	@Override
	public String toString() {
		return "TaskVO{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", creatorTeacherVO=" + creatorTeacherVO.getId() +
				", createDate='" + createDate + '\'' +
				", modifyDate='" + modifyDate + '\'' +
				", beginDate='" + beginDate + '\'' +
				", targetDate='" + targetDate + '\'' +
				", projectVOSet=" + projectVOSet +
				'}';
	}

	public Set<ProjectVO> getProjectVOSet() {
        return projectVOSet;
    }

    public void setProjectVOSet(Set<ProjectVO> projectVOSet) {
        this.projectVOSet = projectVOSet;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

    public TeacherVO getCreatorTeacherVO() {
        return creatorTeacherVO;
    }

    public void setCreatorTeacherVO(TeacherVO creatorTeacherVO) {
        this.creatorTeacherVO = creatorTeacherVO;
    }


}
