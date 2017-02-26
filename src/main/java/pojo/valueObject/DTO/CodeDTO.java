package pojo.valueObject.DTO;

import pojo.valueObject.domain.CodeVO;

/**
 * Created by GR on 2017/2/24.
 */
public class CodeDTO {
    private Integer id;
    private String codeName;
    private Integer row;
    private String createDate;
    private String deadDate;
    private Integer downLoadTimes;
    private Integer score;
    private String path;
    private Integer studentVOId;
    private Integer teamVOId;
    private Integer projectVOId;

    public void clone(CodeVO codeVO){
        if(codeVO!=null){
            this.setId(codeVO.getId());
            this.setCodeName(codeVO.getCodeName());
            this.setRow(codeVO.getRow());
            this.setCreateDate(codeVO.getCreateDate());
            this.setDeadDate(codeVO.getDeadDate());
            this.setDownLoadTimes(codeVO.getDownLoadTimes());
            this.setScore(codeVO.getScore());
            this.setPath(codeVO.getPath());
            if(codeVO.getStudentVO()!=null){
                this.setStudentVOId(codeVO.getStudentVO().getId());
            }
            if(codeVO.getTeamVO()!=null){
                this.setTeamVOId(codeVO.getTeamVO().getId());
            }
            if(codeVO.getProjectVO()!=null){
                this.setProjectVOId(codeVO.getProjectVO().getId());
            }
        }else{
            System.out.println("codeVO is null!!");
        }
    }

    @Override
    public String toString() {
        return "CodeDTO{" +
                "id=" + id +
                ",codeName=" + codeName +
                ",row=" + row +
                ",createDate=" + createDate +
                ",deadDate=" + deadDate +
                ",downLoadTimes=" + downLoadTimes +
                ",score=" + score +
                ",path=" + path +
                ",studentVOId=" + studentVOId +
                ",teamVOId=" + teamVOId +
                ",projectVOId=" + projectVOId +
                "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStudentVOId() {
        return studentVOId;
    }

    public void setStudentVOId(Integer studentVOId) {
        this.studentVOId = studentVOId;
    }

    public Integer getTeamVOId() {
        return teamVOId;
    }

    public void setTeamVOId(Integer teamVOId) {
        this.teamVOId = teamVOId;
    }

    public Integer getProjectVOId() {
        return projectVOId;
    }

    public void setProjectVOId(Integer projectVOId) {
        this.projectVOId = projectVOId;
    }
}
