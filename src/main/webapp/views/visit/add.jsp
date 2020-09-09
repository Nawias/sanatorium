<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - nowy użytkownik</title>

    <link rel="stylesheet" href="main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>

    <script src="scripts/main.js"></script>

</head>
<body>
<jsp:include page="../layout/dropdown.jsp"></jsp:include>

<div class="container border border-info pt-3 mt-5">
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

    <div class="row p-1 align-items-center">

        <div class="col-md-4   align-items-center ">

            <h3>Nowa wizyta</h3>
            <form action="/saveVisit" method="post">

                <div class="form-group">
                    <label> Lekarz
                    </label>
                    <select name="doctor" class="form-control select2">
                        <option disabled selected value class="hide"> </option>
                                      <c:forEach items="${doctors}" var="doctor">
                                          <option value="<c:out value="${doctor.getId()}"/>">
                                              <c:out value="${doctor.getName()}"/>
                                              <c:out value="${doctor.getSurname()}"/>
                                          </option>

                                      </c:forEach>
                    </select>

                </div>

                <div class="form-group">
                    <label> Pacjent
                    </label>
                    <select name="patient" class="form-control select2">
                        <option disabled selected value class="hide"> </option>

                                      <c:forEach items="${patients}" var="patient">
                                          <option value="<c:out value="${patient.getId()}"/>">
                                              <c:out value="${patient.getName()}"/>
                                              <c:out value="${patient.getSurname()}"/>
                                          </option>

                                      </c:forEach>
                    </select>

                </div>

                <div class="form-group">
                    <label> Data i godzina wizyty
                    </label>
                    <input type="datetime-local" name="date" class="form-control" step="900">


                </div>


                <div class="form-group ">
                    <input type="Submit" value="Zapisz" class="btn btn-success">
                    <input type="reset" value="Resetuj" class="btn btn-light">
                    <a href="#" class="btn btn-light"> Powrót</a>
                </div>
            </form>
        </div>

    </div>

</div>
</body>
</html>