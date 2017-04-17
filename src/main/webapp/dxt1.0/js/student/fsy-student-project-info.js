/**
 * Created by fansuyu on 2017/4/17.
 */
function getinfo(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            alert(xhr.responseText);
            var JObject=JSON.parse(xhr.responseText);
            var student=JObject.studentBean;
            setSelfInfo(student);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get",'getUserInfo',false);
    xhr.send();

}
$(document).ready(getinfo);
$($("#pt").test(sessionStorage.getItem('itemId')));