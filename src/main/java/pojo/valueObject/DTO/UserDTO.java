package pojo.valueObject.DTO;

import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by geyao on 2017/2/23.
 */
public class UserDTO {

    private Integer id;
    private String schoolId;
    private String name;
    private Integer sex;	//1:男 0：女
    private String role;// 这里还是直接用字符串清晰明了
    private String email;
    private String phoneNumber;
    private String logName;
    private String passWord;
    private String createDate;
    private String photo; //路径，默认放在web.upload.headPhotos下
    private String lastLogTime;
    private String activeBefore;
    private int newFlag;
    private Set<Integer> messageVOIdSet;

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
                ", newFlag=" + newFlag +
                ", messageVOIdSet=" + messageVOIdSet.size() +
                '}';
    }


    public void clone(UserVO userVO){
        System.out.println("userDTO clone");
        if(userVO!=null){

            //基本属性
            this.setId(userVO.getId());
            this.setSchoolId(userVO.getSchoolId());
            this.setName(userVO.getName());
            this.setSex(userVO.getSex());
            this.setRole(userVO.getRole());
            this.setEmail(userVO.getEmail());
            this.setPhoneNumber(userVO.getPhoneNumber());
            this.setLogName(userVO.getLogName());
            this.setPassWord(userVO.getPassWord());
            this.setCreateDate(userVO.getCreateDate());
            this.setPhoto(userVO.getPhoto());
            this.setLastLogTime(userVO.getLastLogTime());
            this.setActiveBefore(userVO.getActiveBefore());

            //集合的id值
            //空 则初始化messageVOIdSet
            if(!userVO.getMessageVOSet().isEmpty()) {
                Set<MessageVO> messageVOSet = userVO.getMessageVOSet();
                Set<Integer> messageVOIdSet = new HashSet<>();
                for (MessageVO messageVO: messageVOSet) {
                    messageVOIdSet.add(messageVO.getId());
                }
                this.setMessageVOIdSet(messageVOIdSet);
            }else{
                this.messageVOIdSet = new HashSet<>();
            }
        }else{
            System.out.println("userVO is null");
        }
    }

    public UserDTO() {
        super();
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

    public int getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(int newFlag) {
        this.newFlag = newFlag;
    }

    public Set<Integer> getMessageVOIdSet() {
        return messageVOIdSet;
    }

    public void setMessageVOIdSet(Set<Integer> messageVOIdSet) {
        this.messageVOIdSet = messageVOIdSet;
    }
}
