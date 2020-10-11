<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.Role"%>
<%@ page import="ua.repair_agency.constants.CRAPaths"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<nav class="navbar navbar-expand-sm navbar-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}${CRAPaths.HOME}">
        <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="logo">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <span class="navbar-text"><fmt:message key="cra.header.site_title"/></span>

        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <div class="dropdown dropleft">
                    <button type="button" class="btn dropdown-toggle" data-toggle="dropdown">
                        <i class="fas fa-globe-americas"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}${CRAPaths.LANGUAGE}?lang=uk&prevURL=${requestScope['javax.servlet.forward.request_uri']}${param.page ne null ? '&page='.concat(param.page) : ''}">
                            <img src="${pageContext.request.contextPath}/static/img/ukr.png" alt="Ukraine flag">
                            <fmt:message key="cra.header.lang_uk"/>
                        </a>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}${CRAPaths.LANGUAGE}?lang=en&prevURL=${requestScope['javax.servlet.forward.request_uri']}${param.page ne null ? '&page='.concat(param.page) : ''}">
                            <img src="${pageContext.request.contextPath}/static/img/usa.png" alt="USA flag">
                            <fmt:message key="cra.header.lang_en"/>
                        </a>
                    </div>
                </div>
            </li>
            <c:choose>
                <c:when test="${user.role ne Role.UNKNOWN}">
                    <cust:info_logout/>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] ne CRAPaths.LOGIN and
                               requestScope['javax.servlet.forward.servlet_path'] ne CRAPaths.REGISTRATION}">
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#registerForm">
                            <i class="fas fa-user"></i> <fmt:message key="cra.header.registration"/>
                        </button>
                        <div class="modal" id="registerForm">
                            <cust:registration_form/>
                        </div>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#loginForm"
                                aria-pressed="true">
                            <i class="fas fa-sign-in-alt"></i> <fmt:message key="cra.header.login"/>
                        </button>
                        <div class="modal" id="loginForm">
                            <cust:login_form/>
                        </div>
                    </li>
                </c:when>
            </c:choose>
        </ul>
    </div>
</nav>
