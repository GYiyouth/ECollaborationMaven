/**
 * Created by fansuyu on 2017/4/17.
 */
function getinfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            var JObject=JSON.parse(xhr.responseText);
            var teacher=JObject.teacherBean;
            setSelfInfo(teacher);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get",'getUserInfo',false);
    xhr.send();

}
function setSelfInfo(teacher){
    $(":input[name=name]").val(teacher.name);
    $(":input[name=sex]").val(teacher.sex);
    $(":input[name=schoolId]").val(teacher.schoolId);
    $(":input[name=phoneNumber]").val(teacher.phoneNumber);
    $(":input[name=email]").val(teacher.email);
    $(":input[name=homePageUrl]").val(teacher.homePageUrl);
}
function validate(){
    $("#selfInfo").validate({
        rules:{
            name:{
                required:true,
                rangelength:[2,10]
            },
            sex:{
                required:true,
            },
            schoolId:{
                required:true
            },
            phoneNumber:{
                required:true,
                digits:true,
                rangelength:[11,11]
            },
            email:{
                required:true,
                email:true
            },
            homePageUrl:{
                required:true
            }
        },
        messages:{
            name:{
                required:"不能为空",
                rangelength:"最少两个字符"
            },
            sex:{
                required:"不能为空"
            },
            schoolId:{
                required:"不能为空"
            },
            phoneNumber:{
                required:"不能为空",
                digits:"必须是数字",
                rangelength:"手机号应为11位"
            },
            email:{
                required:"不能为空",
                email:"请输入正确邮箱格式"
            },
            homePageUrl:{
                required:"不能为空"
            }
        }
    })
}
function submitSelfInfo(){
    var xhrr=new XMLHttpRequest();
    xhrr.onload=function () {
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert("修改成功");
        }else{
            alert("请刷新页面");
        }
    }
    xhrr.open("post","modifyTeacherInfoAction",false);
    var selfForm=document.getElementById("selfInfo");
    if($("#selfInfo").valid()){
        xhrr.send(new FormData(selfForm));
    }else{
        return false;
    }
}
$(document).ready(getinfo);
$(document).ready(validate);
$(document).ready(function () {
    $("#submitButton").click(submitSelfInfo);
});