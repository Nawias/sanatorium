<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - ustawienia konta</title>

    <link rel="stylesheet" href="/main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>
    <style>

    </style>
</head>
<body>
<jsp:include page="../layout/dropdown.jsp"></jsp:include>

<div class="pt-3 m-5">

    <div class="row ">
        <div class="col text-danger align-content-center text-center h3" role="alert">
            ${error}
            <%= (request.getParameter("error") == null) ? "" : request.getParameter("error") %>
        </div>

    </div>
    <div class="row p-3">
        <div class="col text-info">
            <h2>

                <%= (request.getParameter("message") == null) ? "" : request.getParameter("message") %>


            </h2>
            <h2>
                ${message}
            </h2>

        </div>
    </div>


    <div class="row">
        <div class="col-md-3  m-3 border-left border-info">
            <i class="fas fa-user-circle h1 fa-10x m-3"></i>
            <h3 class="my-2">${user.getName()} ${user.getSurname()}  </h3>
            <ul class="list-group list-group-flush">
                <li class="list-group-item list-group-item-action"><a href="/user/edit/password/${user.getId()}"
                                                                      class="link100">Zmień hasło</a></li>
            <%--    <li class="list-group-item list-group-item-action">Dapibus ac facilisis in</li>
                <li class="list-group-item list-group-item-action">Morbi leo risus</li>
                <li class="list-group-item list-group-item-action">Porta ac consectetur ac</li>
                <li class="list-group-item list-group-item-action">Vestibulum at eros</li>--%>
            </ul>


        </div>

        <div class="col-md-8 <%--border-left border-info--%> ml-5">

        </div>
    </div>

</div>
</body>
</html>