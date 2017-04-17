package pojo.valueObject.DTO;

import pojo.valueObject.domain.ApplicationVO;

/**
 * 暂时没测试 2016/2/24/19/54
 * Created by GR on 2017/2/24.
 */
public class ApplicationDTO {
    private int id;
    private String type;
    private int projectId;
    private int handlerId;
    private int affectedId;
    private String createdTime;
    private String handleTime;
    private String result;
    private int teamId;

    public void clone(ApplicationVO applicationVO){
        if(applicationVO!=null){
            this.setId(applicationVO.getId());
            this.setType(applicationVO.getType());
            if(applicationVO.getProjectVO()!=null) {
                this.setProjectId(applicationVO.getProjectVO().getId());
            }
            if(applicationVO.getHandlerUserVO()!=null){
                this.setHandlerId(applicationVO.getHandlerUserVO().getId());
            }
            if(applicationVO.getAffectedUserVO()!=null){
                this.setAffectedId(applicationVO.getAffectedUserVO().getId());
            }
            this.setCreatedTime(applicationVO.getCreatedTime());
            this.setHandleTime(applicationVO.getHandleTime());
            this.setResult(applicationVO.getResult());
            this.setTeamId(applicationVO.getTeamVO().getId());
        }else{
            System.out.println("applicationVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", projectId=" + projectId +
                ", handlerId=" + handlerId +
                ", affectedId=" + affectedId +
                ", createdTime='" + createdTime + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", result='" + result + '\'' +
                ", teamId=" + teamId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
    }

    public int getAffectedId() {
        return affectedId;
    }

    public void setAffectedId(int affectedId) {
        this.affectedId = affectedId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
