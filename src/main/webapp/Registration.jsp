<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- ===== Iconscout CSS ===== -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
    <!-- ===== CSS ===== -->
    <link rel="stylesheet" href="./RegistrationCss/Registration.css">
    <title>Registration Form</title>

</head>
<body style="height: 130vh;">
<div class="container">
    <div class="title">Registration</div>
    <form method="post" action="Register">
        <!--Personal Information-->

        <div class="SubTitle-one">Personal Information</div>

        <div class="user-details">
            <div class="input-box">
                <span class="details">First name</span>
                <label for="FirstName">
                    <input type="text" name="FirstName" id="FirstName" placeholder="Enter your first name " value="<c:out value="${requestScope.CurrentUser.getFirstName()}"/>" required>
                </label>
            </div>
            <div class="input-box">
                <span class="details">Family name</span>
                <label for="FamilyName">
                    <input type="text" name="FamilyName" id="FamilyName" placeholder="Enter your family name " value="<c:out value="${requestScope.CurrentUser.getFamilyName()}"/>" required>
                </label>
            </div>
            <div class="input-box">
                <span class="details">Address</span>
                <label for ="Address">
                    <input type="text" name="Address" id="Address" placeholder="Enter your Address" value="<c:out value="${requestScope.CurrentUser.getAddress()}"/>" required>
                </label>
            </div>
            <div class="input-box">
                <span class="details">Email</span>
                <label for="Email">
                    <input type="email" name="Email" id="Email" placeholder="example@example.example" value="<c:out value="${requestScope.CurrentUser.getEmail()}"/>" required>
                </label>
                <c:if test="${!empty requestScope.EmailFormatError || !empty requestScope.EmailError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.EmailFormatError}"/>
                        <c:out value="${requestScope.EmailError}"/>
                    </p>
                </c:if>
            </div>

            <div class="input-box">
                <span class="details">Phone Number</span>
                <label for="Telephone">
                    <input type="text" name="Telephone" id="Telephone" placeholder="(+216)" value="<c:out value="${requestScope.CurrentUser.getTelephone()}"/>" required>
                </label>
                <c:if test="${!empty requestScope.TelephoneError || !empty requestScope.TelephoneFormatError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.TelephoneError}"/>
                        <c:out value="${requestScope.TelephoneFormatError}"/>
                    </p>
                </c:if>
            </div>
        </div>
        <div class="gender-details">
            <input type="radio" name="Gender" id="dot-1" value="Male" checked>
            <input type="radio" name="Gender" id="dot-2" value="Female" >
            <input type="radio" name="Gender" id="dot-3" value="Other" >
            <span class="gender-title">Gender</span>
            <div class="category">
                <label for="dot-1">
                    <span class="dot one"></span>
                    <span class="gender">Male</span>
                </label>
                <label for="dot-2">
                    <span class="dot two"></span>
                    <span class="gender">Female</span>
                </label>
                <label for="dot-3">
                    <span class="dot three"></span>
                    <span class="gender">Other</span>
                </label>
            </div>
        </div>
        <div class="grade-details">
            <label for="Grade">Grade</label>
            <select name="Grade" id="Grade" required>
                <option><c:out value="${requestScope.CurrentUser.getGrade()}"/></option>
                <option value="Student">Student</option>
                <option value="Worker">Worker</option>
                <option value="Jobless">Jobless</option>
            </select>
        </div>

        <!--Login Information-->

        <div class="SubTitle-two">Login Information</div>

        <div class="Login-Information">
            <div class="input-box">
                <span class="details">Username</span>
                <label for="Username">
                    <input type="text" name="Username" id="Username" placeholder="Username (maximum 10 digits)" value="<c:out value="${requestScope.CurrentUser.getLogin()}"/>" required>
                </label>
                <c:if test="${!empty requestScope.LoginLengthError || !empty requestScope.LoginError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.LoginLengthError}"/>
                        <c:out value="${requestScope.LoginError}"/>
                    </p>
                </c:if>
            </div>

            <div class="input-box">
                <span class="details">Password</span>
                <label for="Password">
                    <input type="password" name="Password" id="Password" placeholder="Password(minimum 6 digits)" required>
                </label>
                <c:if test="${!empty requestScope.PasswordLengthError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.PasswordLengthError}"/>
                    </p>
                </c:if>
            </div>

            <div class="input-box">
                <span class="details">Password Confirmation</span>
                <label for="PasswordConfirmation">
                    <input type="password" name="PasswordConfirmation" id="PasswordConfirmation" placeholder="Confirm your password" required>
                </label>
                <c:if test="${!empty requestScope.PassConfirmationError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.PassConfirmationError}"/>
                    </p>
                </c:if>
            </div>
        </div>
        <div class="button">
            <input type="submit" value="Register">
        </div>
        <c:if test="${!empty requestScope.UniqueError }">
            <p style="font-size: 12px ; margin: 15px ; color: #9b59b6">
                <c:out value="${requestScope.UniqueError}"/>
            </p>
        </c:if>
        <div class="Login-ref">
            <a href="index.jsp">Already have an account ?</a>
        </div>
    </form>
</div>
</body>
</html>