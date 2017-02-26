package pojo.valueObject.DTO;

import pojo.valueObject.domain.ManagerVO;
import pojo.valueObject.domain.UserVO;

/**
 * Created by GR on 2017/2/24.
 */
public class ManagerDTO extends UserDTO{
    private Integer mRole;  //0：超级管理员 1：普通管理员

    public void clone(ManagerVO managerVO) {
        super.clone(managerVO);
        if(managerVO!=null){
//            this.setmRole(managerVO.getmRole());
        }else{
            System.out.println("managerVO is null!!");
        }

    }

    @Override
    public String toString() {
        return "ManagerDTO{" + super.toString() +
                ",mRole=" + mRole +
                '}';
    }

    public Integer getmRole() {
        return mRole;
    }

    public void setmRole(Integer mRole) {
        this.mRole = mRole;
    }
}
