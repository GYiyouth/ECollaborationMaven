/**
 * Created by fansuyu on 2017/4/19.
 */
function getInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            alert(xhr.responseText);
            var JObject=JSON.parse(xhr.responseText);
            var teamInfo=JObject.teamBean;
            var projectInfo=JObject.projectBean;
            projectBean=JObject.projectBean;
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
        var str='<a name="item" href="teacher-project-info.html" title='+projectInfo[i].id+'>'+projectInfo[i].name+'</a>'+'<br>';
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
function addRule() {
    var context=$("#ruleDiv").html();
    context+=
        '<div class="row" >'
        +'<div class="col-md-2  col-md-offset-0.5">'
        +'<p class="text-muted">'+"评价规则"+clicknumber+'</p>'
        +'</div>'
        +'<div class="col-md-2 ">'
        +'<input type="text" class="form-control" name="typeNames" id='+"typeNames"+clicknumber+'>'
        +'</div>'
        +'</div>'
    clicknumber++;
    $("#ruleDiv").html(context);
}
function clearCount() {
    clicknumber=2;
    var inite=
        '<div class="row" >'
        +'<div class="col-md-2  col-md-offset-0.5">'
        +'<p class="text-muted">'+'选择项目'+'</p>'
        +'</div>'
        +'<div class="col-md-2 ">';
    projectBean.forEach(function (p1, p2, p3) {
        inite+= '<input type="radio" name="projectIds" value='+p1.id+'>'+'项目-'+p1.name
    })
    inite+=
        '</div>'
        +'</div>';
    inite+=
        '<div class="row" >'
        +'<div class="col-md-2  col-md-offset-0.5">'
        +'<p class="text-muted">'+"评价规则1"+'</p>'
        +'</div>'
        +'<div class="col-md-2 ">'
        +'<input type="text" class="form-control" name="typeNames" id='+"typeNames1"+'>'
        +'</div>'
        +'</div>';
    $("#ruleDiv").html(inite);
}
function submitRules() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            window.open("teacher-personScore.html","_self");
        } else {
            alert(xhr.status);
            alert("请刷新页面");
        }
    }
    xhr.open("post","createAccessType", false);
    var rulesForm=document.getElementById("rulesForm");
    var projectId=$("input[name=projectIds]:checked").attr("value");
    sessionStorage.setItem("projectId",projectId);
    xhr.send(new FormData(rulesForm));
}
var clicknumber=2;
var projectBean=[];
$(document).ready(getInfo);
$("#scoreButton").click(clearCount);
$("#addButton").click(addRule);
$("#submitButton").click(submitRules);
