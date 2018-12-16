<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  User: 吴逸尘
  Date: 18-11-12
  Time: 下午2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>人脸识别</title>

    <link rel="stylesheet" href="../static/css/bootstrap.min.css">
    <link rel="stylesheet" href="../static/css/faceIndex.css">
    <link rel="stylesheet" href="../static/css/loading.css">
    <script src="../static/js/jquery-3.3.1.js"></script>
    <script src="../static/js/loading.js"></script>
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?58a6d06d21cd0534b426c0c9894e689a";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>

</head>
<body>
<div class="header">
    <span class="title1"> 人 </span>
    <span class="title2"> 脸 </span>
    <span class="title3"> 识 </span>
    <span class="title4"> 别 </span>
</div>
<div class="content col-xs-offset-3 col-xs-6">
    <img class="picture"/>
    <div class="data">
        <ul class="return-info">
            <li class="gender">性别&nbsp：&nbsp<span></span></li>
            <li class="age">年龄&nbsp：&nbsp<span></span></li>
            <li class="skin-color">肤色&nbsp：&nbsp<span></span></li>
            <li class="beauty">颜值&nbsp：&nbsp<span></span></li>
            <li class="expression">表情&nbsp：&nbsp<span></span></li>
            <li class="emotion">情绪&nbsp：&nbsp<span></span></li>
            <li class="face-shape">脸型&nbsp：&nbsp<span></span></li>
            <li class="glasses">戴眼镜：<span></span></li>
            <br>
            <br>
            <br>
            <br>
        </ul>
    </div>

</div>
<div class="footer">
    <a class="upload">选择文件
        <input id="file1" type="file" multiple="multiple"/>
    </a>
    <span>copyright by 易班技术部</span>
</div>

</body>

<script src="../static/js/face.js" type="text/javascript" charset="utf-8"></script>
</html>