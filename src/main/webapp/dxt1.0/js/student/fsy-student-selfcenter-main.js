/**
 * Created by fansuyu on 2017/4/17.
 */
function getinfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert(xhr.responseText);
            var JObject=JSON.parse(xhr.responseText);
            var student=JObject.studentBean;
            setSelfInfo(student);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get",'getUserInfo',false);
    xhr.send();

}
function setSelfInfo(student){
    $(":input[name=name]").val(student.name);
    $(":input[name=sex]").val(student.sex);
    $(":input[name=schoolId]").val(student.schoolId);
    $(":input[name=phoneNumber]").val(student.phoneNumber);
    $(":input[name=email]").val(student.email);
    $(":input[name=graduatedSchool]").val(student.graduatedSchool);
    $(":input[name=grade]").val(student.grade);
    $(":input[name=githubLogin]").val(student.githubLogin);
    $(":input[name=homePageUrl]").val(student.homePageUrl);
}
function submitSelfInfo(){
    var xhrr=new XMLHttpRequest();
    xhrr.onload=function () {
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert("修改成功");
            alert(xhrr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhrr.open("post","modifyStudentInfo",false);
    var selfForm=document.getElementById("selfInfo");
    if($("#selfInfo").valid()){
        xhrr.send(new FormData(selfForm));
    }else{
        return false;
    }
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
            graduatedSchool:{
                required:true
            },
            grade:{
                required:true
            },
            githubLogin:{
                required:true
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
            graduatedSchool:{
                required:"不能为空"
            },
            grade:{
                required:"不能为空"
            },
            githubLogin:{
                required:"不能为空"
            },
            homePageUrl:{
                required:"不能为空"
            }
        }
    })
}
$(document).ready(getinfo);
$(document).ready(function () {
    $("#submitButton").click(submitSelfInfo);
});
$(document).ready(validate);