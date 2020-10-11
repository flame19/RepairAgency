<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.repair_agency.constants.Role" %>
<%@ tag import="ua.repair_agency.constants.CRAPaths" %>
<%@ attribute name="user_for_editing"
              type="java.lang.Object" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:if test="${user_for_editing ne null}">
    <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.EDIT_USER ? '' : ' modal-dialog-centered'}">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}${CRAPaths.EDIT_USER}" method="post">
                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="cra.user_edit.header"/></h4>
                </div>
                <div class="modal-body">
                    <c:if test="${inconsistencies.contains('firstName')}">
                        <p class="formError">
                            <fmt:message key="cra.user_edit.inconsistencies.f_name"/>
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required
                               placeholder="<fmt:message key="cra.user_edit.ent_f_name"/>" name="fName"
                               value="${user_for_editing.firstName}">
                    </div>
                    <c:if test="${inconsistencies.contains('lastName')}">
                        <p class="formError">
                            <fmt:message key="cra.user_edit.inconsistencies.l_name"/>
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required
                               placeholder="<fmt:message key="cra.user_edit.ent_l_name"/>" name="lName"
                               value="${user_for_editing.lastName}">
                    </div>
                    <c:if test="${inconsistencies.contains('email')}">
                        <p class="formError">
                            <fmt:message key="cra.user_edit.inconsistencies.email"/>
                        </p>
                    </c:if>
                    <c:if test="${inconsistencies.contains('notFreeEmail')}">
                        <p class="formError">
                            <fmt:message key="cra.user_edit.inconsistencies.not_free_em"/>
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required placeholder="E-mail" name="email"
                               value="${user_for_editing.email}">
                    </div>
                    <div class="form-group reg_radio">
                        <div class="form-check-inline">
                            <input type="radio" class="form-check-input" name="role" value="${Role.MASTER}"
                                ${user_for_editing.role eq Role.MASTER ? 'checked' : ''}><fmt:message key="cra.user_edit.mas"/>
                        </div>
                        <div class="form-check-inline">
                            <input type="radio" class="form-check-input" name="role" value="${Role.MANAGER}"
                                ${user_for_editing.role eq Role.MANAGER ? 'checked' : ''}><fmt:message key="cra.user_edit.man"/>
                        </div>
                    </div>
                    <input type="hidden" name="editing_user_id" value="${user_for_editing.id}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn"><fmt:message key="cra.user_edit.edit"/></button>
                    <c:choose>
                        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.EDIT_USER}">
                            <a href="${pageContext.request.contextPath}${CRAPaths.ADMIN_HOME}" class="btn"><fmt:message key="cra.user_edit.cancel"/></a>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn" data-dismiss="modal"><fmt:message key="cra.user_edit.cancel"/></button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </div>
    </div>
</c:if>