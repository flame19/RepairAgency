<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:choose>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMER_HOME}">
        <h4 class="up_h"><fmt:message key="cra.customer_page.header.current_ord"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMER_ORDER_HISTORY}">
        <h4 class="up_h"><fmt:message key="cra.customer_page.header.prev_ord"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_HOME}">
        <h4 class="up_h"><fmt:message key="cra.master_page.header.act_ord"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_COMPLETED_ORDERS}">
        <h4 class="up_h"><fmt:message key="cra.master_page.header.comp_ord"/></h4>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${not empty orders}">
        <c:forEach var="order" items="${orders}" varStatus="status">
            <cust:order loop_num="${status.count}" order_object="${order}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:otherwise>
        <div class="col-md-12 order rounded page-content">
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMER_HOME}">
                    <h6><fmt:message key="cra.customer_page.ord.abs_act_ord"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMER_ORDER_HISTORY}">
                    <h6><fmt:message key="cra.customer_page.ord.abs_prev_ord"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_HOME}">
                    <h6><fmt:message key="cra.master_page.ord.abs_act_ord"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_COMPLETED_ORDERS}">
                    <h6><fmt:message key="cra.master_page.ord.abs_prev_ord"/></h6>
                </c:when>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>

