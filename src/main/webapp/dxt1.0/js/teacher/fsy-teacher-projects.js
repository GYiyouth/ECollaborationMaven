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
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            var myProJson=JSON.parse(xhr.responseText);
            var oldProJson=myProJson.oldProjects;
            var nowProJson=myProJson.oldProjects;
            var oldDomBox=document.getElementById("old-domBox");
            var oldDomList=document.getElementById("old-list");
            var oldPreDom=document.getElementById("old-preDom");
            var oldNextDom=document.getElementById("old-nextDom");
            var nowDomBox=document.getElementById("now-domBox");
            var nowDomList=document.getElementById("now-list");
            var nowPreDom=document.getElementById("now-preDom");
            var nowNextDom=document.getElementById("now-nextDom");
            var each=5;
            paging(nowDomBox,"current",nowDomList,each,nowPreDom,nowNextDom,nowProJson);
            paging(oldDomBox,"current",oldDomList,each,oldPreDom,oldNextDom,oldProJson);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get","getTeacherProjectAction",false);
    xhr.send();
}
function paging(domBox,addclass,domList,each,pagePreDom,pageNextDom,arrJson)
{
    //当前元素
    var preNum = 0;
    //数据总长度
    var jsonLen = arrJson.length;
    //页数总数
    var page = Math.ceil(jsonLen / each);

    // 初始化内容
    for (var i = 0; i < each; i++) {
        if (arrJson[i] == null) {
            break;
        }
        var domP = '<a href="javascript:;" class="list-group-item">';
        domP += '<h4>'+arrJson[i].name+'</h4>';
        domP += '<p class="list-group-item-text">' + arrJson[i].info + '</p>';
        domP += '</a>';
        domBox.innerHTML += domP;
    }

    // 设置列表页数
    for (var i = 0; i < page; i++) {
        var domA = document.createElement('a');
        domA.href = 'javascript:;';
        domA.innerHTML = i + 1 ;
        domList.appendChild(domA);
    }
    // 初始化当前页面对象
    var preDom = domList.children[0];
    preDom.className = addclass;

    // 切换页响应事件
    domList.addEventListener('click', function (e) {
        preNum = e.target.innerHTML - 1;
        changeHtml(domBox, preNum);
    });


    // 上一页响应事件
    pagePreDom.addEventListener('click', function () {
// 判断当前元素索引
        if (preNum > 0) {
            preNum--;
            changeHtml(domBox, preNum);
        }

    })

    // 下一页响应事件
    pageNextDom.addEventListener('click', function () {
// 判断当前元素索引
        if (preNum < domList.children.length - 1) {
            preNum++;
            changeHtml(domBox, preNum);
        }

    })

    // 更新domBox显示内容
    function changeHtml(domBox, currentNum) {
        domBox.innerHTML = '';
        preDom.className = '';
        preDom = domList.children[currentNum];
        preDom.className=addclass;
        for (var i = 0; i < each; i++) {
            var arrJsonCurrent = arrJson[(currentNum * each) + i];
            if (arrJsonCurrent == null) {
                break;
            }
            var domP = '<a href="javascript:;" class="list-group-item">';
            domP += '<h4>'+arrJsonCurrent.name+'</h4>';
            domP += '<p class="list-group-item-text">' + arrJsonCurrent.info + '</p>';
            domP += '</a>';
            domBox.innerHTML += domP;
        }
    }
}
//获取Json数据并进行分页显示

addLoadEvent(getInfo);