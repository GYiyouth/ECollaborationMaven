package action.com.task;

import action.com.Base.BaseAction;

/**
 * Created by GR on 2017/4/19.
 */
public class JudgeTaskAction extends BaseAction{

    //jsp
    private Integer teamId;
    private Integer taskId;
    private Integer score;
    private Integer projectId;

    @Override
    public String execute() throws Exception {
        return super.execute();
    }


    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
