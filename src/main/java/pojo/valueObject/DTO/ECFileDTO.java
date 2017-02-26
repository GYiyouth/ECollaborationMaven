package pojo.valueObject.DTO;

import pojo.valueObject.domain.ECFileVO;

/**
 * Created by GR on 2017/2/24.
 */
public class ECFileDTO {
    private Integer id;
    private String fileName;
    private String createDate;
    private String deadDate;
    private Integer downLoadTimes;
    private Integer priority;
    private Integer creatorUserVOId;
    private String path;

    public void clone(ECFileVO ecFileVO){
        if(ecFileVO!=null){
            this.setId(ecFileVO.getId());
            this.setFileName(ecFileVO.getFileName());
            this.setCreateDate(ecFileVO.getCreateDate());
            this.setDeadDate(ecFileVO.getDeadDate());
            this.setDownLoadTimes(ecFileVO.getDownLoadTimes());
            this.setPriority(ecFileVO.getPriority());
            this.setPath(ecFileVO.getPath());
            if(ecFileVO.getCreatorUserVO()!=null){
                this.setCreatorUserVOId(ecFileVO.getCreatorUserVO().getId());
            }
        }else{
            System.out.println("ecFileVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "ECFileDTO{" +
                "id=" + id +
                ",fileName=" + fileName +
                ",createDate=" + createDate +
                ",deadDate=" + deadDate +
                ",downLoadTimes=" + downLoadTimes +
                ",priority=" + priority +
                ",creatorUserVOId=" + creatorUserVOId +
                ",path=" + path +
                "}";
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

    public Integer getCreatorUserVOId() {
        return creatorUserVOId;
    }

    public void setCreatorUserVOId(Integer creatorUserVOId) {
        this.creatorUserVOId = creatorUserVOId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
