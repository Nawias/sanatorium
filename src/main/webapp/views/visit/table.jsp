<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <table class="table table-hover table-bordered">
        <thead>
        <tr>
          <%--  <th>L.p.</th>--%>
            <th>Lekarz</th>
            <th>Pacjent</th>
            <th>Data i godzina</th>

            <th>Akcje</th>
        </tr>
        </thead>
        <%! int i =1; %>
        <c:forEach items="${visits}" var="visit">
            <tr>
       <%--         <td> <% out.println(i); i=i+1; %></td>--%>
                <td><c:out value="${visit.getDoctor().getName()}"/>&nbsp;<c:out value="${visit.getDoctor().getSurname()}"/></td>
                <td><c:out value="${visit.getPatient().getName()}"/>&nbsp;<c:out value="${visit.getPatient().getSurname()}"/></td>

                <td><c:out value="${visit.getDateTimeInUserFriendlyFormat()}"/></td>



                <td>
                    <a href="/editVisit/<c:out value="${visit.id}"/>" class="btn btn-info" data-toggle="tooltip"
                       title="Edytuj użytkownika">
                        <i class="fa fa-wrench" aria-hidden="true"></i>
                    </a>

                    <a href="/deleteVisit/<c:out value="${visit.id}"/>" class="btn btn-danger" data-toggle="tooltip"
                       title="Usuń użytkownika">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>

                </td>
            </tr>
        </c:forEach>
    </table>

