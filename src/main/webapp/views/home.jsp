<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - Panel</title>

    <%-- <link rel="stylesheet" href="w3.css">
 --%><%--
    <link rel="stylesheet" href="bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>--%>

    <link rel="stylesheet" href="/main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>

</head>
<body>

<jsp:include page="layout/dropdown.jsp"></jsp:include>
<div class="container">
    <div class="row p-3">
        <div class="col text-success">
            <h1>
                ${message}
            </h1>

        </div>
    </div>


    <div class="row p-1 align-items-center">
        <div class="col-md-6">
            <jsp:include page="layout/cards/patients.jsp"></jsp:include>
        </div>

        <div class="col-md-6">
            <jsp:include page="layout/cards/doctors.jsp"></jsp:include>
        </div>
    </div>


    <div class="row p-1 align-items-center">
        <div class="col-md-6">
            <jsp:include page="layout/cards/rooms.jsp"></jsp:include>
        </div>

        <div class="col-md-6">
            <jsp:include page="layout/cards/visits.jsp"></jsp:include>

        </div>
    </div>


    <div class="row p-1 align-items-center">
        <div class="col-md-6">
            <jsp:include page="layout/cards/turnuses.jsp"></jsp:include>

        </div>

        <div class="col-md-6">
            <jsp:include page="layout/cards/rehabilitations.jsp"></jsp:include>

        </div>

    </div>


</div>
</body>
</html>