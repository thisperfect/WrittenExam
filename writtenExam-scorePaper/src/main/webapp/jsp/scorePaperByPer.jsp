<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin_yk
  Date: 2017/7/28
  Time: 9:27
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
    <c:set var="accountInfoMap" value="${data.accountInfoMap}"/>
    <c:set var="quesInfos" value="${data.quesInfoList}"/>
    <title></title>

    <link rel="stylesheet" href="${ctx}/static/css/common.css">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <style>
        .dropdown-menu {
            -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0);
        }

        .btn-danger, .btn-default, .btn-info, .btn-primary, .btn-success, .btn-warning {
            text-shadow: 0 -1px 0 rgba(0, 0, 0, 0);
            -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .15), 0 1px 1px rgba(0, 0, 0, 0);
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, .15), 0 1px 1px rgba(0, 0, 0, 0);
        }

        .tabText {
            width: 42px;
            height: 36px;
            float: left;
        }

        .titleStyle {
            padding-top: 35px;
            padding-bottom: 35px;
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
        <div class="yjsearch">
            <div class="dropdown pagebt" style="float:left">
                <button style="" class="btn btn-default dropdown-toggle btnsele yjBtn" type="button" id="dropdownMenu1"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    按题目
                    <span class="caret sd" style="float: right;"></span>
                </button>
                <ul class="dropdown-menu dropsele yjBtnUl" aria-labelledby="dropdownMenu1" style="">
                    <li><a href="#">按候选人</a></li>
                </ul>
            </div>
            <div class="dropdown pagebt" style="float:left">
                <button style="" class="btn btn-default dropdown-toggle btnsele yjBtn" type="button" id="dropdownMenu2"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    未阅卷(20)
                    <span class="caret sd" style="float: right;"></span>
                </button>
                <ul class="dropdown-menu dropsele yjBtnUl" aria-labelledby="dropdownMenu2" style="">
                    <li><a href="#">已阅卷(20)</a></li>
                </ul>
            </div>
            <label class="yjsearchRight">
                <input placeholder="请输入关键字"/><i class="icon-search"></i>
            </label>
        </div>
        <ul class="ksName">
            <li class="ksNamePrior" style=""></li>
            <c:forEach var="accountId" items="${accountInfoMap.keySet()}" varStatus="status">
                <c:if test="${status.index==0}">
                    <!--添加bgcb背景色为白色-->
                    <li class="ksNameLi bgcBai" accountId="${accountId}">
                </c:if>
                <c:if test="${status.index!=0}">
                    <li class="ksNameLi" accountId="${accountId}">
                </c:if>
                <span class="tabText">${accountId}</span><i
                    class="closeBtn"></i></li>
            </c:forEach>
            <li class="ksNameNext"></li>
        </ul>
        <div class="ksAnswer">
            <ul class="ksAnswerD">
                <c:forEach var="ques" items="${quesInfos}">
                    <li class="ksAnswerDli" quesId="${ques.id}">
                        <div class="titleStyle">
                            <span class="spanLeft">第${ques.order}题：</span>
                            <span class="spanRight">${ques.content}
                                <c:forEach var="subques" items="${ques.ssubQuesList}">
                                    <span style="display:block;padding-top: 10px;">${subques.order}、${subques.content}（总分：${subques.score}分）</span>
                                </c:forEach>
                            </span>
                        </div>
                        <div>
                            <span class="spanLeft">参考答案：</span>
                            <span class="spanRight">
                                <c:forEach var="subques" items="${ques.ssubQuesList}">
                                    ${subques.referAnswer}
                                </c:forEach>
                            </span>
                        </div>
                        <div style="margin-top: 30px">
                            <span class="spanLeft">候选人答案：</span>
                            <span class="spanRight personAnswer"></span>
                        </div>
                        <div class="ksPin" style="">
                            <span class="ksPinFen">评分<label><input placeholder="输入评价分数"
                                                                   onblur="saveContent(this);"/>分</label></span>
                            <span class="ksPinYu">评语<input class="ksPingYuInput"
                                                           onblur="saveContent(this);"/></span>
                        </div>
                    </li>
                </c:forEach>
            </ul>

        </div>
        <div class="col-md-12">
            <div class="formsub" style="margin-top: 0">
                <a href="#" class="reset qianLu">关闭</a>
                <a href="#" class="save luSe" onclick="javascript:saveResult();">提交</a>
            </div>
        </div>
    </div>
</div>
<div id="goTop" class="goTop" style="display: none">

</div>
<script src="${ctx}/static/javascript/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/javascript/action.js" charset="utf-8"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script>

    var answerResult = ${data.resultInfoStr};
    var scoreResult = new Object();

    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 100) {
                $("#goTop").show();
            }
            else {
                $("#goTop").hide();
            }
        });

        fillResult();
    });

    $(".ksNameLi").on("click", function () {
        $(".bgcBai").removeClass("bgcBai");

        var _elem = $(this);
        _elem.addClass("bgcBai");

        fillResult();
    });

    $(".closeBtn").on("click", function (event) {
        var _elem = $(this);
        var tab_size = _elem.parent().parent().find(".ksNameLi").length;
        var index = _elem.parent().index();
        var isChose = _elem.parent().is(".bgcBai");
        if (index != tab_size) {
            if (isChose)
                _elem.parent().next().addClass("bgcBai");
            _elem.parent().hide();
        } else if (index > 0) {
            if (isChose)
                _elem.parent().prev().addClass("bgcBai");
            _elem.parent().hide();
        } else if (index <= 0) {
            alert("只剩最后一个候选人了，无法关闭啦！");
        }
        /*禁止点击事件的向上传递*/
        event.stopPropagation();
    });

    function fillResult() {
        var accountId = $(".bgcBai").attr("accountId");
        var resultInfo = answerResult[accountId];

        var answers = $(".ksAnswer").find(".personAnswer");
        for (var i = 0; i < answers.length; i++) {
            var questionId = $(answers[i]).parents(".ksAnswerDli").attr("quesId");
            var ques_result = resultInfo[questionId];

            if (ques_result != null) {
                $(answers[i]).html(ques_result.answerContent);

                if (ques_result.isScore == 0) {
                    $(answers[i]).parent().next().find(".ksPinFen").find("input").val(ques_result.realScore);
                } else {
                    $(answers[i]).parent().next().find(".ksPinFen").find("input").val(null);
                }

                $(answers[i]).parent().next().find(".ksPingYuInput").val(ques_result.assessContent);
            }
        }
    }

    function saveContent(elem) {
        var _elem = $(elem);
        var accountId = $(".bgcBai").attr("accountId");
        var questionId = _elem.parents(".ksAnswerDli").attr("quesId");
        var result_unit = answerResult[accountId][questionId];
        if (_elem.is(".ksPingYuInput")) {
            result_unit.assessContent = _elem.val();
        } else {
            result_unit.realScore = parseFloat(_elem.val());
            result_unit.isScore = 0;

            if (scoreResult[accountId] != null && scoreResult[accountId][questionId] != null) {
                scoreResult[accountId][questionId] = result_unit;
            }else{
                var obj = new Object();
                obj[questionId] = result_unit;
                scoreResult[accountId] = obj;
            }
        }
    }
</script>
</body>
</html>


