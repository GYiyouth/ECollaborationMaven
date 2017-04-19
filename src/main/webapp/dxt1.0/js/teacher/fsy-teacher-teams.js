/**
 * Created by fansuyu on 2017/4/19.
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
        var domP = '<a name="team" href="teacher-team-info.html" class="list-group-item" title='+arrJson[i].id+'>';
        domP += '<h4>'+arrJson[i].teamName+'</h4>';
        domP += '<p class="list-group-item-text">' + arrJson[i].description + '</p>';
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
            var domP = '<a name="team" href="teacher-team-info.html" class="list-group-item" title='+arrJsonCurrent.id+'>';
            domP += '<h4>'+arrJsonCurrent.teamName+'</h4>'
            domP += '<p class="list-group-item-text">' + arrJsonCurrent.description + '</p>';
            domP += '</a>';
            domBox.innerHTML += domP;
        }
    }
}
function getTeamInfo() {
    var xhr=new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.status>=200&&xhr.status<300||xhr.status==304){
            var JObject=JSON.parse(xhr.responseText);
            var myTeams=JObject.teamBeans;
            showTeams(myTeams);
            setclick();
        }else{
            alert("请刷新页面");
        }
    }
    xhr.open("get","getTeamsByTeacher",false);
    xhr.send();
}
function showTeams(arrJson) {
    var domBox=document.getElementById("domBox");
    var domList=document.getElementById("list");
    var preDom=document.getElementById("preDom");
    var nextDom=document.getElementById("nextDom");
    var each=5;
    paging(domBox,"showred",domList,each,preDom,nextDom,arrJson);
}
function setclick(){
    $("a[name=team]").click(function(){
        var teamId = $(this).attr("title");
        sessionStorage.setItem("teamId",teamId);
    });
}
$(document).ready(getTeamInfo);