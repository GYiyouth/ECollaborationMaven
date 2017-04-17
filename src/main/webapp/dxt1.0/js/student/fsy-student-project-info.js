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
    for(var i=0;i<myteam.length;i++){
        var teamstr='<input type="radio" name="team" value="male"/>'+myteam[i].teamName;
        teamDom.innerHTML+=teamstr;
    }
}
$(document).ready(getInfo);
$("#applyButton").click(getTeam);