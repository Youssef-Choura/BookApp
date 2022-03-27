<html>
<head>
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
    <link rel="stylesheet" href="./AdminPagesCss/EditUser.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css">
    <title>Title</title>
</head>
<body>
<div class="container">
    <h2>Book App</h2>
    <div class="welcome-message">
        <h3>~~ Welcome
            <c:if test="${ !empty sessionScope.login }">
                <c:out value="${sessionScope.login}"/>
            </c:if>
            !
        </h3>
    </div>
    <ul class="nav nav-pills nav-justified">
        <li class="nav-item">
            <a class="nav-link" href="AllBooks">Books</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="Users">Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="NewBook">New Book</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Logout">logout</a>
        </li>
    </ul>
    <div class="sub-container">
        <div class="title">Edit User</div>
        <div class="NewBookForm">
            <form method="post" action="EditUser">
                <div class="SubTitle-one">Personal Information</div>
                <div class="user-details">
                    <div class="input-box">
                        <span class="details">First name</span>
                        <label for="FirstName">
                            <input type="text" name="FirstName" id="FirstName" placeholder="Enter your first name "
                                   value="<c:out value="${CurrentUser.getFirstName()}"/>" required>
                        </label>
                    </div>
                    <div class="input-box">
                        <span class="details">Family name</span>
                        <label for="FamilyName">
                            <input type="text" name="FamilyName" id="FamilyName" placeholder="Enter your family name "
                                   value="<c:out value="${CurrentUser.getFamilyName()}"/>" required>
                        </label>
                    </div>
                    <div class="input-box">
                        <span class="details">Address</span>
                        <label for="Address">
                            <input type="text" name="Address" id="Address" placeholder="Enter your Address"
                                   value="<c:out value="${CurrentUser.getAddress()}"/>" required>
                        </label>
                    </div>
                    <div class="input-box">
                        <span class="details">Email</span>
                        <label for="Email">
                            <input type="email" name="Email" id="Email" placeholder="example@example.example"
                                   value="<c:out value="${CurrentUser.getEmail()}"/>" required>
                        </label>
                        <c:if test="${!empty EmailFormatError || !empty EmailError }">
                            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                                <c:out value="${EmailFormatError}"/>
                                <c:out value="${EmailError}"/>
                            </p>
                        </c:if>
                    </div>
                    <div class="input-box">
                        <span class="details">Phone Number</span>
                        <label for="Telephone">
                            <input type="text" name="Telephone" id="Telephone" placeholder="(+216)"
                                   value="<c:out value="${CurrentUser.getTelephone()}"/>" required>
                        </label>
                        <c:if test="${!empty TelephoneError || !empty TelephoneFormatError }">
                            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                                <c:out value="${TelephoneError}"/>
                                <c:out value="${TelephoneFormatError}"/>
                            </p>
                        </c:if>
                    </div>
                </div>
                <div class="gender-details">
                    <input type="radio" name="Gender" id="dot-1" value="Male" checked>
                    <input type="radio" name="Gender" id="dot-2" value="Female">
                    <input type="radio" name="Gender" id="dot-3" value="Other">
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
                        <option>
                            <c:out value="${CurrentUser.getGrade()}"/>
                        </option>
                        <option value="Student">Student</option>
                        <option value="Worker">Worker</option>
                        <option value="Jobless">Jobless</option>
                    </select>
                </div>
                <div class="SubTitle-two">Login Information</div>

                <div class="Login-Information">
                    <div class="input-box">
                        <span class="details">Username</span>
                        <label for="Username">
                            <input type="text" name="Login" id="Username" placeholder="Username (maximum 10 digits)"
                                   value="<c:out value="${CurrentUser.getLogin()}"/>" required>
                        </label>
                        <c:if test="${!empty LoginLengthError || !empty LoginError }">
                            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                                <c:out value="${LoginLengthError}"/>
                                <c:out value="${LoginError}"/>
                            </p>
                        </c:if>
                    </div>

                    <div class="input-box">
                        <span class="details">Password</span>
                        <label for="Password">
                            <input type="password" name="Password" id="Password" value="<c:out value="${CurrentUser.getPassword()}"/>"
                                   placeholder="Password(minimum 6 digits)" required>
                        </label>
                        <c:if test="${!empty PasswordLengthError }">
                            <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                                <c:out value="${PasswordLengthError}"/>
                            </p>
                        </c:if>
                    </div>
                </div>
                <div class="button">
                    <input type="submit" value="Validate">
                </div>
                <c:if test="${!empty UniqueError }">
                    <p style="font-size: 12px ; margin: 15px ; color: #9b59b6">
                        <c:out value="${UniqueError}"/>
                    </p>
                </c:if>
            </form>
        </div>
    </div>
</div>
</body>
</html>
