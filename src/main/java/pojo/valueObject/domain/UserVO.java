package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String schoolId;
    private String name;
    private Integer sex;	//1:男 0：女
    private String role;//teacher, student, manager
    private String email;
    private String phoneNumber;
    private String logName;
    private String passWord;
    private String createDate;
    private String photo; //路径，默认放在web.upload.headPhotos下
    private String lastLogTime;
    private String activeBefore;
    private Integer newsFlag;
    // toString needed
    @ManyToMany(targetEntity = MessageVO.class)
    @JoinTable(name = "message_receiver",
            joinColumns = @JoinColumn(name = "receiverId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "messageId", referencedColumnName = "id")
    )
    private Set<MessageVO> messageVOSet;


    public UserVO() {
        super();
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", schoolId='" + schoolId + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", logName='" + logName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", createDate='" + createDate + '\'' +
                ", photo='" + photo + '\'' +
                ", lastLogTime='" + lastLogTime + '\'' +
                ", activeBefore='" + activeBefore + '\'' +
                ", newsFlag=" + newsFlag +
                ", messageVOSet=" + messageVOSet.size() +
                '}';
    }


    public Set<MessageVO> getMessageVOSet() {
        return messageVOSet;
    }

    public void setMessageVOSet(Set<MessageVO> messageVOSet) {
        this.messageVOSet = messageVOSet;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getLogName() {
        return logName;
    }
    public void setLogName(String logName) {
        this.logName = logName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getLastLogTime() {
        return lastLogTime;
    }
    public void setLastLogTime(String lastLogTime) {
        this.lastLogTime = lastLogTime;
    }
    public String getActiveBefore() {
        return activeBefore;
    }
    public void setActiveBefore(String activeBefore) {
        this.activeBefore = activeBefore;
    }
    public Integer getNewsFlag() {
        return newsFlag;
    }
    public void setNewsFlag(int newsFlag) {
        this.newsFlag = newsFlag;
    }


}
