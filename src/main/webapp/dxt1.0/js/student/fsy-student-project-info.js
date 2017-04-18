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
    xhr.open("get","getMyManageTeams", false);
    xhr.send();
}
function showTeam(myteam){
    var teamDom=document.getElementById("teamDom");
    teamDom.innerHTML="";
    if(myteam==null){
        teamDom.innerHTML="此项目没有团队参加";
    }else{
        for(var i=0;i<myteam.length;i++){
            var teamstr='<input type="radio" name="team" value="male" id='+myteam[i].id+'>'+myteam[i].teamName;
            teamDom.innerHTML+=teamstr;
        }
    }
}
function applyItem() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var result=JObject.result;
            if(result=="success"){
                alert("申请成功");
            }else{
                alert("已经提交过申请");
            }
        } else {
            alert("请刷新页面");
        }
    }
    var url="applyProject";
    var applyTeamId=$("p[id=teamDom] input[name=team]:checked").attr("id");
    url=addURLParam(url,"projectId",sessionStorage.getItem("itemId"));
    url=addURLParam(url,"teamId",applyTeamId);
    xhr.open("get",url,false);
    xhr.send();
}
function addURLParam(url,name,value) {
    url+=(url.indexOf("?")==-1 ? "?" : "&");
    url+=encodeURIComponent(name)+"="+encodeURIComponent(value);
    return url;
}
$(document).ready(getInfo);
$("#applyButton").click(getTeam);
$("#applyItemButton").click(applyItem);