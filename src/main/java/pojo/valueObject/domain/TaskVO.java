package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2016/11/7.
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
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
	private TeacherVO creatorTeacherVO;

	private String createDate = null;
	private String modifyDate = null;
	private String beginDate = null;
	private String targetDate = null;

	public TaskVO() {
		super();
	}

    @Override
    public String toString() {
        return "TaskVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creatorTeacherVO=" + creatorTeacherVO +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", targetDate='" + targetDate + '\'' +
                '}';
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
