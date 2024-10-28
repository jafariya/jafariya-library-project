<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library Books</title>
    <link rel="stylesheet" href="/books.css" type="text/css">
</head>
<body>
<div class="container">
    <h2>BOOKS</h2>
    <table class="responsive-table">
        <thead>
        <tr>
            <th class="col-id">ID</th>
            <th class="col-name">NAME</th>
            <th class="col-genre">GENRE</th>
        </tr>
        </thead>
        <tbody>
        <#list books as book>
        <tr>
            <td class="col-id">${book.id}</td>
            <td class="col-name">${book.name}</td>
            <td class="col-genre">${book.genre}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>
