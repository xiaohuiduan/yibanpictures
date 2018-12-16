<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  User: 吴逸尘
  Date: 18-11-12
  Time: 下午2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>跟我斗图吧(๑•̀ㅂ•́)و✧</title>


    <link rel="stylesheet" href="../static/css/bootstrap.min.css">
    <link rel="stylesheet" href="../static/css/douTuindex.css">


    <script src="../static/js/jquery-3.3.1.js"></script>
    <script src="../static/js/yb_h5.js"></script>

</head>

<body>

<div class="header">
    <div class="wrapper">
        <input id="search-input" type="text" name="search-name" placeholder="请输入表情包名称">
        <input type="button" name="search-button" onclick="searchImg()" value="搜索" class="btn btn-primary">
    </div>
</div>

<div class="content">
    <ul class="row-ul row">

        <c:forEach items="${msgResult.data.list}" var="imgJson">
            <li class="col-xs-offset-1 col-xs-5" style="background-color: #f5f5f5;">
                <span class="row">
                    <img src="${imgJson.image_url}">
                    <button type="button" name="download" onclick="downImg('${imgJson.image_url}')" value="#">下载表情包</button>
                </span>
            </li>
        </c:forEach>

    </ul>

</div>


<div class="next-page">
    <button type="button" id="prePage" onclick="prePage()" value="#"><a href="#">上一页</a></button>
    <button type="button" id="nextPage" onclick="nextPage()" value="#"><a href="#">下一页</a></button>

</div>
<div class="footer">Copyright @ 2018 by <a href="http://yiban.cn">易班技术部</a> Api @ Support by<a
        href="https://www.doutula.com/">斗图啦</a>
</div>
</body>
<%--<script src="../static/js/face.js"></script>--%>

<script>
    // 搜索词
    var word = "${param.word}";
    // 页数
    var page = parseInt("${param.page}");
    //还有有没有
    var more = "${msgResult.data.more}"
    if (more != "1") {
        //将下一页按钮去掉
        document.getElementById("nextPage").style.display = "none";
    }
    if (page == 1) {
        //将上一页按钮去掉
        document.getElementById("prePage").style.display = "none";
    }
    function downImg(imgUrl) {
        download_fun(imgUrl)
    }
    function searchImg() {
        var word = document.getElementById("search-input").value;
        window.location.href = "get?word=" + word + "&page=1";
    }

    function prePage() {
        // 假如前面还有数据
        if (page != 1) {
            window.location.href = "get?word=" + word + "&page=" + (page - 1);
        }
    }
    function nextPage() {
        // 还有数据
        if (more == "1") {
            window.location.href = "get?word=" + word + "&page=" + (page + 1);
        }
    }
</script>


</html>

