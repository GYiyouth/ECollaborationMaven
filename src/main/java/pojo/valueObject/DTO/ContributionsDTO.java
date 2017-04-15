package pojo.valueObject.DTO;

import pojo.valueObject.domain.ContributionsVO;

/**
 * Created by GR on 2017/4/12.
 */
public class ContributionsDTO {
    private Integer id;
    private Integer projectId;
    private Integer studentId;
    private Integer contributions;

    public void clone(ContributionsVO contributionsVO){
        if(contributionsVO!=null){
            this.setId(contributionsVO.getId());
            if(contributionsVO.getProjectVO()!=null){
                this.setProjectId(contributionsVO.getProjectVO().getId());
            }
            if(contributionsVO.getStudentVO()!=null){
                this.setStudentId(contributionsVO.getStudentVO().getId());
            }
            this.setContributions(contributionsVO.getContributions());
        }else{
            System.out.println("contributionsVO is null"+this.getClass()+"clone()");
        }
    }

    @Override
    public String toString() {
        return "ContributionsVO{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", projectId=" + projectId +
                ", contributions=" + contributions +
                "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }
}
