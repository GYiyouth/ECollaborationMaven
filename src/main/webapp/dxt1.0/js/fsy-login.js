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

function submitform() {
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            var JObject=JSON.parse(xhr.responseText);
            switch(JObject.result){
                case "success": alert("登陆成功");
                    if(JObject.role=="student"){
                        window.open("/dxt1.0/student-projects.html","_self");
                    }
                    if(JObject.role=="teacher"){
                        window.open("/dxt1.0/teacher-projects.html","_self");
                    }
                    break;
                case "fail":alert("系统出错");break;
                case "logInfoWrong":alert("用户名或密码错误");break;
            }
        }else{
            alert("发生错误，请重新登录");
        }

    }
    xhr.open("post","logIn",false);
    var form=document.getElementById("loginform");
    xhr.send(new FormData(form));
}
