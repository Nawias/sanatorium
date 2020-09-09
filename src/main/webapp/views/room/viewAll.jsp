<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - pokoje</title>

    <link rel="stylesheet" href="/main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>

    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>
    <style>

    </style>
    <script src="scripts/main.js"></script>

</head>
<body>
<jsp:include page="../layout/dropdown.jsp"></jsp:include>


<div class="container border border-info pt-3 mt-5 table-responsive">


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

    <div class="p-3 ">
        <span class="h2">Pokoje</span>

        <a href="/addRoom" class="btn-info btn float-right"> + Dodaj pokój</a>
    </div>

    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Piętro</th>
            <th>Numer pokoju</th>
            <th>Stan</th>

            <th>Akcje</th>
        </tr>
        </thead>

        <c:forEach items="${rooms}" var="room">
            <tr>
                <td><c:out value="${room.getId()}"/></td>
                <td><c:out value="${room.getFloor()}"/>&nbsp;</td>

                <td><c:out value="${room.getNumber()}"/></td>

                <td> ${room.getState()}</td>


                <td>
                    <a href="/editRoom/<c:out value="${room.getId()}"/>" class="btn btn-info" data-toggle="tooltip"
                       title="Zmień stan pokoju">
                        <i class="fa fa-wrench" aria-hidden="true"></i>
                    </a>

                    <a href="/deleteRoom/<c:out value="${room.getId()}"/>" class="btn btn-danger" data-toggle="tooltip"
                       title="Usuń pokój">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>

                </td>
            </tr>
        </c:forEach>
    </table>


</div>
</body>
</html>