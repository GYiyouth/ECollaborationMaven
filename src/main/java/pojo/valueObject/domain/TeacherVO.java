package pojo.valueObject.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class TeacherVO extends UserVO {

	private String homePageUrl = null;
	private Integer needStudentsFlag = null;

    public TeacherVO() {
        super();
    }


    @Override
    public String toString() {
        return "TeacherVO{" +
                "homePageUrl='" + homePageUrl + '\'' +
                ", needStudentsFlag=" + needStudentsFlag +
                '}';
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
