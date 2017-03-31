/**
 * Created by fansuyu on 2017/3/31.
 */
function addLoadEvent(func){
    var oldonload=window.onload;
    if(typeof window.onload !='function'){
        window.onload=func;
    }else{
        window.onload=function(){
            oldonload();
            func();
        }
    }
}
function getInfo(){
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert('收到JSON数据');
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
            alert(xhr.responseText);
            alert(xhr.status);
        }
    }
    xhr.open("get","getTeacherProjectAction",false);
    xhr.send();
}

var xhr=new XMLHttpRequest();
addLoadEvent(getInfo);