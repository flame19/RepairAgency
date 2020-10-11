<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>


<c:choose>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME}">
        <h4 class="up_h"><fmt:message key="cra.manager_page.home.header"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS}">
        <h4 class="up_h"><fmt:message key="cra.manager_page.act.header"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ORDER_HISTORY}">
        <h4 class="up_h"><fmt:message key="cra.manager_page.hist.header"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMERS}">
        <h4 class="up_h"><fmt:message key="cra.manager_page.cra_cus.header"/></h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTERS}">
        <h4 class="up_h"><fmt:message key="cra.manager_page.cra_mas.header"/></h4>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${(requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME or
         requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS or
         requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ORDER_HISTORY) and
         not empty orders}">
        <c:forEach var="order" items="${orders}" varStatus="status">
            <cust:order loop_num="${status.count}" order_object="${order}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMERS and not empty customers}">
        <c:forEach var="customer" items="${customers}" varStatus="status">
            <cust:user_info loop_num="${status.count}" user_for_mapping="${customer}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTERS and not empty masters}">
        <c:forEach var="master" items="${masters}" varStatus="status">
            <cust:user_info loop_num="${status.count}" user_for_mapping="${master}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:otherwise>
        <div class="col-md-12 order rounded page-content">
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME}">
                    <h6><fmt:message key="cra.manager_page.home_abs.header"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS}">
                    <h6><fmt:message key="cra.manager_page.act_abs.header"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ORDER_HISTORY}">
                    <h6><fmt:message key="cra.manager_page.hist_abs.header"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.CUSTOMERS}">
                    <h6><fmt:message key="cra.manager_page.cra_cus_abs.header"/></h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTERS}">
                    <h6><fmt:message key="cra.manager_page.cra_mas_abs.header"/></h6>
                </c:when>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>