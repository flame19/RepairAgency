<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="pagination_model" required="true"
              type="ua.repair_agency.models.pagination.PaginationModel" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:if test="${pagination_model ne null}">
    <div class="col-md-9 offset-md-3 pagination">
        <ul>
            <li class="pagination-previous${pagination_model.previous ? '' : ' disabled'}">
                <a <c:if test="${pagination_model.previous}">
                    href="${pagination_model.previousUri}"
                </c:if>>« <fmt:message key="cra.pagination.prev"/></a>
            </li>
            <c:forEach var="page" items="${pagination_model.pages}">
                <c:choose>
                    <c:when test="${page.current}">
                        <li class="current">${page.pageNum}</li>
                    </c:when>
                    <c:when test="${page.ellipsis}">
                        <li>...</li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${page.pageUri}">${page.pageNum}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <li class="pagination-next${pagination_model.next ? '' : ' disabled'}">
                <a <c:if test="${pagination_model.next}">
                    href="${pagination_model.nextUri}"
                </c:if>><fmt:message key="cra.pagination.next"/> »</a>
            </li>
        </ul>
    </div>
</c:if>



