/**
 * Created by fansuyu on 2017/3/31.
 */
function addLoadEvent(func){
    var oldonload=window.onload;
    if(typeof window.onload!='function'){
        window.onload=func;
    }else{
        window.onload=function(){
            oldonload();
            func();
        }
    }
}

function getinfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert("收到JSON数据");
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get",'getUserInfo',false);
    xhr.send();

}
function submitSelfInfo() {
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert("收到JSON数据");
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("post","",false);
    var selfInfoForm=$("#selfInfoform").get(0);
    xhr.send(new FormData(selfInfoForm));
}
$(document).ready(getinfo);
$(document).ready(function () {
    $("#submitbutton").click(function(){
        submitSelfInfo();
    })
})
