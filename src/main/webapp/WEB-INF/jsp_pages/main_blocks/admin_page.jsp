<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>


<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ADMIN_HOME}">
    <c:choose>
        <c:when test="${not empty managers}">
            <h6><fmt:message key="cra.admin_page.header.man_reg"/></h6>
            <c:forEach var="manager" items="${managers}" varStatus="status">
                <cust:user_info loop_num="${status.count}" user_for_mapping="${manager}"/>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="col-md-12 rounded page-content">
                <h6><fmt:message key="cra.admin_page.header.no_reg_man"/></h6>
            </div>
        </c:otherwise>
    </c:choose>
    <br>
    <c:choose>
        <c:when test="${not empty masters}">
            <h6><fmt:message key="cra.admin_page.header.mas_reg"/></h6>
            <c:forEach var="master" items="${masters}" varStatus="status">
                <cust:user_info loop_num="${status.count}" user_for_mapping="${master}"/>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="col-md-12 rounded page-content">
                <h6><fmt:message key="cra.admin_page.header.no_reg_mas"/></h6>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MAN_MAS_REGISTRATION}">
    <cust:registration_form/>
</c:if>