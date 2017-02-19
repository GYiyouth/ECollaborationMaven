package pojo.valueObject.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class ManagerVO extends UserVO {

    private Integer mRole;  //0：超级管理员 1：普通管理员


    public ManagerVO() {
        super();
    }

    @Override
    public String toString() {
        return "ManagerVO{" + super.toString() +
                "mRole=" + mRole +
                '}';
    }


    public Integer getmRole() {
        return mRole;
    }

    public void setmRole(Integer mRole) {
        this.mRole = mRole;
    }
}
