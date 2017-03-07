<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="pojo.valueObject.domain.Test" %><%--
  Created by IntelliJ IDEA.
  User: geyao
  Date: 2017/3/7
  Time: 上午10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
<%
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    Test test1 = ctx.getBean("test", Test.class);
    Test test2 = (Test) ctx.getBean("test");
    out.println( (test1 == test2) + "<br/>");
    out.println( test1 + "<br/>");
    out.println( test2 );
%>
</body>
</html>
