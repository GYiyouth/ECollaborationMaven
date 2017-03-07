/**
 * Created by fansuyu on 2017/3/2.
 */
function addonLoadEvent(func){
    oddonload=document.onload;
    if(typeof document.onload!='function'){
        document.onload=func();
    }else{
        document.onload=function(){
            oddonload();
            func();
        }
    }

}
function fitpicture() {
    var picture = document.getElementById("picture");
    picture.width = "110";
    picture.height = "135";
    picture.style.position = "relative";
    picture.style.top = "155px";
    picture.style.left = "770px"
}

addonLoadEvent(fitpicture);