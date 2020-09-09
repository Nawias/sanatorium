<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - Login</title>

    <link rel="stylesheet" href="/main.css">
    <link rel="stylesheet" href="bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/975f2122b4.js"<%-- crossorigin="anonymous"--%>></script>
    <style>
        /*        body {
                    background-image: url("graphics/background.jpg");
                    background-repeat: no-repeat;
                    background-size: cover;

                }*/

    </style>
</head>
<body>
<div class="container">



        <div class="row ">
            <div class="col text-danger align-content-center text-center h3" role="alert">
                    ${error}
            </div>

        </div>


   <div class="row align-items-center align-self-center justify-content-center pt-5">
        <div class="col-md-auto card">
            <%--  <img class="card-img-top" src="..." alt="Card image cap">--%>
            <div class="card-img-top align-items-center align-self-center justify-content-center">
                <i class='fas fa-user-circle' style='font-size:100px;color: #ffc107'></i>
            </div>
            <form action="/userLogin" method="post">
                <div class="form-group">
                    <label>
                        Email
                        <input type="text" name="email" class="form-control" autofocus required>
                    </label>
                </div>
                <div class="form-group">
                    <label>
                        Hasło
                        <input type="password" name="passoword" class="form-control">
                    </label>
                </div>

                <div class="form-group">
                    <input type="Submit" value="Zaloguj" class="btn btn-success">
                    <input type="reset" value="Resetuj" class="btn btn-light">
                    <a href="/" class="btn btn-light"> Powrót</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>