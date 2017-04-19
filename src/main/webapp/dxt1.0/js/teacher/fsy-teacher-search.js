/**
 * Created by fansuyu on 2017/4/16.
 */
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
        var domP = '<a name="itemDom" href="teacher-project-info.html" class="list-group-item" title='+arrJson[i].id+'>';
        domP += '<h4>'+arrJson[i].name+'</h4>';
        domP += '<p class="list-group-item-text">' + arrJson[i].info + '</p>';
        domP += '<div style="display: none;">'+arrJson[i].id+'</div>';
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
            var domP = '<a name="itemDom" href="teacher-project-info.html" class="list-group-item" title='+arrJsonCurrent.id+'>';
            domP += '<h4>'+arrJsonCurrent.name+'</h4>'
            domP += '<p class="list-group-item-text">' + arrJsonCurrent.info + '</p>';
            domP += '<div style="display: none;">'+arrJson[i].id+'</div>';
            domP += '</a>';
            domBox.innerHTML += domP;
        }
    }
}
function submitSearchForm(){
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            JObject=JSON.parse(xhr.responseText);
        }else{
            alert("请重新登录");
        }
    }
    var keyWord=$("#searchInfo").val();
    var url="appSearchProjectAction?"+encodeURIComponent("keyWord")+"="+encodeURIComponent(keyWord);
    xhr.open("get",url,false);
    xhr.send();
}
function showResult(JObject){
    var projectsList=JObject.projectsList;
    var $showDiv=$("#formshowDiv");
    $showDiv.empty();
    var str=
        '<div class="container">'
        +'<h3>搜索结果</h3>'
        +'<section id="domBox" class="list-group"></section>'+ '<ul class="pager">'
        +'<li><a href="javascript:;" id="preDom">上一页</a></li>'
        +'<li id="list"></li>'
        +'<li><a href="javascript:;" id="nextDom">下一页</a></li>'
        +'</ul>'
        +'</div>'
    $showDiv.html(str);
    var domBox=document.getElementById("domBox");
    var domList=document.getElementById("list");
    var preDom=document.getElementById("preDom");
    var nextDom=document.getElementById("nextDom");
    var each=5;
    paging(domBox,"current",domList,each,preDom,nextDom,projectsList);
    setclick();

}
function setclick(){
    $("a[name=itemDom]").click(function(){
        var itemId = $(this).attr("title");
        sessionStorage.setItem("itemId",itemId);
    });
}
var JObject;
$(function(){
    $("#searchButton").click(function(event){
        var info=$("#searchInfo").val();
        if(info==""){
            return false;
        }else{
            event.preventDefault();
            submitSearchForm();
            showResult(JObject);
        }
    });
})
// var jsonTest=[
//     {
//         "name":"name1",
//         "info":"info1",
//         "id":"1"
//     },
//     {
//         "name":"name2",
//         "info":"info2",
//         "id":"2"
//     },
//     {
//         "name":"name3",
//         "info":"info3",
//         "id":"3"
//     },
//     {
//         "name":"name4",
//         "info":"info4",
//         "id":"4"
//     },
//     {
//         "name":"name5",
//         "info":"info5",
//         "id":"5"
//     },
//     {
//         "name":"name5",
//         "info":"info5",
//         "id":"5"
//     },
//     {
//         "name":"name6",
//         "info":"info6",
//         "id":"6"
//     }
// ]


