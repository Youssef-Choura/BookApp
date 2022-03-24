<html>
<head>
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
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
            !</h3>
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
    <div class="NewBookForm">
        <form method="post" action="EditUser">
            <div class="form-group row mx-sm-3 ">
                <label for="Login" class="col-sm-2 col-form-label">Login</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="Login" id="Login" placeholder="Login"
                           value="<c:out value="${CurrentUser.getLogin()}"/>" required>
                </div>
                <c:if test="${!empty LoginLengthError || !empty LoginError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${LoginLengthError}"/>
                        <c:out value="${LoginError}"/>
                    </p>
                </c:if>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="Password" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-3">
                    <input type="password" class="form-control" name="Password" id="Password" placeholder="Password"
                           value="<c:out value="${CurrentUser.getPassword()}"/>" required>
                </div>
                <c:if test="${!empty PasswordLengthError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${PasswordLengthError}"/>
                    </p>
                </c:if>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="FirstName" class="col-sm-2 col-form-label">First Name</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="FirstName" id="FirstName" placeholder="FirstName"
                           value="<c:out value="${CurrentUser.getFirstName()}"/>" required>
                </div>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="FamilyName" class="col-sm-2 col-form-label">Family Name</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="FamilyName" id="FamilyName" placeholder="FamilyName"
                           value="<c:out value="${CurrentUser.getFamilyName()}"/>" required>
                </div>
            </div>
            <fieldset class="form-group">
                <div class="row">
                    <legend class="col-form-label col-sm-2 pt-0">Gender</legend>
                    <div class="col-sm-10">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="Gender" id="gridRadios1"
                                   value="Male" checked>
                            <label class="form-check-label" for="gridRadios1">
                                Male
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="Gender" id="gridRadios2"
                                   value="Female">
                            <label class="form-check-label" for="gridRadios2">
                                Female
                            </label>
                        </div>
                        <div class="form-check ">
                            <input class="form-check-input" type="radio" name="Gender" id="gridRadios3"
                                   value="Other" >
                            <label class="form-check-label" for="gridRadios3">
                                Other
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="form-group row mx-sm-3 ">
                <label for="exampleFormControlSelect1" class="col-sm-2 col-form-label">Grade</label>
                <div class="col-sm-3">
                    <select class="form-control" name="Grade" id="exampleFormControlSelect1" required>
                        <option><c:out value="${CurrentUser.getGrade()}"/></option>
                        <option value="Student">Student</option>
                        <option value="Worker">Worker</option>
                        <option value="Jobless">Jobless</option>
                    </select>
                </div>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="Address" class="col-sm-2 col-form-label">Address</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="Address" id="Address" placeholder="Address"
                           value="<c:out value="${CurrentUser.getAddress()}"/>" required>
                </div>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="Telephone" class="col-sm-2 col-form-label">Telephone</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="Telephone" id="Telephone" placeholder="Enter a tunisian phone number(+212)"
                           value="<c:out value="${CurrentUser.getTelephone()}"/>" required>
                </div>
                <c:if test="${!empty TelephoneError || !empty TelephoneFormatError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${TelephoneError}"/>
                        <c:out value="${TelephoneFormatError}"/>
                    </p>
                </c:if>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="Email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-3">
                    <input type="email" class="form-control" name="Email" id="Email" placeholder="Email"
                           value="<c:out value="${CurrentUser.getEmail()}"/>" required>
                </div>
                <c:if test="${!empty EmailFormatError || !empty EmailError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${EmailFormatError}"/>
                        <c:out value="${EmailError}"/>
                    </p>
                </c:if>
            </div>
            <c:if test="${!empty DaoException }">
                <p style="font-size: 12px ; margin: 15px ; color: #9b59b6">
                    <c:out value="${DaoException}"/>
                </p>
            </c:if>
            <button type="submit" class="btn btn-primary ms-3">Validate</button>
        </form>
    </div>
</div>
</body>
</html>
