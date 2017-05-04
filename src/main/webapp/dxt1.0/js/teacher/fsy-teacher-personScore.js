/**
 * Created by fansuyu on 2017/4/19.
 */
function getInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
            var JObject=JSON.parse(xhr.responseText);
            var rules=JObject.accessTypeDTOList;
            var students=JObject.studentDTOList;
            var scorePeopleList=JObject.studentScore;
            showTable(rules,students,scorePeopleList);
        } else {
            alert("请刷新页面");
        }
    }
    var necessaryInfo=new FormData();
    necessaryInfo.append("teamId",sessionStorage.getItem("teamId"));
    necessaryInfo.append("projectId",sessionStorage.getItem("projectId"));
    xhr.open("post","getAccess", false);
    xhr.send(necessaryInfo);
}
function showTable(rules,students,scorePeopleList) {
    var headstr='<th></th>';
    for(var i=0;i<rules.length;i++){
        headstr+='<th>'+rules[i].type+'</th>'
    }
    $("#headTr").html(headstr);
    var bodystr='';
    for(var j=0;j<students.length;j++){
        bodystr+='<tr class="trClass">'
        bodystr+='<td name='+'studentId-'+students[j].id+'>'+students[j].name+'</td>';
            var scorelist=scorePeopleList[j];
            var scoreLength=scorelist.length;
            for(var k=0;k<scoreLength;k++){
                bodystr+='<td contentEditable="true" name='+rules[k].id+'>'+scorelist[k].score+'</td>';
            }
            for(var q=scoreLength;q<rules.length;q++){
                bodystr+='<td contentEditable="true" name='+rules[q].id+'>'+'0'+'</td>';
            }
        bodystr+='</tr>'
    };
    $("#bodycontent").html(bodystr);
}
function score() {
    var jsonstr='[';
    $("table tbody tr").each(function () {
        var copystr='';
        $(this).find("td").each(function () {
            var namestr=$(this).attr("name");
            if(namestr.slice(0,7)=="student"){
                copystr='{'+'\"studentId\":'+namestr.slice(10)+',';
            }else{
                jsonstr+=copystr;
                jsonstr+='\"typeId\":'+'\"'+namestr+'\"'+',';
                jsonstr+='\"score\":'+'\"'+$(this).text()+'\"';
                jsonstr+='},';
            }
        })
    })
    jsonstr+=']';
    alert(jsonstr);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
        } else {
            alert("请刷新页面");
        }
    }
    var scoreData=new FormData();
    scoreData.append("evaluationResults",jsonstr);
    xhr.open("post","addAccessToStudent", false);
    xhr.send(scoreData);
}
$(document).ready(getInfo);
$("#scoreButton").click(score);