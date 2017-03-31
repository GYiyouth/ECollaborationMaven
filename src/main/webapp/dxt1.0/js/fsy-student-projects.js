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
var xhr=new XMLHttpRequest();
function getInfo(){
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert('收到JSON数据');
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get","getMyProjectVOList",false);
    xhr.send();
}
addLoadEvent(getInfo);