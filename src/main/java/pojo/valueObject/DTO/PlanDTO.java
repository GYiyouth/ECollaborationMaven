package pojo.valueObject.DTO;

import pojo.valueObject.domain.PlanVO;

/**
 * Created by GR on 2017/2/24.
 */
public class PlanDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer studentVOId;
    private String createDate;
    private String finishDate;
    private String beginDate;
    private String targetDate;

    public void clone(PlanVO planVO){
        if(planVO!=null){
            this.setId(planVO.getId());
            this.setTitle(planVO.getTitle());
            this.setContent(planVO.getContent());
            if(planVO.getStudentVO()!=null){
                this.setStudentVOId(planVO.getStudentVO().getId());
            }
            this.setCreateDate(planVO.getCreateDate());
            this.setFinishDate(planVO.getFinishDate());
            this.setBeginDate(planVO.getBeginDate());
            this.setTargetDate(planVO.getTargetDate());
        }else{
            System.out.println("planVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "PlanVO{" +
                "id=" + id +
                ", title='" + title +
                ", content='" + content +
                ", studentVOId=" + studentVOId +
                ", createDate='" + createDate +
                ", finishDate='" + finishDate +
                ", beginDate='" + beginDate +
                ", targetDate='" + targetDate +
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

    public Integer getStudentVOId() {
        return studentVOId;
    }

    public void setStudentVOId(Integer studentVOId) {
        this.studentVOId = studentVOId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
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
}
