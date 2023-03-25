<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="上传">
</form>

<h1>文件列表</h1>
<c:forEach items="${files}" var="file">
    <a href="download?file=${file}">${file}</a><br>
</c:forEach>

</body>
</html>
