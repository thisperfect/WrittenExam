<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin_yk
  Date: 2017/7/25
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <title></title>

    <link rel="stylesheet" href="${ctx}/static/css/common.css">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <style media="screen">
        .title {
            background: #f4f4f4;
            text-align: left;
            padding-left: 20px;
            line-height: 45px;
            height: 45px;
        }

        .alertcontent {
            min-height: 100px;
            background: #fff;
            line-height: 25px;
            color: #666;
            padding: 20px 40px 20px 106px;
            position: relative;
        }

        .alertcontent img {
            position: absolute;
            top: 50%;
            left: 40px;
            margin-top: -23px;
        }

        .btn-wrap {
            background: #fff;
            text-align: center;
            height: 50px;
            line-height: 25px;
        }

        .btn {
            padding: 0;
            height: 30px;
            background: #cfeae5;
            line-height: 30px;
            vertical-align: top;
            border-radius: 2px;
            width: 80px;
            border: 1px solid #FFF;
            cursor: pointer;
            color: #666;
            margin: 0 10px;
        }

        .btn2 {
            display: inline-block;
            height: 30px;
            background: #63c9bd;
            border-radius: 2px;
            line-height: 30px;
            width: 80px;
            border: 1px solid #FFF;
            cursor: pointer;
            color: #fff;
            margin: 0 10px;
        }

        .officers {
            padding-left: 20px;
            height: 57px;
            vertical-align: middle
        }

        .officerCnt {
            width: 50px
        }
    </style>

</head>
<body>
<div class="header">
    <div class="header-cont">
        <div class="logo">
            <img src="${ctx}/static/images/logo.png" alt="">
        </div>
        <div style="display:inline-block;margin-top:4px;">
            <ul class="nav nav-pills">
                <li role="presentation" class="clickable"><a href="#" class="locallink">场次管理</a></li>
                <li role="presentation" class="clickable"><a href="#">系统配置</a></li>
            </ul>
        </div>
        <div class="btn-group" style="margin-top:8px;">
            <div class="infoimg">
                <img src="${ctx}/static/images/t16.png" alt="">
            </div>
            <button type="button" class="btn btn-default dropdown-toggle infobt" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                名字 <span class="caret sd"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Separated link</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="content">
    <div class="video bord">
        <div class="utl">
            <div class="utlc">
                作答情况
            </div>
        </div>
        <table class="zdqkt table table-bordered">
            <tr>
                <td rowspan=2 class="lefttab assignColumn">
                    <span>*</span>阅卷方式
                </td>
                <td>
                    <input type="radio" name="scoreType" value="0" id="person" checked><label for="person"
                                                                                              class="radio">按候选人阅卷</label><input
                        type="radio" name="scoreType" value="1" id="question"><label class="radio"
                                                                                     for="question">按题阅卷</label>
                </td>
            </tr>
            <tr id="assign">
                <td style="padding-left:20px;height:57px;vertical-align:middle">
                    <span>分 <input type="text" style="width:50px" id="groupCnt" onblur="groupPaper(this);"> 组阅卷</span>
                    <span class="xts">*分组为1时，所有试卷随机平均分配给各个阅卷管，相差不超过1</span>
                </td>
            </tr>
        </table>
        <table class="zdqkt table table-bordered">
            <tr>
                <td class="lefttab">
                    <span>*</span>匿名阅卷
                </td>
                <td>
                    <input type="radio" name="anonymous" value="0" id="YES"><label for="YES"
                                                                                   class="radio">是</label><input
                        type="radio" name="anonymous" value="1" id="NO" checked><label class="radio" for="NO">否</label>
                </td>
            </tr>
            <tr>
                <td class="lefttab">
                    邮件标题
                </td>
                <td>
                    <input type="text" class="yjTitle"/>
                    <span class="yjShoQi">收起</span>
                </td>
            </tr>
            <tr>
                <td class="lefttab">
                    内容
                </td>
                <td>
                    <div class="TCXLabel">
                        <span>用户名</span><span>简历信息列表</span><span>发送时间</span><span>系统</span><span>发送者部门</span><span>发送者</span>
                    </div>

                    <div id="bjq" style="">
                        <script id="editor2" type="text/plain" style="width:680px;height:200px;"></script>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="lefttab">
                    发送短信
                </td>
                <td>
                    <input type="radio" name="sms" value="0" id="DXYES"><label for="DXYES"
                                                                               class="radio">是</label><input
                        type="radio" name="sms" value="1" id="DXNO" checked><label class="radio"
                                                                                   for="DXNO">否</label>
                </td>
            </tr>
        </table>
        <div class="col-md-12">
            <div class="formsub">
                <a href="javascript:window.location.reload();" class="reset qianLu">重置</a>
                <a href="javascript:saveDistribution();" class="save luSe">保存</a>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/javascript/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/javascript/action.js" charset="utf-8"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/javascript/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/javascript/UEditor/ueditor.all.min.js"></script>
