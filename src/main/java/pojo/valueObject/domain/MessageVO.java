package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import pojo.valueObject.assist.MessageReceiverVO;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "message")
public class MessageVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String createTime;
    @ManyToOne(targetEntity = UserVO.class)
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private UserVO senderUserVO;

    private Integer readFlag;//是否收件人都阅读了
    private String deadDate;
    //这里如果加上了@Cascade(CascadeType.ALL) 那么添加消息的时候会多出一些垃圾记录，在user表中
//    @ManyToMany(targetEntity = UserVO.class)
//    @JoinTable(name = "message_receiver",
//        joinColumns = @JoinColumn(name = "messageId", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "receiverId", referencedColumnName = "id")
//    )
//    @Cascade(CascadeType.ALL)
//    private Set<UserVO> receiverUserVOSet;

    public MessageVO() {
        super();
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", senderUserVO=" + senderUserVO.toString() +
                ", readFlag=" + readFlag +
                ", deadDate='" + deadDate + '\'' +
                '}';
    }

    public UserVO getSenderUserVO() {
        return senderUserVO;
    }

    public void setSenderUserVO(UserVO senderUserVO) {
        this.senderUserVO = senderUserVO;
    }

//    public Set<UserVO> getReceiverUserVOSetSet() {
//        return receiverUserVOSet;
//    }
//
//    public void setReceiverUserVOSetSet(Set<UserVO> userVOSet) {
//        this.receiverUserVOSet = userVOSet;
//    }

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



    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public String getDeadDate() {
        return deadDate;
    }

    public void setDeadDate(String deadDate) {
        this.deadDate = deadDate;
    }
}
