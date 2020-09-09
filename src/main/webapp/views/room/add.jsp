<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - nowy pokój</title>

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
            <h3>Dodaj nowy pokój</h3>

            <form action="/saveRoom" method="post">

                <div class="form-group">
                    <label> Numer pokoju
                    </label>
                    <input type="number" min="0" name="number" class="form-control" step="1">
                </div>

                <div class="form-group">
                    <label> Wybierz piętro
                    </label>
                    <select name="floor" class="form-control select2">
                        <option disabled selected value class="hide"></option>

                        <c:forEach  begin="0" end="${floor}" var="i">
                            <option value="<c:out value="${i}"/>">
                                <c:out value="${i}"/>

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