<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Sanatorium - form template</title>

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
<jsp:include page="layout/dropdown.jsp"></jsp:include>

<div class="container border border-info pt-3 mt-5">


    <div class="row p-1 align-items-center">
        <div class="col-md-4   align-items-center ">

            <form action="" method="post">

                <div class="form-group">
                    <label > Label
                    </label>
                    <input type="text" class="form-control">
                </div>


                <div class="form-group ">
                    <input type="Submit" value="Zapisz" class="btn btn-success">
                    <input type="reset" value="Resetuj" class="btn btn-danger">
                    <a href="#" class="btn btn-secondary"> Powrót</a>
                </div>
            </form>
        </div>

    </div>

</div>
</body>
</html>