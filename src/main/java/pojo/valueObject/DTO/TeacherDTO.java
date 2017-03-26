package pojo.valueObject.DTO;

import pojo.valueObject.domain.ProjectVO;
import pojo.valueObject.domain.TeacherVO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by GR on 2017/2/24.
 */
public class TeacherDTO extends UserDTO {
    private String homePageUrl;
    private Integer needStudentsFlag;
    private Set<Integer> projectVOIdSet;

    public void clone(TeacherVO teacherVO) {

        if(teacherVO!=null){
            super.clone(teacherVO);
            this.setHomePageUrl(teacherVO.getHomePageUrl());
            this.setNeedStudentsFlag(teacherVO.getNeedStudentsFlag());
//            if(!teacherVO.getProjectVOSet().isEmpty()){
//                Set<ProjectVO> projectVOSet = teacherVO.getProjectVOSet();
//                Set<Integer> projectVOIdSet = new HashSet<>();
//                for(ProjectVO projectVO: projectVOSet){
//                    projectVOIdSet.add(projectVO.getId());
//                }
//                this.setProjectVOIdSet(projectVOIdSet);
//            }else{
//                this.projectVOIdSet = new HashSet<>();
//            }
        }else{
            System.out.println("teacherVO is null!");
        }
    }

    @Override
    public String toString(){
        return  "TeacherDTO{" + super.toString() +
                ",homePageUrl='" + homePageUrl + '\'' +
                ", needStudentsFlag=" + needStudentsFlag +
                ", projectVOIdSet=" + projectVOIdSet.size() +
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

    public Set<Integer> getProjectVOIdSet() {
        return projectVOIdSet;
    }

    public void setProjectVOIdSet(Set<Integer> projectVOIdSet) {
        this.projectVOIdSet = projectVOIdSet;
    }
}
