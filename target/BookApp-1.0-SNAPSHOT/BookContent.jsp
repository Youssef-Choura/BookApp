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
            <a class="nav-link active" href="AllBooks">Books</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Users">Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="NewBook">New Book</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Logout">logout</a>
        </li>
    </ul>
    <table class="table table-hover">
        <thead>
        </thead>
        <tbody>
            <tr>
                <th scope="row"> Title </th>
                <td><c:out value="${CurrentBook.getTitle()}"/></td>
            </tr>
            <tr>
                <th scope="row"> ISBN </th>
                <td><c:out value="${CurrentBook.getIsbn()}"/> </td>
            </tr>
            <tr>
                <th scope="row"> Author(s) </th>
                <td><c:out value="${CurrentBook.getAuthors()}"/></td>
            </tr>
            <tr>
                <th scope="row"> Language </th>
                <td><c:out value="${CurrentBook.getLanguage()}"/></td>
            </tr>
            <tr>
                <th scope="row"> Publish Year </th>
                <td><c:out value="${CurrentBook.getYear()}"/></td>
            </tr>
            <tr>
                <th scope="row"> Abstract </th>
                <td><c:out value="${CurrentBook.getAbstract_()}"/></td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>