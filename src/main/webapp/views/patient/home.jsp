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
        <div class="col-md-3">
            <div class="h3 my-2">
                Zaplanowane wizyty
            </div>
            <div style=" height: 400px; overflow-y: scroll;">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>

                        <th>Lekarz</th>
                        <th>Data i godzina</th>

                    </tr>
                    </thead>

                    <c:forEach items="${visits}" var="visit">
                        <tr>
                            <td><c:out value="${visit.getDoctor().getName()}"/>&nbsp;<c:out
                                    value="${visit.getDoctor().getSurname()}"/></td>

                            <td><c:out value="${visit.getDateTimeInUserFriendlyFormat()}"/></td>

                        </tr>
                    </c:forEach>
                </table>

            </div>

        </div>

        <div class="col-md-5">
            <div class="h2 mb-1">
                ${visit.getPatient().getName()}&nbsp;${visit.getPatient().getSurname()}
            </div>
            <div class="h3 mb-2">
                Historia wizyt pacjenta
            </div>
            <div style=" height: 400px; overflow-y: scroll;">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>

                        <th>Data</th>
                        <th>Lekarz</th>
                        <th>Opis</th>

                    </tr>
                    </thead>
                    <%! int i = 1; %>
                    <c:forEach items="${cards}" var="card">
                        <tr>
                            <td><c:out value="${card.getVisit().getDateInUserFriendlyFormat()}"/>&nbsp;</td>
                            <td><c:out value="${card.getVisit().getDoctor().getName()}"/>&nbsp;<c:out value="${card.getVisit().getDoctor().getSurname()}"/></td>
                            <td>
                                <c:out value="${card.getDescription()}"/> <br/>

                                <c:if test="${card.getPrescription() != null }">
                                    Przepisane leki: <c:forEach items="${card.getPrescription().getMedicament()}"
                                                                var="medic">
                                    <c:out value="${medic.getName()}"/> <c:out value="${medic.getDose()}"/> mg,
                                </c:forEach>
                                </c:if> <br/>

                                <c:if test="${card.getReferral() != null}">
                                    Wystawione skierowanie: <c:out
                                        value="${card.getReferral().getService()}"/>, Rozpoznanie: <c:out
                                        value="${card.getReferral().getDecription()}"/>

                                </c:if>


                            </td>


                        </tr>
                    </c:forEach>
                </table>

            </div>


        </div>


    </div>


</div>
</body>
</html>