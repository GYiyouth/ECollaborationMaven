/**
 * Created by fansuyu on 2017/3/31.
 */
//多事件加载函数
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
        var domP = '<div>';
        domP += '<h1>' + arrJson[i].title + '</h1>';
        domP += '<p>' + arrJson[i].content + '</p>';
        domP += '</div>'
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
            var domP = '<div>';
            domP += '<h1>' + arrJsonCurrent.title + '</h1>';
            domP += '<p>' + arrJsonCurrent.content + '</p>';
            domP += '</div>';
            domBox.innerHTML += domP;
        }
    }
}
