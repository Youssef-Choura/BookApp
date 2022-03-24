<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css">
    <title>AdminPage</title>
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

    <div class="row">
        <c:forEach items="${books}" var="book">
        <div class="col-md-4">
            <div class="product-top text-center">
                <img src="./BookPics/${book.getTitle()}.jpg" alt="BookImage">
                <div class="overlay">
                    <form method="get" action="BookContent">
                    <button type="submit" name="ViewButton" value="${book.getIsbn()}" class="btn btn-secondary" title="View">
                        <i class="fa-regular fa-eye"></i>
                    </button>
                    </form>
                    <form method="get" action="EditBook">
                    <button type="submit" name="EditButton" value="${book.getIsbn()}" class="btn btn-secondary" title="Edit">
                        <i class="fa-regular fa-pen-to-square"></i>
                    </button>
                    </form>
                    <form method="post" action="DeleteBook">
                    <button type="submit" name="DeleteButton" value="${book.getIsbn()}" class="btn btn-secondary" title="Delete">
                        <i class="fa-regular fa-trash-can"></i>
                    </button>
                    </form>
                </div>
            </div>
            <div class="product-bottom text-center">
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star"></i>
                <i class="fa-solid fa-star-half-stroke"></i>
                <i class="fa-regular fa-star"></i>
                <h3><c:out value="${book.getTitle()}"/></h3>
                <h5><c:out value="${book.getAuthors()}"/></h5>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>