<script>

    var selectQuesRuleId;

    $(".yjShoQi").on("click", function () {
        var _this = $(this);
        if (_this.text() == "收起") {
            _this.parents("tr").next().css("display", "none");
            _this.text('展开');
        } else {
            _this.parents("tr").next().css("display", "table-row");
            _this.text('收起');
        }
    });

    $("input:radio[name='scoreType']").change(function () {
        var checkResult = $("input:radio[name='scoreType']:checked").val();
        if (checkResult == "0") {
            $(".assignColumn").attr("rowspan", 2);
            if ($("#selectQues")[0]) {
                $("#selectQues").remove();
            }
        } else if (checkResult == "1") {
            $(".assignColumn").attr("rowspan", 1);
            var str = selectQues();
            $("#assign").before(str);
        }
    });

    var ue = UE.getEditor('editor2', {
        toolbars: [
            ['fullscreen', 'source', 'undo', 'redo'],
            ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
        ]
    });

    function groupPaper(elem) {
        var _elem = $(elem);
        var groupCnt = _elem.val();
        var reg = /^[0-9]*[1-9][0-9]*$/;
        if (!reg.test(groupCnt)) {
            _elem.val("");
            standardAlert("请输入大于0的合法数字!");
        } else {
            var trs = $("#assign").parent().find(".groupComp");
            for (var i = 0; i < trs.length; i++) {
                trs[i].remove();
            }

            var str = "";
            for (var i = 1; i <= groupCnt; i++) {
                var name = "";

                switch (i) {
                    case 1:
                        name = "一";
                        break;
                    case 2:
                        name = "二";
                        break;
                    case 3:
                        name = "三";
                        break;
                    case 4:
                        name = "四";
                        break;
                    case 5:
                        name = "五";
                        break;
                }

                if (groupCnt == 1) {
                    str += addGroup(name, true);
                } else {
                    str += addGroup(name, false);
                }
            }
            $("#assign").after(str);
        }
    }

    function addGroup(name, single) {
        var str = "<tr class='groupComp'>";
        str += "<td class='lefttab'>";
        str += "<p>第" + name + "组</p>";
        if (!single) {
            str += "<p>每人 <input type='text' class='officerCnt' onblur='javascript:validateEnter(\"num\",this)' onfocus='javascript:retryColor(this);'> 份</p>";
        }
        str += "</td>";
        str += "<td class='officers'>";

        str += addOfficerStr(false);
        str += addOfficerStr(true);

        str += "</td>";
        str += "</tr>";

        return str;
    }

    function addOfficerStr(last) {
        var str = "<p>阅卷官：";
        str += "<input type='text' placeholder='姓名' class='greinp' onblur='javascript:validateEnter(\"name\",this)' onfocus='javascript:retryColor(this);'>";
        str += "<input type='text' placeholder='电话号码' class='greinp' onblur='javascript:validateEnter(\"phone\",this)' onfocus='javascript:retryColor(this);'>";
        str += "<input type='text' placeholder='电子邮箱' class='greinp' onblur='javascript:validateEnter(\"email\",this)' onfocus='javascript:retryColor(this);'>";
        str += "<img src='${ctx}/static/images/js.png' style='cursor:pointer;margin:0 10px;' onclick='javascript:delOfficer(this);'>";

        if (last) {
            str += "<span style='color:#84DCD0;'onclick='javascript:addOfficer(this);' >继续添加</span>";
        }
        str += "</p>";
        return str;
    }

    function addOfficer(elem) {
        var _elem = $(elem);
        _elem.parent().after(addOfficerStr(true));
        _elem.remove();
    }

    function delOfficer(elem) {
        var _elem = $(elem);

        var index = _elem.parent().index() + 1;
        var officerCnt = _elem.parents("td").find("p").length;
        if (officerCnt <= 1) {
            standardAlert("只剩一个阅卷官了，不能再删了!");
        } else {
            if (index == officerCnt) {
                _elem.parent().prev().append("<span style='color:#84DCD0;'onclick='javascript:addOfficer(this);' >继续添加</span>");
            }
            _elem.parent().remove();
        }
    }

    function selectQues() {

        var str = "<tr id='selectQues'>";
        str += "<td class='lefttab' rowspan=2>题目</td>";
        str += "<td style='padding-left:20px;height:57px;vertical-align:middle;position:relative;padding-right:130px;'>";
        str += "<div class='chosetm' onclick='javascript:window.open(\"${ctx}/selectQues/deal?paperIds=" +
        ${paperIds}.
        toString() + "\",\"_blank\");'>选择题目</div>";
        str += "</td></tr>";
        return str;
    }

    function standardAlert(message) {
        alert(message, "提示", ".alertcontent{ padding: 31px 60px 30px 60px;line-height: 25px;}",
            {fir: '取消', sec: '确认'},
            ".alertcontent{background:#fff;}"
        );
    }

    function selectedQues(quesNumArray, quesContent, ruleId) {
        selectQuesRuleId = ruleId;
        var start_index = quesContent.indexOf("[");
        var start_end = quesContent.indexOf("]");
        quesContent = quesContent.substring(0, start_index) + quesContent.substring(start_end + 2, quesContent.length);
        if (quesContent.length > 200) {
            quesContent = quesContent.substring(0, 200);
        }
        quesContent = "<p>Q" + quesContent + "</p>";
        quesContent = "<p>第 " + quesNumArray.join("，") + " 题</p>" + quesContent;

        $(".chosetm").before(quesContent);
    }

    function validateEnter(type, elem) {
        var _elem = $(elem);
        var reg;
        var message;
        if (type == "num") {
            reg = /^[0-9]*[1-9][0-9]*$/;
            message = "请输入大于0的合法数字!";
        } else if (type == "name") {
            reg = /\S/;
            message = "姓名输入不能为空!";
        } else if (type == "phone") {
            reg = /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/;
            message = "请输入合法的手机号码!";
        } else if (type == "email") {
            reg = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            message = "请输入合法的邮箱!";
        }
        if (!reg.test(_elem.val())) {
            _elem.css("color", "red");
            standardAlert(message);
        } else {
            _elem.css("color", "rgb(102, 102, 102)");
        }
    }

    function retryColor(elem) {
        var _elem = $(elem);
        _elem.css("color", "rgb(102, 102, 102)");
    }

    function saveDistribution() {
        var assignObj = new Object();

        /*需要分配的账号*/
        assignObj.accunntIds = ${accountIds};
        /*需要分配的账号对应的答题试卷*/
        assignObj.paperIds = ${paperIds};
        /*分配方式，0为按候选人分配，1为按题分配*/
        assignObj.scoreType = $("input:radio[name='scoreType']:checked").val();
        /*阅卷方式，0为匿名阅卷，1为不匿名阅卷*/
        assignObj.anonymous = $("input:radio[name='anonymous']:checked").val();
        /*是否发送短信通知，0为发送，1为不发送*/
        assignObj.sms = $("input:radio[name='sms']:checked").val();
        /*分为几组*/
        assignObj.groupCnt = $("#groupCnt").val();
        /*选题对应规则*/
        assignObj.selectRuleId = selectQuesRuleId;

        var groups = [];
        var groupInfos = $("#assign").parent().find(".groupComp");
        for (var i = 0; i < groupInfos.length; i++) {
            var groupObj = new Object();
            groupObj.number = $(groupInfos[i]).find(".officerCnt").val();
            groupObj.officers = [];

            var officers = $(groupInfos[i]).find(".officers").find("p");
            for (var m = 0; m < officers.length; m++) {
                var officerInfo = $(officers[m]).find("input:text");

                var officerObj = new Object();
                officerObj.name = $(officerInfo[0]).val();
                officerObj.phone = $(officerInfo[1]).val();
                officerObj.email = $(officerInfo[2]).val();

                groupObj.officers.push(officerObj);
            }
            groups.push(groupObj);
        }
        assignObj.groups = groups;
        assignObj.emailInfo = ue.getContent();

        var url = "${ctx}/assignPaper/save";
        var data = {"content": JSON.stringify(assignObj)};
        $.post(url, data, function (result) {
            if (result.state == "200") {
                standardAlert("success");
                window.location.reload();
            }
        });
    }
</script>
</body>
</html>
