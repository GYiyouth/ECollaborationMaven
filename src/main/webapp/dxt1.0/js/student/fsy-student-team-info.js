/**
 * Created by fansuyu on 2017/4/19.
 */
function getInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var teamInfo=JObject.teamBean;
            var projectInfo=JObject.projectBean;
            setInfo(teamInfo,projectInfo);
            setclick();
        } else {
            alert("请刷新页面");
        }
    }
    xhr.open("post","getTeamInfo", false);
    var teamId=sessionStorage.getItem("teamId");
    var formdata=new FormData();
    formdata.append("teamId",teamId);
    xhr.send(formdata);
}
function setInfo(teamInfo,projectInfo) {
    var printHtml="";
    $("#teamName").html(teamInfo.teamName);
    $("#memberMax").html(teamInfo.memberMax);
    $("#createDate").html(teamInfo.createDate);
    $("#description").html(teamInfo.description);
    for(var i=0;i<projectInfo.length;i++){
        var str='<a name="item" href="student-project-info.html" title='+projectInfo[i].id+'>'+projectInfo[i].name+'</a>'+'<br>';
        printHtml+=str;
    }
    $("#teamProjects").html(printHtml);
}
function setclick(){
    $("a[name=item]").click(function(){
        var itemId = $(this).attr("title");
        sessionStorage.setItem("itemId",itemId);
    });
}
function applyTeam() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            alert("申请成功");
        } else {
            alert("请刷新页面");
        }
    }
    var applyInfo=new FormData();
    applyInfo.append("teamId",sessionStorage.getItem("teamId"));
    xhr.open("post","applyJoinTeam", false);
    xhr.send(applyInfo);
}
$(document).ready(getInfo);
$("#applyTeamButton").click(applyTeam);