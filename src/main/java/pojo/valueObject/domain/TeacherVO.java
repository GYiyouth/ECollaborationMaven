package pojo.valueObject.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class TeacherVO extends UserVO {

	private String homePageUrl = null;
	private Integer needStudentsFlag = null;

	@OneToMany(targetEntity = ProjectVO.class,
        mappedBy = "teacherVO")
    private Set<ProjectVO> projectVOSet;

    public TeacherVO() {
        super();
    }


    @Override
    public String toString() {
        return "TeacherVO{" +
                "homePageUrl='" + homePageUrl + '\'' +
                ", needStudentsFlag=" + needStudentsFlag +
                ", projectVOSet=" + projectVOSet +
                '}';
    }

    public Set<ProjectVO> getProjectVOSet() {
        return projectVOSet;
    }

    public void setProjectVOSet(Set<ProjectVO> projectVOSet) {
        this.projectVOSet = projectVOSet;
    }

    public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public Integer getNeedStudentsFlag() {
		return needStudentsFlag;
	}

	public void setNeedStudentsFlag(Integer needStudentsFlag) {
		this.needStudentsFlag = needStudentsFlag;
	}
}
