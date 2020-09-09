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
    <style>

    </style>
</head>
<body>
<jsp:include page="../layout/dropdown.jsp"></jsp:include>


<div class="container border border-info pt-3 mt-5">


    <div class="row p-1 align-items-center">
        <div class="col-md-4   align-items-center ">

            <form action="/updateUser/${user.getId()}" method="post">


                <div class="form-group">
                    <label> Imię
                    </label>
                    <input type="text" name="name" class="form-control" value =${user.getName()}>
                </div>

                <div class="form-group">
                    <label> Nazwisko
                    </label>
                    <input type="text" name="surname" class="form-control" value  =${user.getSurname()}>
                </div>


                <div class="form-group">
                    <label> Rola
                    </label>
                    <select name="role" class="form-control" >
                        <c:forEach items="${permissions}" var="permission">
                        <option value="<c:out value="${permission.id}"/>"  <c:if test="${permission.id == user.permission.getId()}">selected</c:if> >
                                <c:out value="${permission.name}"/>
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