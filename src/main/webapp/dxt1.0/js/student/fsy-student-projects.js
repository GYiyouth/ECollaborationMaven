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
            var reqJson=JSON.parse(xhr.responseText);
            var myProJson=reqJson.schoolProjectDTOList;
            setMyProInfo(myProJson);
            var intProJson=reqJson.interestProjectDTOList;
            var domBox=document.getElementById("domBox");
            var domList=document.getElementById("list");
            var pagePreDom=document.getElementById("pre");
            var pageNextDom=document.getElementById("next");
            var each=5;
            paging(domBox,"current",domList,each,pagePreDom,pageNextDom,intProJson);
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get","getMyProjectVOList",false);
    xhr.send();
}
//分页函数--参数分别为（显示内容的域,当前页特效,分页数，一页多少条记录，上一页，下一页，Json对象数组）
function paging(domBox,addclass,domList,each,pagePreDom,pageNextDom,arrJson)
{
    //当前元素
    var preNum = 0;
    //数据总长度
    var jsonLen = arrJson.length;
    //每个页的分量
    var each = each;
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
            domP += '<h4>'+arrJsonCurrent.name+'</h4>'
            domP += '<p class="list-group-item-text">' + arrJsonCurrent.info + '</p>';
            domP += '</a>';
            domBox.innerHTML += domP;
        }
    }
}
function setMyProInfo(myProJson) {
    var number=document.getElementById('number');
    var createDate=document.getElementById('createDate');
    var keyWord=document.getElementById('keyWord');
    var info=document.getElementById('info');
    var status=document.getElementById('status');
    var teacherVOId=document.getElementById('teacherVOId');
    var teamVOIdSet=document.getElementById('teamVOIdSet');
    number.innerHTML='成员人数 :'+myProJson[0].teamVOIdSet.length;
    createDate.innerHTML='创建日期 :'+myProJson[0].createDate;
    keyWord.innerHTML='关键字 :'+myProJson[0].keyWord;
    info.innerHTML='信息 :'+myProJson[0].info;
    status.innerHTML='项目状态 :'+myProJson[0].status;
    teacherVOId.innerHTML='指导老师 :'+myProJson[0].teacherVOId;
    for(var i=0;i<myProJson[0].teamVOIdSet.length;i++){
        teamVOIdSet.innerHTML+='成员 '+i+' :'+myProJson[0].teamVOIdSet[i]+'<br>';
    }

}


var xhr=new XMLHttpRequest();
addLoadEvent(getInfo);