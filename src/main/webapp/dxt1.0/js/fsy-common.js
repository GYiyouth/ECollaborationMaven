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