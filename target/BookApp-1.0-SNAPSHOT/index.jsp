<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Log in BooksApp</title>
    <link rel="stylesheet" type="text/css" href="LoginCss/Login.css">
</head>
<body>
<div class="LoginBox" style="height: max-content;">
    <div class="title">Login</div>
    <form method="post" action="Login">
        <div class="User-details">
            <div class="input-box">
                <span class="details">Username</span>
                <label for="Username">
                    <input type="text" name="Username" id="Username" placeholder="Enter a maximum 10 digits username" required>
                </label>
                <c:if test="${!empty LoginLengthError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${LoginLengthError}"/>
                    </p>
                </c:if>
            </div>
            <div class="input-box">
                <span class="details">Password</span>
                <label for="Password">
                    <input type="password" name="Password" id="Password" placeholder="Enter a minimum 6 digits password" required>
                </label>
                <c:if test="${!empty PasswordLengthError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${PasswordLengthError}"/>
                    </p>
                </c:if>
            </div>
        </div>
        <c:if test="${!empty LoginInfoError }">
            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                <c:out value="${LoginInfoError}"/>
            </p>
        </c:if>
        <div class="button">
            <input type="submit" value="Login">
        </div>
        <c:if test="${!empty NoSessionError }">
            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                <c:out value="${NoSessionError}"/>
            </p>
        </c:if>
        <div class="Register-ref">Or
            <a href="Registration.jsp">Register</a>
        </div>
    </form>
</div>
