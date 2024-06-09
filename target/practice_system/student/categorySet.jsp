<%@ page import="com.violetks.entity.Student" %>
<%@ page import="com.violetks.entity.Examination" %>
<%@ page import="com.violetks.dao.QuestionDao" %>
<%@ page import="com.violetks.dao.RecordDao" %>
<%@ page import="com.violetks.dao.ExaminationDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 获取当前会话中的学生对象
    Student s = (Student) session.getAttribute("student");
    // 获取试题类型
    QuestionDao questionDao = new QuestionDao();
    ExaminationDao examinationDao = new ExaminationDao();
    RecordDao recordDao = new RecordDao();
    // 获取学生的班级信息
    String sClass = s.getsClass();
    // 通过班级信息获取作业列表
    List<Examination> examList = examinationDao.getAllExamination(sClass);
    // 将作业列表存储在请求属性中
    request.setAttribute("examList", examList);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>作业列表</title>
    <link rel="stylesheet" href="../css/base.css">
    <link rel="stylesheet" href="../css/categorySet.css">
</head>
<body>
<%-----------导航条-------------%>
<div id="nav">
    <h1>Java练习系统</h1>
    <ul>
        <li><a href="../index.jsp">首页</a></li>
        <li><a href="categorySet.jsp">试题分类</a></li>
        <li><a href="${pageContext.request.contextPath}/student/exerciseSet.jsp">练习记录</a></li>
        <li><a href="${pageContext.request.contextPath}/student/rankList.jsp">排行榜</a></li>
        <li>欢迎：${student.getsName()}</li>
        <li><a href="../logout.jsp" target="_top">退出</a></li>
    </ul>
</div>
<%-----------表格区域-------------%>
<div id="table_area">
    <table border="0" align="center">
        <caption><h2>作业列表</h2>
            <hr>
        </caption>
        <tr align="center">
            <td><b>作业号</b></td>
            <td><b>作业名称</b></td>
            <td><b>试题总数 </b></td>
            <td><b>已完成数量 </b></td>
            <td><b>发布时间</b></td>
            <td><b>进入作业</b></td>
        </tr>
        <%
            for (Examination exam : examList) {
                int eid = exam.getEid();
                String eName = exam.geteName();
                String qList = exam.getqList();
                Date addTime = exam.getAddTime();
                String tPhone = exam.gettPhone();
                int totalCount = examinationDao.getTotalCount(exam);
                int resolvedCount = recordDao.getResolvedCount(eid, s.getSid());
        %>
        <tr align="center">
            <td><%= eid %></td>
            <td><%= eName %></td>
            <td><%= totalCount %></td>
            <td><%= resolvedCount %></td>
            <td><%= (addTime == null ? "暂无" : addTime.toString()) %></td>
            <td>
                <a href="${pageContext.request.contextPath}/student/questionLevelSet.jsp?eid=<%= eid %>&qList=<%=URLEncoder.encode(qList, "UTF-8")%>">进入试题集</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>