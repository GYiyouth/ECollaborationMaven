package pojo.valueObject.DTO;

import pojo.valueObject.domain.MessageVO;

/**
 * Created by GR on 2017/2/24.
 */
public class MessageDTO {
    private Integer id;
    private String title;
    private String content;
    private String createTime;
    private Integer senderUserVOId;
    private Integer readFlag;
    private String deadDate;

    public void clone(MessageVO messageVO){
        if(messageVO!=null){
            this.setId(messageVO.getId());
            this.setTitle(messageVO.getTitle());
            this.setContent(messageVO.getContent());
            this.setCreateTime(messageVO.getCreateTime());
            if(messageVO.getSenderUserVO()!=null){
                this.setSenderUserVOId(messageVO.getSenderUserVO().getId());
            }
            this.setReadFlag(messageVO.getReadFlag());
            this.setDeadDate(messageVO.getDeadDate());
        }else{
            System.out.println("messageVO is null!");
        }

    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ",title=" + title +
                ",content=" + content +
                ",createTime=" + createTime +
                ",senderUserVOId=" + senderUserVOId +
                ",readFlag=" + readFlag +
                ",deadDate=" + deadDate +
                "}";
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

    public Integer getSenderUserVOId() {
        return senderUserVOId;
    }

    public void setSenderUserVOId(Integer senderUserVOId) {
        this.senderUserVOId = senderUserVOId;
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
