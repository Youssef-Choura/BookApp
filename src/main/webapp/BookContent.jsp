<html>
<head>
    <link rel="stylesheet" href="./UserPagesCss/ViewBook.css">
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
    <div class="card" style="width: 20rem;margin-left : 470px;margin-top: 80px ">
        <img class="card-img-top" src="./BookPics/${requestScope.CurrentBook.getTitle()}.jpg" alt="BookImage">
        <div class="card-body">
            <h5 class="card-title"><c:out value="${requestScope.CurrentBook.getTitle()}"/></h5>
            <p class="card-text"><span>Abstract : </span>
            <p><c:out value="${requestScope.CurrentBook.getAbstract_()}"/></p>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item"><span>ISBN : </span><c:out value="${requestScope.CurrentBook.getIsbn()}"/></li>
            <li class="list-group-item"><span>Author(s) : </span><c:out value="${requestScope.CurrentBook.getAuthors()}"/></li>
            <li class="list-group-item"><span>Publish Year : </span><c:out value="${requestScope.CurrentBook.getYear()}"/></li>
            <li class="list-group-item"><span>Language : </span><c:out value="${requestScope.CurrentBook.getLanguage()}"/></li>
        </ul>
    </div>
</div>
</body>
</html>