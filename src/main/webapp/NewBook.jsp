<html lang="en">
<head>
    <link rel="stylesheet" href="./AdminPagesCss/AllBooks.css">
    <link rel="stylesheet" href="./AdminPagesCss/NewBook.css">
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
    <div class="sub-container">
        <div class="title">Book form</div>
        <form method="post" action="NewBook">
            <div class="user-details">
                <div class="input-box">
                    <span class="details">Title</span>
                    <label for="title">
                        <input type="text" name="Title" id="title" placeholder="Enter title" value="<c:out value="${requestScope.CurrentBook.getTitle()}"/>"
                               required>
                    </label>
                </div>
                <div class="input-box">
                    <span class="details">ISBN</span>
                    <label for="isbn">
                        <input type="text" name="ISBN" id="isbn" placeholder="Enter isbn " value="<c:out value="${requestScope.CurrentBook.getIsbn()}"/>"
                               required>
                    </label>
                </div>
                <c:if test="${!empty requestScope.ISBNError || !empty requestScope.ISBNFormatError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.ISBNError}"/>
                        <c:out value="${requestScope.ISBNFormatError}"/>
                    </p>
                </c:if>
                <div class="input-box">
                    <span class="details">Author(s)</span>
                    <label for="Author">
                        <input type="text" name="Authors" id="Author" placeholder="Enter Author" value="<c:out value="${requestScope.CurrentBook.getAuthors()}"/>"
                               required>
                    </label>
                </div>
            </div>
            <div class="grade-details">
                <label for="language">Language</label>
                <select name="Language" id="language" required>
                    <option value="${requestScope.CurrentBook.getLanguage()}"><c:out value="${requestScope.CurrentBook.getLanguage()}"/></option>--%>
                    <option value="English">English</option>
                    <option value="Arabic">Arabic</option>
                    <option value="French">French</option>
                </select>
            </div>
            <div class="Login-Information">
                <div class="input-box">
                    <span class="details">Publish Year</span>
                    <label for="year">
                        <input type="text" name="PublishYear" id="year" placeholder="Enter publish year" value="<c:out value="${requestScope.CurrentBook.getYear()}"/>"
                               required>
                    </label>
                </div>
                <c:if test="${!empty requestScope.PublishYearError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.PublishYearError}"/>
                    </p>
                </c:if>
                <div class="input-box">
                    <span class="details">Abstract</span>
                    <label for="Abstract">
                    <textarea class="form-control" name="Abstract" id="Abstract" rows="4" placeholder="Abstract"
                              required><c:out value="${requestScope.CurrentBook.getAbstract_()}"/></textarea>
                    </label>
                </div>
                <c:if test="${!empty requestScope.AbstractError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.AbstractError}"/>
                    </p>
                </c:if>
                <div class="button">
                    <input type="submit" value="Validate">
                </div>
                <c:if test="${!empty requestScope.DaoError }">
                    <p style="font-size: 12px ; margin-bottom: 15px ; color: #9b59b6">
                        <c:out value="${requestScope.DaoError}"/>
                    </p>
                </c:if>
            </div>
        </form>
    </div>
</div>
</body>
</html>








