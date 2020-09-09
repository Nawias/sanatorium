<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - lekarze</title>

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
        <span class="h2">Lekarze</span>

        <a href="/addDoctor" class="btn-info btn float-right"> + Dodaj lekarza</a>
    </div>

    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th>L.p.</th>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Email</th>

            <th>Akcje</th>
        </tr>
        </thead>
        <%! int i = 1; %>
        <c:forEach items="${doctors}" var="doctor">
            <tr>
                <td><% out.println(i);
                    i = i + 1; %></td>
                <td><c:out value="${doctor.getName()}"/>&nbsp;</td>

                <td><c:out value="${doctor.getSurname()}"/></td>

                <td><c:out value="${doctor.getEmail()}"/></td>


                <td>
                    <a href="/editDoctor/<c:out value="${doctor.id}"/>" class="btn btn-info" data-toggle="tooltip"
                       title="Edytuj lekarza">
                        <i class="fa fa-wrench" aria-hidden="true"></i>
                    </a>

                    <a href="/deleteDoctor/<c:out value="${doctor.id}"/>" class="btn btn-danger" data-toggle="tooltip"
                       title="Usuń lekarza">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>

                </td>
            </tr>
        </c:forEach>
    </table>


</div>
</body>
</html>