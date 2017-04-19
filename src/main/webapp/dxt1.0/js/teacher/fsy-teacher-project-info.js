/**
 * Created by fansuyu on 2017/4/17.
 */
function getInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var project=JObject.projectBean;
            setInfo(project);
        } else {
            alert("请刷新页面");
        }
    }
    var url="getProjectById?"+encodeURIComponent("projectId")+"="+encodeURIComponent(sessionStorage.getItem("itemId"));
    xhr.open("get", url, false);
    xhr.send();
}
function setInfo (project) {
    $(":input[name=name]").val(project.name);
    $(":input[name=info]").val(project.info);
    $(":input[name=keyWord]").val(project.keyWord);
    $(":input[name=applyBeforeDate]").val(project.applyBeforeDate);
    $(":input[name=teamMax]").val(project.teamMax);
    $(":input[name=teamNumber]").val(project.teamNumber);
    $(":input[name=requirement]").val(project.requirement);
    $(":input[name=finishDate]").val(project.finishDate);
}
function getTeam() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var myteam=JObject.teamBeans;
            showTeam(myteam);
        } else {
            alert("请刷新页面");
        }
    }
    xhr.open("get","getMyJoinTeams", false);
    xhr.send();
}
function showTeam(myteam){
    var teamDom=document.getElementById("teamDom");
    teamDom.innerHTML="";
    if(myteam==null){
        teamDom.innerHTML="没有团队进行此项任务";
    }else{
        for(var i=0;i<myteam.length;i++){
            var teamstr='<a href="#">'+myteam[i].teamName+'</a><br>';
            teamDom.innerHTML+=teamstr;
        }
    }
}
function releaseTask() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var result=JObject.result;
            if(result=="success"){
                alert("提交成功");
            }else{
                alert("提交失败");
            }
        } else {
            alert("请刷新页面");
        }
    }
    xhr.open("post","createTask", false);
    var releaseForm=document.getElementById("releaseForm");
    var releaseFD=new FormData(releaseForm);
    releaseFD.append("projectId",sessionStorage.getItem("itemId"));
    xhr.send(releaseFD);
}
$(document).ready(getInfo);
$("#teamButton").click(getTeam);
$("#releaseButton").click(releaseTask);