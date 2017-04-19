package pojo.valueObject.DTO;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TaskVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by GR on 2017/2/24.
 */
public class TaskDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer creatorTeacherVOId;
    private String createDate;
    private String modifyDate;
    private String beginDate;
    private String targetDate;
    private Set<Integer> projectVOIdSet;

    public void clone(TaskVO taskVO){
        if(taskVO!=null){
            this.setId(taskVO.getId());
            this.setTitle(taskVO.getTitle());
            this.setContent(taskVO.getContent());
            System.out.println("-----");
            if(taskVO.getCreatorTeacherVO()!=null) {
                this.setCreatorTeacherVOId(taskVO.getCreatorTeacherVO().getId());
            }
            if(taskVO.getCreateDate()!=null) {
                this.setCreateDate(taskVO.getCreateDate());
            }
            if(taskVO.getModifyDate()!=null) {
                this.setModifyDate(taskVO.getModifyDate());
            }
            if(taskVO.getBeginDate()!=null) {
                this.setBeginDate(taskVO.getBeginDate());
            }
            if(taskVO.getTargetDate()!=null) {
                this.setTargetDate(taskVO.getTargetDate());
            }
//            if(taskVO.getProjectVOSet()!=null&&!taskVO.getProjectVOSet().isEmpty()){
//                Set<ProjectVO> projectVOSet = taskVO.getProjectVOSet();
//                Set<Integer> projectVOIdSet = new HashSet<>();
//                for(ProjectVO projectVO: projectVOSet){
//                    projectVOIdSet.add(projectVO.getId());
//                }
//                this.setProjectVOIdSet(projectVOIdSet);
//            }else{
//                this.projectVOIdSet = new HashSet<>();
//            }
        }else{
            System.out.println("taskVO is null!!");
        }

    }

    @Override
    public String toString() {
        return "TaskVO{" +
                "id=" + id +
                ", title='" + title +
                ", content='" + content +
                ", creatorTeacherVOId=" + creatorTeacherVOId +
                ", createDate='" + createDate +
                ", modifyDate='" + modifyDate +
                ", beginDate='" + beginDate +
                ", targetDate='" + targetDate +
                ", projectVOIdSet=" + projectVOIdSet.size() +
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

    public Integer getCreatorTeacherVOId() {
        return creatorTeacherVOId;
    }

    public void setCreatorTeacherVOId(Integer creatorTeacherVOId) {
        this.creatorTeacherVOId = creatorTeacherVOId;
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

    public Set<Integer> getProjectVOIdSet() {
        return projectVOIdSet;
    }

    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {
        this.projectVOIdSet = projectVOIdSet;
    }
}
