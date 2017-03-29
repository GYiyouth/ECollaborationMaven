package pojo.valueObject.domain;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "teacher")
public class TeacherVO extends UserVO {

	private String homePageUrl = null;
	private Integer needStudentsFlag = null;

	@OneToMany(targetEntity = ProjectVO.class,
        mappedBy = "teacherVO")
    @Lazy(value = false)
    private Set<ProjectVO> projectVOSet;

    public TeacherVO() {
        super();
    }


    @Override
    public String toString() {
        return "TeacherVO{" + super.toString() +
                "homePageUrl='" + homePageUrl + '\'' +
                ", needStudentsFlag=" + needStudentsFlag +
//                ", projectVOSet=" + projectVOSet.size() +
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
