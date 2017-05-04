/**
 * Created by fansuyu on 2017/5/4.
 */
function getInfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert(xhr.responseText);
            var JObject=JSON.parse(xhr.responseText);
            var applicationDTOList=JObject.applicationDTOList;
            var name=JObject.name;
            showApply(applicationDTOList,name);
            setapplyclick();
            setdisapplyclick();
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("post","getApplication",false);
    xhr.send();
}
function showApply(applicationDTOList,name) {
    var htmlStr='';
    for(var i=0;i<applicationDTOList.length;i++){
        var applyName=name[i];
        htmlStr+='<li class="list-group-item" name="applyItem">'+'申请时间:'+applicationDTOList[i].createdTime
            +'申请团队:'+applyName[1]+'申请人:'+applyName[3]
            +'<button name="apply" style=" float:right" type="button" id='+applicationDTOList[i].id+'>'+'同意'+'</button>'
            +'<button name="disapply" style=" float:right" type="button" id='+applicationDTOList[i].id+'>'+'拒绝'+'</button>'
            +'</li>'
    }
    $("#applyList").html(htmlStr);
}
function setapplyclick(){
    $("button[name=apply]").click(function() {
        var applyId = $(this).attr("id");
        var xhra = new XMLHttpRequest();
        xhra.onload = function () {
            if (xhra.status >= 200 && xhra.status < 300 || xhra.status == 304) {
                alert(xhra.responseText);
            } else {
                alert("请刷新页面");
            }
        }
        var applyDate=new FormData();
        applyDate.append("applicationId",applyId)
        xhra.open("post", "acceptJoinApplication", false);
        xhra.send(applyDate);
    });
}
function setdisapplyclick(){
    $("button[name=disapply]").click(function() {
        var applyId = $(this).attr("id");
        var xhrd = new XMLHttpRequest();
        xhrd.onload=function () {
            if (xhrd.status >= 200 && xhrd.status < 300 || xhrd.status == 304) {
                alert(xhrd.responseText);
            } else {
                alert("请刷新页面");
            }
        }
        var disapplyDate=new FormData();
        disapplyDate.append("applicationId",applyId)
        xhrd.open("post", "refuseJoinApplication", false);
        xhrd.send(disapplyDate);
    });
}
$(document).ready(getInfo);