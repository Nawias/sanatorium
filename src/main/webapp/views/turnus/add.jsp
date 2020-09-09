<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - nowy pobyt</title>

    <link rel="stylesheet" href="main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>

    <script src="scripts/main.js"></script>

    <script>
        $(document).ready(function () {
            $('div.new-patient').on("click", function (e) {

                if ($("#check_patient").is(":checked")) {
                    $('.new-patient-form').removeClass("d-none");
                    $('.exist-patient-select').addClass("d-none");

                } else {
                    $('.new-patient-form').addClass("d-none");
                    $('.exist-patient-select').removeClass("d-none");

                }

            });
        });
    </script>

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

            <h3>Nowa pobyt</h3>
            <form action="/saveTurnus" method="post">


                <div class="form-group new-patient">
                    <label><input type="checkbox" name="newPatient" class="form-control-md" id="check_patient"> Nowy
                        pacjent
                    </label>
                </div>


                <div class="d-none new-patient-form">

                    <div class="form-group">
                        <label>Imię</label>
                        <input type="text" name="name" class="form-control" placeholder="Imię">
                    </div>

                    <div class="form-group">
                        <label>Nazwisko</label>
                        <input type="text" name="surname" class="form-control" placeholder="Nazwisko">
                    </div>
                </div>


                <div class="form-group exist-patient-select">
                    <label> Pacjent
                    </label>
                    <select name="patient" class="form-control select2">
                        <option disabled selected value class="hide"></option>

                        <c:forEach items="${patients}" var="patient">
                            <option value="<c:out value="${patient.getId()}"/>">
                                <c:out value="${patient.getName()}"/>
                                <c:out value="${patient.getSurname()}"/>
                            </option>

                        </c:forEach>
                    </select>

                </div>

                <div class="form-group">
                    <label> Data rozpoczęcia pobytu
                    </label>
                    <input type="date" name="dateStart" class="form-control">

                    <label> Data końca pobytu
                    </label>
                    <input type="date" name="dateEnd" class="form-control">


                </div>

                <div class="form-group">
                    <label> Pokój
                    </label>
                    <select name="room" class="form-control select2">
                        <option disabled selected value class="hide"></option>

                        <c:forEach items="${rooms}" var="room">
                            <option value="<c:out value="${room.getId()}"/>">
                                Piętro <c:out value="${room.getFloor()}"/> nr. <c:out
                                    value="${room.getNumber()}"/>
                            </option>

                        </c:forEach>
                    </select>

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