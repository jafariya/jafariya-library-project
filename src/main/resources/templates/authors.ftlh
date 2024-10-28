<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authors</title>
    <link rel="stylesheet" href="/authors.css" type="text/css">
</head>
<body>
<div class="container">
    <h2>AUTHORS</h2>
    <table class="responsive-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Books</th>
        </tr>
        </thead>
        <tbody>
        <#list authors as author>
        <tr>
            <td>${author.id}</td>
            <td>${author.name}</td>
            <td>${author.surname}</td>
            <td>
                <#if author.books?has_content>
                <ul>
                    <#list author.books as book>
                    <li>${book.name}</li>
                </#list>
            </ul>
            <#else>
            No Books
        </#if>
        </td>
    </tr>
</#list>
</tbody>
</table>
        </div>
        </body>
        </html>
