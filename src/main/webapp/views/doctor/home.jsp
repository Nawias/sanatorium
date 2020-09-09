<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - Panel</title>


    <link rel="stylesheet" href="/main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>

</head>
<body>

<jsp:include page="../layout/dropdown.jsp"></jsp:include>
<div class="container">
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

</div>

<div class="p-5">
    <div class="row p-1 align-items-center">
        <div class="col-md-4" >
            <div class="h3 my-2">
                Najbliższe wizyty
                <a href="/addVisit" class="btn-info btn float-right"> + Dodaj wizytę</a>
            </div>
            <div style=" height: 400px; overflow-y: scroll;">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <%--  <th>L.p.</th>--%>

                        <th>Pacjent</th>
                        <th>Data i godzina</th>

                        <th>Akcje</th>
                    </tr>
                    </thead>
                    <%! int i =1; %>
                    <c:forEach items="${visits}" var="visit">
                        <tr>
                                <%--         <td> <% out.println(i); i=i+1; %></td>--%>

                            <td><c:out value="${visit.getPatient().getName()}"/>&nbsp;<c:out value="${visit.getPatient().getSurname()}"/></td>

                            <td><c:out value="${visit.getDateTimeInUserFriendlyFormat()}"/></td>



                            <td>

                                <a href="/startVisit/<c:out value="${visit.id}"/>" class="btn btn-light" data-toggle="tooltip"
                                   title="Rozpocznij wizytę">
                                    <i class="fas fa-band-aid"></i>
                                </a>

                                <a href="/editVisit/<c:out value="${visit.id}"/>" class="btn btn-info" data-toggle="tooltip"
                                   title="Edytuj wizytę">
                                    <i class="fa fa-wrench" aria-hidden="true"></i>
                                </a>

                                <a href="/deleteVisit/<c:out value="${visit.id}"/>" class="btn btn-danger" data-toggle="tooltip"
                                   title="Usuń wizytę">
                                    <i class="fa fa-trash" aria-hidden="true"></i>
                                </a>

                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </div>
<%--

            <div class="my-3">
                <jsp:include page="../layout/cards/visits.jsp"></jsp:include>
            </div>
--%>


        </div>


    </div>


</div>
</body>
</html>