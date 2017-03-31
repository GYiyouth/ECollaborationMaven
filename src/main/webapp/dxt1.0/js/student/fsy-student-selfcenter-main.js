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
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert("收到JSON数据");
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get",'',false);
    alert("定义xhr")
    xhr.send();
    alert("发送请求");

}

var xhr=new XMLHttpRequest();
addLoadEvent(getinfo);