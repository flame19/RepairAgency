<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.ResourcesWithAsideMenu" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RepairAgency</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">
</head>
<body>

<header>
    <jsp:include page="common_blocks/header.jsp"/>
</header>

<div class="container-fluid" id="pageBody">
    <c:choose>
        <c:when test="${ResourcesWithAsideMenu.RESOURCES.contains(requestScope['javax.servlet.forward.servlet_path'])
                        and main_block ne '404.jsp' and main_block ne '500.jsp'}">
            <div class="row">
                <aside class="col-sm-3">
                    <jsp:include page="aside_blocks/aside_menu.jsp"/>
                </aside>
                <main class="col-sm-9">
                    <jsp:include page="main_blocks/${main_block}"/>
                </main>
            </div>
        </c:when>
        <c:otherwise>
            <main class="col-sm-6 offset-sm-3">
                <jsp:include page="main_blocks/${main_block}"/>
            </main>
        </c:otherwise>
    </c:choose>
</div>

<footer>
    <jsp:include page="common_blocks/footer.jsp"/>
</footer>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
</body>
</html>