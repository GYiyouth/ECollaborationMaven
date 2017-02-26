package pojo.valueObject.DTO;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.StudentVO;
import pojo.valueObject.domain.TeamVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by GR on 2017/2/24.
 */
public class TeamDTO {
    private Integer id;
    private String teamName;
    private Integer creatorId;
    private String createDate;
    private Integer memberMax;
    private String description;

    private Set<Integer> projectVOIdSet;
    private Set<Integer> studentVOIdSet;

    public void clone(TeamVO teamVO){
        if(teamVO!=null){
            this.setId(teamVO.getId());
            this.setTeamName(teamVO.getTeamName());
            if(teamVO.getCreatorStudentVO()!=null){
                this.setCreatorId(teamVO.getCreatorStudentVO().getId());
            }else{
                this.setCreatorId(null);
            }
            this.setCreateDate(teamVO.getCreateDate());
            this.setMemberMax(teamVO.getMemberMax());
            this.setDescription(teamVO.getDescription());
            if(!teamVO.getProjectVOSet().isEmpty()){
                Set<ProjectVO> projectVOSet = teamVO.getProjectVOSet();
                Set<Integer> projectVOIdSet = new HashSet<>();
                for (ProjectVO projectVO: projectVOSet) {
                    projectVOIdSet.add(projectVO.getId());
                }
                this.setProjectVOIdSet(projectVOIdSet);
            }else{
                this.projectVOIdSet = new HashSet<>();
            }
            if(!teamVO.getStudentVOSet().isEmpty()){
                Set<StudentVO> studentVOSet = teamVO.getStudentVOSet();
                Set<Integer> studentVOIdSet = new HashSet<>();
                for (StudentVO studentVO: studentVOSet) {
                    studentVOIdSet.add(studentVO.getId());
                }
                this.setStudentVOIdSet(studentVOIdSet);
            }else{
                this.studentVOIdSet = new HashSet<>();
            }
        }else{
            System.out.println("teamVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "id=" + id +
                ", teamName='" + teamName +
                ", creatorStudentId=" + creatorId +
                ", createDate='" + createDate +
                ", memberMax=" + memberMax +
                ", description='" + description +
                ", projectVOIdSet=" + projectVOIdSet.size() +
                ", studentVOIdSet=" + studentVOIdSet.size() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getMemberMax() {
        return memberMax;
    }

    public void setMemberMax(Integer memberMax) {
        this.memberMax = memberMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Integer> getProjectVOIdSet() {
        return projectVOIdSet;
    }

    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {
        this.projectVOIdSet = projectVOIdSet;
    }

    public Set<Integer> getStudentVOIdSet() {
        return studentVOIdSet;
    }

    public void setStudentVOIdSet(Set<Integer> studentVOIdSet) {
        this.studentVOIdSet = studentVOIdSet;
    }
}
