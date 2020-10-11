<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.repair_agency.constants.OrderStatus" %>
<%@ tag import="ua.repair_agency.constants.CRAPaths" %>
<%@ attribute name="order_for_editing"
              type="java.lang.Object" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="frm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:if test="${order_for_editing ne null}">
    <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.EDIT_ORDER ? '' : ' modal-dialog-centered'}">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}${CRAPaths.EDIT_ORDER}" method="post">
                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="cra.ord_edit.edit_ord"/>${order_for_editing.id}</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('price')}">
                            <p class="formError">
                                <fmt:message key="cra.ord_edit.inconsistencies.spec_price"/>
                            </p>
                        </c:if>
                        <input type="text" class="form-control" required
                               placeholder="Specify the price" name="price"
                               value="${order_for_editing.price ne null ? order_for_editing.price : 0}">
                    </div>
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('master')}">
                            <p class="formError">
                                <fmt:message key="cra.ord_edit.inconsistencies.ch_mas"/>
                            </p>
                        </c:if>
                        <select name="masterID" class="custom-select mb-3">
                            <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.EDIT_ORDER}">
                                    <c:if test="${order_for_editing.masterID eq 0}">
                                        <option selected value="0"><fmt:message key="cra.ord_edit.mas"/></option>
                                        <c:forEach var="master" items="${masters}">
                                            <option value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${order_for_editing.masterID ne 0}">
                                        <option value="0"><fmt:message key="cra.ord_edit.mas"/></option>
                                        <c:forEach var="master" items="${masters}">
                                            <option ${order_for_editing.masterID eq master.id ? 'selected' : ''}
                                                    value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${order_for_editing.master.id eq 0}">
                                        <option selected value="0"><fmt:message key="cra.ord_edit.mas"/></option>
                                        <c:forEach var="master" items="${masters}">
                                            <option value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${order_for_editing.master.id ne 0}">
                                        <option value="0"><fmt:message key="cra.ord_edit.mas"/></option>
                                        <c:forEach var="master" items="${masters}">
                                            <option ${order_for_editing.master.id eq master.id ? 'selected' : ''}
                                                    value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div class="form-group reg_radio">
                        <c:if test="${order_for_editing.status eq OrderStatus.PENDING or
                                    order_for_editing.status eq OrderStatus.CAR_WAITING or
                                    curOrderStatus eq OrderStatus.PENDING or curOrderStatus eq OrderStatus.CAR_WAITING}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.CAR_WAITING}"
                                    ${order_for_editing.status eq OrderStatus.PENDING or
                                            order_for_editing.status eq OrderStatus.CAR_WAITING ? 'checked' : ''}>
                                <fmt:message key="cra.ord_edit.car_wt"/>
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status eq OrderStatus.REPAIR_WORK
                                    or curOrderStatus eq OrderStatus.REPAIR_WORK}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.REPAIR_WORK}"
                                    ${order_for_editing.status eq OrderStatus.REPAIR_WORK ? 'checked' : ''}>
                                <fmt:message key="cra.ord_edit.rep_work"/>
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status eq OrderStatus.REPAIR_COMPLETED
                                    or curOrderStatus eq OrderStatus.REPAIR_COMPLETED}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.REPAIR_COMPLETED}"
                                    ${order_for_editing.status eq OrderStatus.REPAIR_COMPLETED ? 'checked' : ''}>
                                <fmt:message key="cra.ord_edit.rep_comp"/>
                            </div>
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.ORDER_COMPLETED}"
                                    ${order_for_editing.status eq OrderStatus.ORDER_COMPLETED ? 'checked' : ''}>
                                <fmt:message key="cra.ord_edit.ord_comp"/>
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status ne OrderStatus.REPAIR_COMPLETED and
                                    order_for_editing.status ne OrderStatus.ORDER_COMPLETED}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status" value="${OrderStatus.REJECTED}"
                                    ${order_for_editing.status eq OrderStatus.REJECTED ? 'checked' : ''}>
                                <fmt:message key="cra.ord_edit.reject"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('managerComment')}">
                            <p class="formError">
                                <fmt:message key="cra.ord_edit.inconsistencies.com"/>
                            </p>
                        </c:if>
                        <textarea class="form-control" rows="4" required name="managerComment"
                                  placeholder="<frm:message key="cra.ord_edit.leave_comm"/>">${order_for_editing.managerComment}</textarea>
                    </div>
                    <input type="hidden" name="editing_order_id" value="${order_for_editing.id}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn"><fmt:message key="cra.ord_edit.edit"/></button>
                    <c:choose>
                        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.EDIT_ORDER}">
                            <a href="${pageContext.request.contextPath}${CRAPaths.MANAGER_HOME}" class="btn">
                                <fmt:message key="cra.ord_edit.cancel"/></a>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn" data-dismiss="modal">
                                <fmt:message key="cra.ord_edit.cancel"/></button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </div>
    </div>
</c:if>