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
    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>

    <script src="/scripts/main.js"></script>

    <script>
        $(document).ready(function () {
            $('div.medicbox').on("click", function (e) {
                if ($("#medicbox").is(":checked")) {
                    $('.medic').removeClass("d-none");
                } else {
                    $('.medic').addClass("d-none");
                }
            });


            $('div.refbox').on("click", function (e) {
                if ($("#refbox").is(":checked")) {
                    $('.referal').removeClass("d-none");
                } else {
                    $('.referal').addClass("d-none");
                }
            });
        });
    </script>


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
        <div class="col-md-4">
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
                        <th>Opis</th>

                    </tr>
                    </thead>
                    <%! int i = 1; %>
                    <c:forEach items="${cards}" var="card">
                        <tr>
                            <td><c:out value="${card.getVisit().getDateInUserFriendlyFormat()}"/>&nbsp;</td>

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

        <div class="col-md-8 mt-2">
            <form action="/endVisit/${visit.getId()}" method="post">
                <div class="row">


                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Opis
                                <textarea name="description" rows="10" cols="50" class="form-control"></textarea>
                            </label>
                        </div>

                        <div class="form-group medicbox">
                            <label>
                                <input type="checkbox" name="prescription" class="form-control-dm" id="medicbox"> Dodaj
                                receptę
                            </label> <br/>
                        </div>

                        <div class="form-group refbox">
                            <label>
                                <input type="checkbox" name="referal" class="form-control-dm" id="refbox"> Dodaj
                                skierowanie
                            </label>
                        </div>
                    </div>


                    <div class="col-md-4 p-4 d-none medic">
                        <div class="form-group ">
                            <h2>Wybierz leki</h2>
                            <select name="medicamment" class="form-control select2 " style="width: 100%;">
                                <option disabled selected value class="hide"></option>
                                <c:forEach items="${medicaments}" var="medicament">
                                    <option value="<c:out value="${medicament.getId()}"/>">
                                        <c:out value="${medicament.getName()}"/>,
                                        <c:out value="${medicament.getDose()}"/> mg
                                    </option>

                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-4 d-none referal">
                        <h2>Wystaw skierowanie</h2>

                        <div class="form-group">
                            <label>Nazwa badania
                            </label>
                            <input type="text" name="referal_service" class="form-control ">
                        </div>

                        <div class="form-group">
                            <label>Rozpoznanie
                                <textarea name="referal_description" rows="10" cols="50"
                                          class="form-control"></textarea>
                                <%--<input type="text" name="referal_description" class="form-control w-100">--%>
                            </label>
                        </div>


                    </div>
                </div>
                <div class="row">
                    <div class="form-group ">
                        <input type="Submit" value="Zapisz" class="btn btn-success">
                        <input type="reset" value="Resetuj" class="btn btn-light">
                        <a href="#" class="btn btn-light"> Powrót</a>
                    </div>
                </div>


            </form>
        </div>

    </div>


</div>
</body>
</html>