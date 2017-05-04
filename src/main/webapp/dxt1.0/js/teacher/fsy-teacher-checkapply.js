/**
 * Created by fansuyu on 2017/5/4.
 */
function getInfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert(xhr.responseText);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("post","getApplication",false);
    xhr.send();
}
$(document).ready(getInfo);
