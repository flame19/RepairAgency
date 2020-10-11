<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<li class="nav-item">
    <div class="logout">
        <span>${user.firstName} ${user.lastName}</span>
        <a href="${pageContext.request.contextPath}${CRAPaths.LOGOUT}">
            <i class="fas fa-sign-out-alt"></i><fmt:message key="cra.logout"/></a>
    </div>
</li>