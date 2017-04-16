/**
 * Created by fansuyu on 2017/4/15.
 */
function submitform(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            var JObject=JSON.parse(xhr.responseText);
            var result=JObject.result;
            if(result=="success"){
                alert("新建项目成功")
                alert(xhr.status);
            }else{
                alert("新建项目失败")
            }
        }else{
            alert("请重新登录")
        }
    }
    xhr.open("post","addProjectAction",false);
    var form=document.getElementById("newProForm");
    if($("#newProForm").valid()){
        xhr.send(new FormData(form));
    }else{
        return false;
    }

}
function validate(){
    $("#newProForm").validate({
    rules:{
        name:{
            required:true
        },
        teamMax:{
            required:true,
            digits:true,
            min:1
        },
        memberMax:{
            required:true,
            digits:true,
            min:0
        },
        info:{
            required:true
        },
        keyWord:{
            required:true
        },
        requirement:{
            required:true
        }
    },
    messages:{
        name:{
            required:"不能为空"
        },
        teamMax:{
            required:"不能为空",
            digits:"必须是数字",
            min:"最少是1队"
        },
        memberMax:{
            required:"不能为空",
            digits:"必须是数字",
            min:"人数不能为负"
        },
        info:{
            required:"不能为空"
        },
        keyWord:{
            required:"不能为空"
        },
        requirement:{
            required:"不能为空"
        }
    }
    });
}
function setDataForm() {
    $('#datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd hh:ii'      /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
    });
    $('#datetimepicker2').datetimepicker({
        format: 'yyyy-mm-dd hh:ii'      /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
    });
    $('#datetimepicker3').datetimepicker({
        format: 'yyyy-mm-dd hh:ii'      /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
    });
}
$(document).ready(setDataForm);
$(document).ready(validate);
$("#submitButton").click(submitform);

