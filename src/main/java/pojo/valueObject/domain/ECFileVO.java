package pojo.valueObject.domain;



import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 约定优先级，工程实践1，比赛2，个人兴趣3，头像等信息4
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "ecfile")
public class ECFileVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
	private String fileName = null;
	private String createDate = null;
	private String deadDate = null;
	private Integer downLoadTimes = null;
	private Integer priority = null;
    @ManyToOne(targetEntity = UserVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
	private UserVO creatorUserVO;
	private String path = null;

	public ECFileVO() {
		super();
//		Time time = new TimeImpl();
//		createDate = time.getDateStr();
	}


    @Override
    public String toString() {
        return "ECFileVO{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", deadDate='" + deadDate + '\'' +
                ", downLoadTimes=" + downLoadTimes +
                ", priority=" + priority +
                ", userVO=" + creatorUserVO +
                ", path='" + path + '\'' +
                '}';
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

    public UserVO getCreatorUserVO() {
        return creatorUserVO;
    }

    public void setCreatorUserVO(UserVO creatorUserVO) {
        this.creatorUserVO = creatorUserVO;
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
