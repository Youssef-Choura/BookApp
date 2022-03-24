<html>
<head>
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css">
    <title>Book Form</title>
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
            <a class="nav-link " href="AllBooks">Books</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Users">Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="NewBook">New Book</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Logout">logout</a>
        </li>
    </ul>

    <div class="NewBookForm">
        <form method="post" action="EditBook">
            <div class="form-group row mx-sm-3">
                <label for="inputTitle" class="col-sm-2 col-form-label">Title</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="inputTitle" name="Title" placeholder="Title"
                           value="<c:out value="${CurrentBook.getTitle()}"/>" required >
                </div>
            </div>
            <div class="form-group row mx-sm-3">
                <label for="inputISBN" class="col-sm-2 col-form-label">ISBN</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="inputISBN" name="ISBN" placeholder="ISBN"
                           value="<c:out value="${CurrentBook.getIsbn()}"/>" required>
                </div>
                <c:if test="${!empty ISBNError || !empty ISBNFormatError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${ISBNError}"/>
                        <c:out value="${ISBNFormatError}"/>
                    </p>
                </c:if>
            </div>
            <div class="form-group row mx-sm-3">
                <label for="inputAuthor" class="col-sm-2 col-form-label">Author(s)</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="inputAuthor" name="Authors" placeholder="Author(s)"
                           value="<c:out value="${CurrentBook.getAuthors()}"/>" required>
                </div>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="exampleFormControlSelect1" class="col-sm-2 col-form-label">Language</label>
                <div class="col-sm-3">
                    <select class="form-control" name="Language" id="exampleFormControlSelect1" required>
                        <option value="${CurrentBook.getLanguage()}"><c:out value="${CurrentBook.getLanguage()}"/>"</option>
                        <option value="English">English</option>
                        <option value="French">French</option>
                        <option value="Arabic">Arabic</option>
                        <option value="Deutsch">Deutsch</option>
                    </select>
                </div>
            </div>
            <div class="form-group row mx-sm-3 ">
                <label for="inputYear" class="col-sm-2 col-form-label">Publish Year</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="PublishYear" id="inputYear"  placeholder = "Publish Year"
                           value="<c:out value="${CurrentBook.getYear()}"/>" required>
                </div>
                <c:if test="${!empty PublishYearError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${PublishYearError}"/>
                    </p>
                </c:if>
            </div>
            <div class="form-group row mx-sm-3">
                <label for= "exampleFormControlTextarea1" class="col-sm-2 col-form-label">Abstract</label>
                <div class="col-sm-3" >
                    <textarea class="form-control" name="Abstract"  id="exampleFormControlTextarea1" rows="5"
                               required><c:out value="${CurrentBook.getAbstract_()}"/></textarea>
                </div>
            </div>
            <c:if test="${!empty DaoError }">
                <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                    <c:out value="${DaoError}"/>
                </p>
            </c:if>
            <button type="submit" class="btn btn-primary ms-3" >Validate</button>
        </form>
    </div>
</div>
</body>
</html>