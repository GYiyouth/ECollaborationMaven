package pojo.valueObject.DTO;

import pojo.valueObject.domain.NoticeVO;

/**
 * Created by GR on 2017/2/24.
 */
public class NoticeDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer creatorUserVOId;
    private Integer publishUserVOId;
    private String createTime;

    public void clone(NoticeVO noticeVO){
        if(noticeVO!=null){
            this.setId(noticeVO.getId());
            this.setTitle(noticeVO.getTitle());
            this.setContent(noticeVO.getContent());
            if(noticeVO.getCreatorUserVO()!=null){
                this.setCreatorUserVOId(noticeVO.getCreatorUserVO().getId());
            }
            if(noticeVO.getPublishUserVO()!=null){
                this.setPublishUserVOId(noticeVO.getPublishUserVO().getId());
            }
            this.setCreateTime(noticeVO.getCreateTime());
        }else{
            System.out.println("noticeVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "NoticeDTO{" +
                "id=" + id +
                ",title=" + title +
                ",content=" + content +
                ",creatorUserVOId=" + creatorUserVOId +
                ",publishUserVOId=" + publishUserVOId +
                ",createTime=" + createTime +
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

    public Integer getCreatorUserVOId() {
        return creatorUserVOId;
    }

    public void setCreatorUserVOId(Integer creatorUserVOId) {
        this.creatorUserVOId = creatorUserVOId;
    }

    public Integer getPublishUserVOId() {
        return publishUserVOId;
    }

    public void setPublishUserVOId(Integer publishUserVOId) {
        this.publishUserVOId = publishUserVOId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
