<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人博客</title>
    <link href="statics/css/login.css" rel="stylesheet" type="text/css" />
    <script src="statics/js/jquery-1.11.3.js" type=text/javascript></script>

    <script src="statics/js/config.js" type="text/javascript"></script>
    <script src="statics/js/util.js" type=text/javascript></script>

    <style>
        * {
            box-sizing: border-box;
        }

        .loginbg {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background-image: url("/statics/images/loginbg.jpg");
            background-size: 100% 100%;
            background-repeat: no-repeat;
        }

        .login-box {
            width: 360px;
            margin: 3% auto;
        }
        .login-logo {
            font-size: 35px;
            text-align: center;
            margin-bottom: 25px;
            font-weight: 300;
        }

        .login-box-body {
            background: rgba(255,255,255,0.5);
            padding: 20px;
            border-top: 0;
            color: #666;
            border-radius: 10px;
        }

        .logo {
            text-align: center;
            width: calc(70% + 2px);
            margin-left: 10%;
            margin-bottom: 5px;
        }

        .logo img {
            width: 200px;
            height: 200px;
        }
        .logo .project {
            display: block;
            width: 100%;
            height: 30px;
            font-size: 18px;
        }


        .form-group {
            margin-bottom: 15px;
        }

        .form-control {
            display: block;
            width: 100%;
            height: 34px;
            padding: 6px 12px;
            font-size: 14px;
            line-height: 1.428;
            color: #555555;
            background-color: #fff;
            background-image: none;
            border: 1px solid #ccc;
        }


    </style>
</head>
<body style="overflow: auto">
<div class="loginbg"/>

<div class="login-box">

    <div class="login-logo">
        <a href="#"><b>系统英文简写</b></a>
    </div>

    <div class="login-box-body">

        <div class="logo">
            <img src="statics/images/logo.png">
            <span class="project">个人博客</span>
        </div>

        <form action="${pageContext.request.contextPath}/user" method="post" id="loginForm">
            <%--隐藏的动作参数--%>
            <input type="hidden" name="actionName" value="login"/>

            <%--用户名--%>
            <div class="form-group">
                <input type="text" class="form-control" id="userName" name="userName" value="${resultInfo.result.uname}" placeholder="用户">
            </div>

            <%--密码--%>
            <div class="form-group">
                <input type="text" class="form-control" id="userPwd" name="userPwd" value="${resultInfo.result.upwd}" placeholder="密码">
            </div>

            <input name="rem" type="checkbox" value="1" class="inputcheckbox"/><label>记住我</label>
            <span id="msg" style="color: red;font-size: 12px;">${resultInfo.msg}</span><br /><br />
            <input type="button" class="log jc yahei16" value="登 录" onclick="checkLogin()" />&nbsp; &nbsp; &nbsp; <input type="reset" value="取 消" class="reg jc yahei18" />
        </form>
    </div>
</div>

</body>
</html>