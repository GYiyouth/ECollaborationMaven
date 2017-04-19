/**
 * Created by fansuyu on 2017/4/19.
 */
function createTeam() {
    var xhr=new XMLHttpRequest();
    xhr.onload=function () {
        if(xhr.status >= 200 && xhr.status < 300 || xhr.status == 304){
            alert("申请成功");
        }else{
            alert("申请失败");
        }
    }
    xhr.open("post","createTeam",false);
    var newTeamform=document.getElementById("newTeamForm");
    if($("#newTeamForm").valid()){
        xhr.send(new FormData(newTeamform));
    }else{
        return false;
    }
}
function validata() {
    $("#newTeamForm").validate({
        rules:{
            teamName:{
                required:true
            },
            memberMax:{
                digits:true,
                required:true
            },
            description:{
                required:true
            }
        },
        messages:{
            teamName:{
                required:"不能为空"
            },
            memberMax:{
                digits:"必须是数字",
                required:"不能为空"
            },
            description:{
                required:"不能为空"
            }
        }
    })
}
$("#submitButton").click(createTeam);
$(document).ready(validata);
