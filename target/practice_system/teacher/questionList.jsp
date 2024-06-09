<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>试题列表</title>
    <link rel="stylesheet" href="../css/questionList.css">
</head>
<body>
<%-----------选择试题类型-------------%>
<div id="q_type">
    <div id="header">
        <h3>请选择题型</h3>
        <p class="info_msg">点击题目名称，可展开查看详细内容。</p>
    </div>
    <ul>
        <li><a onclick="viewSingleChoice()">单选题</a></li>
        <li><a onclick="viewFillBlank()">填空题</a></li>
        <li><a onclick="viewProgram()">编程题</a></li>
    </ul>
</div>



<%-----------可变内容部分-------------%>
<div id="container">
    <div id="change_box">
        <iframe id="changeContent" width="100%" height="500px" frameborder="0"></iframe>
    </div>
</div>

<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

<script type="text/javascript">
    // 点击 li 标签，给它加一个 class 样式
    var li = document.querySelectorAll('#q_type li');
    for (var i = 0; i < li.length; i++) {
        li[i].onclick = function () {
            // 先移除其他li的class
            for (var i = 0; i < li.length; i++) li[i].className = '';
            this.className = 'selected';
        }
    }

    var selectedQuestions = [];

    function viewSingleChoice() {
        <%--var changeContent = document.getElementById('changeContent');--%>
        <%--var url = "<%= request.getContextPath() %>/teacher/questionList.jsp?qType=0";--%>
        <%--changeContent.src = url;--%>
        var changeContent = document.getElementById('changeContent');
        url = "${pageContext.request.contextPath}/teacher/viewQuestion.jsp?qType="+0;
        changeContent.src = url;
    }

    function viewFillBlank() {
        <%--var changeContent = document.getElementById('changeContent');--%>
        <%--var url = "<%= request.getContextPath() %>/teacher/questionList.jsp?qType=2";--%>
        <%--changeContent.src = url;--%>
        var changeContent = document.getElementById('changeContent');
        url = "${pageContext.request.contextPath}/teacher/viewQuestion.jsp?qType="+2;
        changeContent.src = url;
    }

    function viewProgram() {
        <%--var changeContent = document.getElementById('changeContent');--%>
        <%--var url = "<%= request.getContextPath() %>/teacher/questionList.jsp?qType=4";--%>
        <%--changeContent.src = url;--%>
        var changeContent = document.getElementById('changeContent');
        url = "${pageContext.request.contextPath}/teacher/viewQuestion.jsp?qType="+4;
        changeContent.src = url;
    }

    <%--function openDialog(){--%>
    <%--    if (selectedQuestions.length === 0) {--%>
    <%--        alert("请先选择题目！");--%>
    <%--        return;--%>
    <%--    }--%>
    <%--    var changeContent = document.getElementById('changeContent');--%>
    <%--    url = "${pageContext.request.contextPath}/teacher/releaseExamination.jsp";--%>
    <%--    url += "?qList=" + encodeURIComponent(qList)--%>
    <%--    console.log("Selected questions:", selectedQuestions);--%>
    <%--    console.log("url:", url);--%>
    <%--    changeContent.src = url;--%>
    <%--}--%>

    <%--// 展开行--%>
    <%--function showDetail(i) {--%>
    <%--    var openRow = document.getElementsByClassName("open_tr");--%>
    <%--    if (openRow[i].style.display == "table-row") {--%>
    <%--        openRow[i].style.display = "none";--%>
    <%--    } else {--%>
    <%--        openRow[i].style.display = "table-row";--%>
    <%--    }--%>
    <%--}--%>

    <%--function composeQuestion(qid){--%>
    <%--    if (!selectedQuestions.includes(qid)) {--%>
    <%--        selectedQuestions.push(qid);--%>
    <%--        alert("题号 " + qid + " 已选中！");--%>
    <%--        console.log("Selected questions:", selectedQuestions);--%>
    <%--    } else {--%>
    <%--        alert("题号 " + qid + " 已经选中！");--%>
    <%--    }--%>
    <%--}--%>

    <%--// 删除功能--%>
    <%--function deleteQuestion(qid) {--%>
    <%--    if(confirm('要删除这道题吗？')){--%>
    <%--        $.ajax({--%>
    <%--            url: "/DelQuestionServlet",--%>
    <%--            async: true,--%>
    <%--            type: "post",--%>
    <%--            dataType: "text",--%>
    <%--            data: {"qid": qid},--%>
    <%--            success: function (data) {--%>
    <%--                if (data === 'fail') {--%>
    <%--                    alert("删除失败！");--%>
    <%--                    return false;--%>
    <%--                }else {--%>
    <%--                    alert("删除成功！");--%>
    <%--                }--%>
    <%--            }--%>
    <%--        });--%>
    <%--    }--%>
    <%--}--%>

    <%--// 修改功能--%>
    <%--function updateQuestion(qid, qType, qLevel, qName, qContent, qKeyword, qAnswer, tScore) {--%>

    <%--    var changeContent = document.getElementById('changeContent');--%>
    <%--    var url = "";--%>

    <%--    if (qType === 0) { // 单选题--%>
    <%--        url = "${pageContext.request.contextPath}/teacher/updateSingleChoice.jsp";--%>
    <%--    } else if (qType === 2) { // 填空题--%>
    <%--        url = "${pageContext.request.contextPath}/teacher/updateFillBlank.jsp";--%>
    <%--    } else if (qType === 4) { // 编程题--%>
    <%--        url = "${pageContext.request.contextPath}/teacher/updateProgram.jsp";--%>
    <%--    }--%>

    <%--    url += "?qid=" + encodeURIComponent(qid) + "&qType=" + encodeURIComponent(qType) +--%>
    <%--        "&qLevel=" + encodeURIComponent(qLevel) + "&qName=" + encodeURIComponent(qName) +--%>
    <%--        "&qContent=" + encodeURIComponent(qContent) + "&qKeyword=" + encodeURIComponent(qKeyword) +--%>
    <%--        "&qAnswer=" + encodeURIComponent(qAnswer) + "&tScore=" + encodeURIComponent(tScore);--%>

    <%--    changeContent.src = url;--%>
    <%--}--%>

</script>

</body>
</html>
