<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin_yk
  Date: 2017/7/24
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/elastislide.css"/>
    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="${ctx}/static/javascript/modernizr.custom.js"></script>
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
        <div class="utl" style="width:148px">
            <div class="utlc" style="width:140px">
                选择题目
            </div>
        </div>
        <div class="anscondition">
            <div class="col-md-12 noBorder">
                <c:forEach var="factor" items="${result}">
                    <div class="tikude">
                        <h3 class="tikuTitle">${factor.factorName}<label class="quanxuan"><input type="checkbox"
                                                                                                 onchange="checkAll(this)">
                            全选</label></h3>

                        <c:forEach var="ques" items="${factor.quesUnits}">
                            <label class="ti" style="min-width: 98%;">
                                <input class="ifChoose" type="checkbox" quesId="${ques.quesId}"
                                       paperId="${ques.paperId}" corpCode="${ques.corpCode}"
                                       subquesNum="${ques.subquesNum}" quesNum="${ques.number}">
                                <p>${ques.number}.[${ques.paperShortName}]-${ques.content}</p>
                            </label>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
            <div class="formsub">
                <a href="javascript:win_close();" class="reset qianLu">关闭</a>
                <a href="javascript:saveSelectQues();" class="save luSe">确定</a>
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
<script>
    function checkAll(elem) {
        var _elem = $(elem);
        var ischecked = _elem.is(':checked');
        var checks = _elem.parents(".tikude").find(".ifChoose");
        for (var i = 0; i < checks.length; i++) {
            $(checks[i]).attr("checked", ischecked);
        }
    }

    function saveSelectQues() {
        var checkeds = $(".anscondition").find(".ifChoose");
        var array_questionId = [];
        var array_number = [];
        var corpCode = "";
        var isFirst = true;
        var quesContent = "";
        for (var i = 0; i < checkeds.length; i++) {
            if ($(checkeds[i]).is(":checked")) {

                if (isFirst) {
                    quesContent = $(checkeds[i]).next().html();
                    isFirst = false;
                }

                var paperId = $(checkeds[i]).attr("paperId");
                var quesId = $(checkeds[i]).attr("quesId");
                var subquesNum = $(checkeds[i]).attr("subquesNum");
                var quesNum = $(checkeds[i]).attr("quesNum");

                if (subquesNum == 0) {
                    array_questionId.push(paperId + "_" + quesId + "_" + quesNum);
                } else {
                    for (var j = 1; j <= subquesNum; j++) {
                        array_questionId.push(paperId + "_" + quesId + ":" + j + "_" + quesNum);
                    }
                }
                array_number.push(parseInt(quesNum));
            }
            corpCode = $(checkeds[i]).attr("corpCode");
        }
        var url = "${ctx}/selectQues/save";
        $.get(url, {
            "corpCode": corpCode,
            "selectedQues": JSON.stringify(array_questionId)
        }, function (result) {
            if (result.state == "200") {
                opener.selectedQues(array_number, quesContent, result.data);
                win_close();
            }
        });
    }

    function win_close() {
        window.opener = null;
        window.open('', '_self');
        window.close();
    }

</script>
</body>
</html>
