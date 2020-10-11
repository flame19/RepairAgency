<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.repair_agency.constants.PaginationConstants" %>
<%@ tag import="ua.repair_agency.constants.Role" %>
<%@ tag import="ua.repair_agency.constants.OrderStatus" %>
<%@ tag import="ua.repair_agency.constants.CRAPaths" %>
<%@ tag import="ua.repair_agency.constants.RepairType" %>
<%@ attribute name="loop_num" required="true"
              type="java.lang.String" %>
<%@ attribute name="order_object" required="true"
              type="ua.repair_agency.models.order.Order" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust_form" %>


<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:if test="${order_object ne null}">
    <div class="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME or
                    requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS)) or
                        (user.role eq Role.MASTER and requestScope['javax.servlet.forward.servlet_path']
                        eq CRAPaths.MASTER_HOME) ? 'col-md-12' : 'col-md-10 offset-md-1'} order rounded page-content">
        <div class="row">
            <div class="col-md-1">
            <span>
                 <cust:entity-page-counter loop_count_num="${loop_num}"
                                           page_num="${param.page}"
                                           entities_page_amount="${PaginationConstants.ORDERS_FOR_PAGE.amount}"/>
            </span>

            </div>
            <div class="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME or
                    requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS)) or
                        (user.role eq Role.MASTER and requestScope['javax.servlet.forward.servlet_path']
                        eq CRAPaths.MASTER_HOME) ? 'col-md-9' : 'col-md-11'}">
                <div class="row">
                    <div class="col-md-3 o"><h6><fmt:message key="cra.order.ord_num"/></h6></div>
                    <div class="col-md-7"><p>#${order_object.id}</p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.cus_name"/></h6></div>
                    <div class="col-md-7"><p>${order_object.customer.firstName} ${order_object.customer.lastName}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.date_ord"/></h6></div>
                    <div class="col-md-7"><p>
                        <cust:date-formatter localDateTime="${order_object.date}" pattern="yyyy-MM-dd HH:mm"/></p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.car_info"/></h6></div>
                    <div class="col-md-7">
                        <p>${order_object.carBrand}, ${order_object.carModel}, ${order_object.carYearManufacture}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.rep_type"/></h6></div>
                    <div class="col-md-7"><p>
                        <c:choose>
                            <c:when test="${order_object.repairType eq RepairType.ENGINE_REPAIR}">
                                <fmt:message key="cra.order.eng_rep"/>
                            </c:when>
                            <c:when test="${order_object.repairType eq RepairType.TRANSMISSION_REPAIR}">
                                <fmt:message key="cra.order.tran_rep"/>
                            </c:when>
                            <c:when test="${order_object.repairType eq RepairType.CHASSIS_REPAIR}">
                                <fmt:message key="cra.order.chas_rep"/>
                            </c:when>
                            <c:when test="${order_object.repairType eq RepairType.BATTERY_REPLACEMENT}">
                                <fmt:message key="cra.order.bat_rep"/>
                            </c:when>
                            <c:when test="${order_object.repairType eq RepairType.OIL_CHANGE}">
                                <fmt:message key="cra.order.oil_chan"/>
                            </c:when>
                            <c:when test="${order_object.repairType eq RepairType.PAINTING_WORKS}">
                                <fmt:message key="cra.order.paint_works"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="cra.order.other"/>
                            </c:otherwise>
                        </c:choose>
                    </p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.rep_desc"/></h6></div>
                    <div class="col-md-7"><p>${order_object.repairDescription}</p></div>
                </div>
                <hr>
                <c:if test="${order_object.price ne 0}">
                    <div class="row">
                        <div class="col-md-3">
                            <h6><c:choose>
                                <c:when test="${order_object.status eq OrderStatus.REPAIR_COMPLETED or
                                                order_object.status eq OrderStatus.ORDER_COMPLETED}">
                                    <fmt:message key="cra.order.ord_price"/>
                                </c:when>
                                <c:otherwise><fmt:message key="cra.order.prev_price"/></c:otherwise>
                            </c:choose></h6></div>
                        <div class="col-md-7"><p>${order_object.price} $</p></div>
                    </div>
                    <hr>
                </c:if>
                <c:if test="${order_object.master.id ne 0}">
                    <div class="row">
                        <div class="col-md-3"><h6><fmt:message key="cra.order.mas_info"/></h6></div>
                        <div class="col-md-7"><p>${order_object.master.firstName} ${order_object.master.lastName}</p>
                        </div>
                    </div>
                    <hr>
                </c:if>
                <c:if test="${order_object.repairCompletionDate ne null}">
                    <div class="row">
                        <div class="col-md-3"><h6><fmt:message key="cra.order.rep_com_date"/></h6></div>
                        <div class="col-md-7"><p>
                            <cust:date-formatter localDateTime="${order_object.repairCompletionDate}" pattern="yyyy-MM-dd HH:mm"/></p></div>
                    </div>
                    <hr>
                </c:if>
                <div class="row">
                    <div class="col-md-3"><h6><fmt:message key="cra.order.ord_st"/></h6></div>
                    <div class="col-md-7"><p>
                        <c:choose>
                            <c:when test="${order_object.status eq OrderStatus.PENDING}">
                                <fmt:message key="cra.ord.pending"/>
                            </c:when>
                            <c:when test="${order_object.status eq OrderStatus.REJECTED}">
                                <fmt:message key="cra.ord.reject"/>
                            </c:when>
                            <c:when test="${order_object.status eq OrderStatus.CAR_WAITING}">
                                <fmt:message key="cra.ord.car_wt"/>
                            </c:when>
                            <c:when test="${order_object.status eq OrderStatus.REPAIR_WORK}">
                                <fmt:message key="cra.order.rep_work"/>
                            </c:when>
                            <c:when test="${order_object.status eq OrderStatus.REPAIR_COMPLETED}">
                                <fmt:message key="cra.ord.rep_comp"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="cra.ord.ord_comp"/>
                            </c:otherwise>
                        </c:choose>
                    </p></div>
                </div>
                <c:if test="${order_object.managerComment ne null}">
                    <hr>
                    <div class="row">
                        <div class="col-md-3"><h6><fmt:message key="cra.order.man_com"/></h6></div>
                        <div class="col-md-7"><p>${order_object.managerComment}</p></div>
                    </div>
                </c:if>
            </div>
            <c:if test="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MANAGER_HOME or
                            requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.ACTIVE_ORDERS))}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#editModal${order_object.id}">
                        <fmt:message key="cra.order.edit_ord"/>
                    </button>
                    <div class="modal fade" id="editModal${order_object.id}">
                        <cust_form:order_editing_form order_for_editing="${order_object}"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${user.role eq Role.MASTER and
            requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_HOME and
            order_object.status ne OrderStatus.REPAIR_WORK and order_object.status ne OrderStatus.REPAIR_COMPLETED}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#rep_workStatus${order_object.id}">
                        <fmt:message key="cra.order.rep_work"/>
                    </button>
                    <div class="modal fade" id="rep_workStatus${order_object.id}">
                        <div class="modal-dialog modal-dialog-centered modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <fmt:message key="cra.order.rep_work_set"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <fmt:message key="cra.order.sure_rep_work"/>
                                </div>
                                <div class="modal-footer">
                                    <form action="${pageContext.request.contextPath}${CRAPaths.EDIT_STATUS}" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="status" value="${OrderStatus.REPAIR_WORK}">
                                            <input type="hidden" name="orderID" value="${order_object.id}">
                                            <button type="submit" class="btn"><fmt:message key="cra.order.yes"/></button>
                                        </div>
                                    </form>
                                    <button type="button" class="btn cancel-btn" data-dismiss="modal"><fmt:message key="cra.order.cancel"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${user.role eq Role.MASTER and
            requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.MASTER_HOME and
            order_object.status eq OrderStatus.REPAIR_WORK}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#rep_compStatus${order_object.id}">
                        <fmt:message key="cra.order.completed"/>
                    </button>
                    <div class="modal fade" id="rep_compStatus${order_object.id}">
                        <div class="modal-dialog modal-dialog-centered modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <fmt:message key="cra.order.set_rep_comp"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <fmt:message key="cra.order.sure_comp_st"/>
                                </div>
                                <div class="modal-footer">
                                    <form action="${pageContext.request.contextPath}${CRAPaths.EDIT_STATUS}" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="status" value="${OrderStatus.REPAIR_COMPLETED}">
                                            <input type="hidden" name="orderID" value="${order_object.id}">
                                            <button type="submit" class="btn"><fmt:message key="cra.order.yes"/></button>
                                        </div>
                                    </form>
                                    <button type="button" class="btn cancel-btn" data-dismiss="modal"><fmt:message key="cra.order.cancel"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</c:if>