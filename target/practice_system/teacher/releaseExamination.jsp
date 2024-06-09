<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<%
    request.setCharacterEncoding("UTF-8");
    // Read query parameters
    String qList = URLDecoder.decode(request.getParameter("qList"), "UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>填写作业信息</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/ReleaseExaminationServlet" method="post" accept-charset="UTF-8">
    <p><input type="hidden" name="q_list" value="<%= qList %>"/></p>
    <p><span>作业ID：</span><input type="number" name="e_id" required></p>
    <p><span>作业名称：</span><input type="text" name="e_name" required></p>
    <p><span>教师电话：</span><input type="text" name="t_phone" required></p>
    <p><span>发布班级：</span><input type="text" name="s_class" required></p>
    <p><input type="submit" class="add_btn" value="发布"/></p>
</form>
</body>
</html>
