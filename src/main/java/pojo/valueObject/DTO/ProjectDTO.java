package pojo.valueObject.DTO;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeamVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by GR on 2017/2/24.
 */
public class ProjectDTO {
    private Integer id;
    private String name;
    private String applyBeforeDate;
    private String finishDate;
    private String survivalDate;
    private Integer teamNumber;
    private Integer teamMax;
    private Integer memberMax;
    private String createDate;
    private Integer grade;
    private String keyWord;
    private String info;
    private String requirement;
    private String gain;
    private Integer priority;
    private Integer status;
    private Integer creatorUserVOId;
    private Integer teacherVOId;
    private Set<Integer> teamVOIdSet;
    private String githubURL;

    public void clone(ProjectVO projectVO){
        if(projectVO!=null){
            this.setId(projectVO.getId());
            this.setName(projectVO.getName());
            this.setApplyBeforeDate(projectVO.getApplyBeforeDate());
            this.setFinishDate(projectVO.getFinishDate());
            this.setSurvivalDate(projectVO.getSurvivalDate());
            this.setTeamNumber(projectVO.getTeamNumber());
            this.setTeamMax(projectVO.getTeamMax());
            this.setMemberMax(projectVO.getMemberMax());
            this.setCreateDate(projectVO.getCreateDate());
            this.setGrade(projectVO.getGrade());
            this.setKeyWord(projectVO.getKeyWord());
            this.setInfo(projectVO.getInfo());
            this.setRequirement(projectVO.getRequirement());
            this.setGain(projectVO.getGain());
            this.setPriority(projectVO.getPriority());
            this.setStatus(projectVO.getStatus());;
            if(projectVO.getCreatorUserVO()!=null){
                this.setCreatorUserVOId(projectVO.getCreatorUserVO().getId());
            }
            if(projectVO.getTeacherVO()!=null){
                this.setTeacherVOId(projectVO.getTeacherVO().getId());
            }
//            if(!projectVO.getTeamVOSet().isEmpty()){
//                Set<TeamVO> teamVOSet = projectVO.getTeamVOSet();
//                Set<Integer> teamVOIdSet = new HashSet<>();
//                for(TeamVO teamVO: teamVOSet){
//                    teamVOIdSet.add(teamVO.getId());
//                }
//            }else{
//                this.teamVOIdSet = new HashSet<>();
//            }
            this.setGithubURL(projectVO.getGithubURL());
        }else{
            System.out.println("projectVO is null");
        }
    }

    @Override
    public String toString() {
        return "ProjectVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applyBeforeDate='" + applyBeforeDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", survivalDate='" + survivalDate + '\'' +
                ", teamNumber=" + teamNumber +
                ", teamMax=" + teamMax +
                ", memberMax=" + memberMax +
                ", createDate='" + createDate + '\'' +
                ", grade=" + grade +
                ", keyWord='" + keyWord + '\'' +
                ", info='" + info + '\'' +
                ", requirement='" + requirement + '\'' +
                ", gain='" + gain + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", creatorUserVOId=" + creatorUserVOId +
                ", teacherVOId=" + teacherVOId +
//                ", teamVOIdSet=" + teamVOIdSet.size() +
                ", githubURL=" + githubURL +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyBeforeDate() {
        return applyBeforeDate;
    }

    public void setApplyBeforeDate(String applyBeforeDate) {
        this.applyBeforeDate = applyBeforeDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getSurvivalDate() {
        return survivalDate;
    }

    public void setSurvivalDate(String survivalDate) {
        this.survivalDate = survivalDate;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public Integer getTeamMax() {
        return teamMax;
    }

    public void setTeamMax(Integer teamMax) {
        this.teamMax = teamMax;
    }

    public Integer getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(Integer memberMax) {
        this.memberMax = memberMax;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatorUserVOId() {
        return creatorUserVOId;
    }

    public void setCreatorUserVOId(Integer creatorUserVOId) {
        this.creatorUserVOId = creatorUserVOId;
    }

    public Integer getTeacherVOId() {
        return teacherVOId;
    }

    public void setTeacherVOId(Integer teacherVOId) {
        this.teacherVOId = teacherVOId;
    }

    public Set<Integer> getTeamVOIdSet() {
        return teamVOIdSet;
    }

    public void setTeamVOIdSet(Set<Integer> teamVOIdSet) {
        this.teamVOIdSet = teamVOIdSet;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }
}
