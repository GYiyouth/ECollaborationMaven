package pojo.valueObject.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by geyao on 2017/02/18.
 */
@Entity
@Table(name = "manager")
public class ManagerVO extends UserVO {

    private String mRole;  //0：超级管理员 1：普通管理员


    public ManagerVO() {
        super();
    }

    @Override
    public String toString() {
        return "ManagerVO{" + super.toString() +
                "mRole=" + mRole +
                '}';
    }


    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }
}
