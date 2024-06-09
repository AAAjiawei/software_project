<%@ page import="com.violetks.dao.QuestionDao" %>
<%@ page import="com.violetks.entity.Question" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuestionDao dao = new QuestionDao();
    int qType = Integer.parseInt(request.getParameter("qType"));
    List<Question> questionList = dao.getQuestionList(qType);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查看试题列表</title>
    <link rel="stylesheet" href="../css/viewQuestion.css">
</head>
<body>
<%-----------表格区域-------------%>
<div id="table_area">
    <%-----------表头-------------%>
    <table border="0" align="center" id="tableTh">
        <tr align="center">
            <td><b>题号</b></td>
            <td style="width: 150px"><b>试题名称</b></td>
            <td><b>难度等级</b></td>
            <td><b>关键字</b></td>
            <td><b>分数</b></td>
            <td><b>操作</b></td>
        </tr>
    </table>

    <%-----------表格区域-------------%>
    <table align="center" id="questionTable">
        <%
            for (int i = 0; i < questionList.size(); i++) {
                Question question = questionList.get(i);
        %>
        <tr align="center">
            <td><%=question.getQid() %>
            </td>
            <td style="width: 150px"><a class="qName" onclick="showDetail(<%=i%>)">
                <%if (qType == 2) {%><%=question.getqContent() %>
                <% } else {%><%=question.getqName() %>
                <% } %>
            </a></td>
            <td><%if (question.getqLevel() == 0) {%>较易
                <% } else if (question.getqLevel() == 1) {%>容易
                <% } else if (question.getqLevel() == 2) {%>难
                <% } else {%>较难
                <% } %>
            </td>
            <td><%=question.getqKeyword() %></td>
            <td><%=question.gettScore() %></td>
            <td>
                <input type="submit" class="enter_btn" value="组题" onclick="composeQuestion(<%=question.getQid()%>)"/>
                <input type="submit" class="enter_btn" value="删除" onclick="deleteQuestion(<%=question.getQid()%>)"/>
                <input type="submit" class="enter_btn" value="修改" onclick="updateQuestion(
                    <%=question.getQid() %>,<%=question.getqType()%>,<%=question.getqLevel()%>,
                    '<%=question.getqName()%>','<%=question.getqContent()%>','<%=question.getqKeyword()%>',
                    '<%=question.getqAnswer()%>',<%=question.gettScore()%>)"/>
            </td>
        </tr>
        <tr class="open_tr">
            <td colspan="6">
                <div class="qDetails">
                    <h4>查看试题详细信息</h4>
                    <p><b><%if (qType == 2) {%><%=question.getqContent() %>
                        <% } else {%><%=question.getqName() %><%} %>
                    </b><span>（<%=question.gettScore() %>分）</span></p>
                    <p><%if (qType == 0) {%><span>题目内容：</span><%=question.getqContent() %>
                        <% } else if (qType == 2) {%><%="" %>
                        <% } else {%><span>题目内容：</span><%=question.getqContent() %>
                        <% } %>
                    </p>
                    <p><%if (question.getqAnswer() == null) {%><span></span>
                        <% } else {%><span>正确答案：</span><%=question.getqAnswer() %>
                        <% } %>
                    </p>
                    <p><span>关键字：</span><span><%=question.getqKeyword() %></span></p>
                    <p><span>添加时间：</span><span><%=dao.getAddTime(question.getQid()) %></span></p>
                </div>
            </td>
        </tr>
        <% } %>
    </table>
</div>

<div id="composeButtonContainer">
    <button id="composeButton" onclick="openDialog()">发布作业</button>
</div>

<div id="dialog-form" title="发布作业" style="display: none;">
    <form>
        <fieldset>
            <label for="examId">作业ID</label>
            <input type="text" name="examId" id="examId" class="text ui-widget-content ui-corner-all">
            <label for="examName">作业名称</label>
            <input type="text" name="examName" id="examName" class="text ui-widget-content ui-corner-all">
            <label for="tPhone">教师电话号码</label>
            <input type="text" name="tPhone" id="tPhone" class="text ui-widget-content ui-corner-all">
            <label for="sClass">发布班级</label>
            <input type="text" name="sClass" id="sClass" class="text ui-widget-content ui-corner-all">
        </fieldset>
    </form>
</div>

<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

<div id="change_box">
    <iframe id="changeContent" width="100%" height="500px" frameborder="0"></iframe>
</div>

<script type="text/javascript">
    var selectedQuestions = [];

    // 点击 li 标签，给它加一个 class 样式
    var li = document.querySelectorAll('#q_type li');
    for (var i = 0; i < li.length; i++) {
        li[i].onclick = function () {
            // 先移除其他li的class
            for (var i = 0; i < li.length; i++) li[i].className = '';
            this.className = 'selected';
        }
    }


    function openDialog(){
        if (selectedQuestions.length === 0) {
            alert("请先选择题目！");
            return;
        }
        var qList = encodeURIComponent(selectedQuestions.join(','));
        var changeContent = document.getElementById('changeContent');
        url = "${pageContext.request.contextPath}/teacher/releaseExamination.jsp";
        url += "?qList=" + encodeURIComponent(qList)
        console.log("Selected questions:", selectedQuestions);
        console.log("url:", url);
        changeContent.src = url;
    }

    // 展开行
    function showDetail(i) {
        var openRow = document.getElementsByClassName("open_tr");
        if (openRow[i].style.display == "table-row") {
            openRow[i].style.display = "none";
        } else {
            openRow[i].style.display = "table-row";
        }
    }

    function composeQuestion(qid){
        if (!selectedQuestions.includes(qid)) {
            selectedQuestions.push(qid);
            alert("题号 " + qid + " 已选中！");
            console.log("Selected questions:", selectedQuestions);
        } else {
            alert("题号 " + qid + " 已经选中！");
        }
    }


    // 删除功能
    function deleteQuestion(qid) {
        if(confirm('要删除这道题吗？')){
            $.ajax({
                url: "/DelQuestionServlet",
                async: true,
                type: "post",
                dataType: "text",
                data: {"qid": qid},
                success: function (data) {
                    if (data === 'fail') {
                        alert("删除失败！");
                        return false;
                    }else {
                        alert("删除成功！");
                    }
                }
            });
        }
    }
    // 修改功能
    function updateQuestion(qid, qType, qLevel, qName, qContent, qKeyword, qAnswer, tScore) {

        var changeContent = document.getElementById('changeContent');
        var url = "";

        if (qType === 0) { // 单选题
            url = "${pageContext.request.contextPath}/teacher/updateSingleChoice.jsp";
        } else if (qType === 2) { // 填空题
            url = "${pageContext.request.contextPath}/teacher/updateFillBlank.jsp";
        } else if (qType === 4) { // 编程题
            url = "${pageContext.request.contextPath}/teacher/updateProgram.jsp";
        }

        url += "?qid=" + encodeURIComponent(qid) + "&qType=" + encodeURIComponent(qType) +
            "&qLevel=" + encodeURIComponent(qLevel) + "&qName=" + encodeURIComponent(qName) +
            "&qContent=" + encodeURIComponent(qContent) + "&qKeyword=" + encodeURIComponent(qKeyword) +
            "&qAnswer=" + encodeURIComponent(qAnswer) + "&tScore=" + encodeURIComponent(tScore);

        changeContent.src = url;
    }

</script>
</body>
</html>
