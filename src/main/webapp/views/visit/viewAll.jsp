<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - użytkownicy</title>

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


<div class="container border border-info pt-3 mt-5 table-responsive">


    <div class="row ">
        <div class="col text-danger align-content-center text-center h3" role="alert">
            ${error}
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
        <span class="h2">Wizyty</span>

        <a href="/addVisit" class="btn-info btn float-right"> + Dodaj wizytę</a>
    </div>




    <jsp:include page="table.jsp"></jsp:include>


</div>
</body>
</html>