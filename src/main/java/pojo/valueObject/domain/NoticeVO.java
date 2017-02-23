package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "notice")
public class NoticeVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    @ManyToOne(targetEntity = UserVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    private UserVO creatorUserVO;

    @ManyToOne(targetEntity = UserVO.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "publishId", referencedColumnName = "id")
    private UserVO publishUserVO;

    private String createTime;

    public NoticeVO() {
        super();
    }

    @Override
    public String toString() {
        return "NoticeVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creatorUserVO=" + creatorUserVO.getId() +
                ", publishUserVO=" + publishUserVO.getId() +
                ", createTime='" + createTime + '\'' +
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


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public UserVO getCreatorUserVO() {
        return creatorUserVO;
    }

    public void setCreatorUserVO(UserVO creatorUserVO) {
        this.creatorUserVO = creatorUserVO;
    }

    public UserVO getPublishUserVO() {
        return publishUserVO;
    }

    public void setPublishUserVO(UserVO publishUserVO) {
        this.publishUserVO = publishUserVO;
    }
}
