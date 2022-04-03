<html>
<head>
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
    <link rel="stylesheet" href="./AdminPagesCss/UsersList.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css">
    <title>Users</title>
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
        <h3>Users list</h3>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">N</th>
                <th scope="col">Login</th>
                <th scope="col">Password</th>
                <th scope="col">First name</th>
                <th scope="col">Family name</th>
                <th scope="col">Gender</th>
                <th scope="col">Grade</th>
                <th scope="col">Address</th>
                <th scope="col">Email</th>
                <th scope="col">Telephone</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="Users" scope="request" type="java.util.List"/>
            <c:forEach items="${Users}" var="user" varStatus="status">
                <tr>
                    <th scope="row">
                        <c:out value="${status.count}"/>
                    </th>
                    <td>
                        <c:out value="${user.getLogin()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getPassword()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getFirstName()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getFamilyName()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getGender()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getGrade()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getAddress()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getEmail()}"/>
                    </td>
                    <td>
                        <c:out value="${user.getTelephone()}"/>
                    </td>
                    <td>
                        <form action="EditUser" method="get">
                            <button type="submit" class="btn btn-outline-primary" name="EditButton"
                                    value="${user.getLogin()}">Edit
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action="DeleteUser" method="post">
                            <button type="submit" class="btn btn-outline-danger" name="DeleteButton"
                                    value="${user.getLogin()}">Delete
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
