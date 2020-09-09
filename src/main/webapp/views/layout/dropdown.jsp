    <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ page contentType="text/html;charset=UTF-8" %>
        <nav class=" navbar-dark bg-dark pt-2 pb-2 navbar navbar-expand-lg ">
        <div class="col-md-1 text-light "><a href="/" class="h3 text-white">Sanatorium</a></div>
        <div class="col-10"></div>

        <div class="dropdown float-right">
        <button type="button" class="btn btn-outline-light dropdown-toggle" data-toggle="dropdown">
        ${sessionScope.user}
        </button>
        <div class="dropdown-menu">


        <%--<a class="dropdown-item" href="#">Link 1</a>--%>
        <a class="dropdown-item" href="/user/configuration/${sessionScope.user}">Ustawienia konta</a>
        <a class="dropdown-item" href="/logout">Wyloguj</a>
        </div>
        </div>

        </nav>