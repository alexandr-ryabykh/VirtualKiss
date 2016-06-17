<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<img src="images/1.jpg">--%>
<%
    String token = (String) session.getAttribute("token");
%>
<sql:setDataSource var="userImages"
                   driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/registration"
                   user="root" password="root" />

<sql:query dataSource="${userImages}"
           sql="SELECT DISTINCT link FROM images WHERE token='${token}'" var="result" />


    <c:forEach var="row" items="${result.rows}">
        <img src="/images/${row.link}" width="500" height="500"/>
    </c:forEach>



<%--<img src="${pageContext.request.contextPath}/images/${fileName}">--%>
<%--<form action="<%=request.getContextPath()%>/UserProfile" method="post">
   &lt;%&ndash; <c:forEach items="${fileName}" var="fileName">
        <img src="${pageContext.request.contextPath}/images/${fileName}">
    </c:forEach>&ndash;%&gt;
       <%=request.getParameter("link")%>
   <img src="<%=request.getParameter("link")%>">
</form>--%>

</body>
</html>
