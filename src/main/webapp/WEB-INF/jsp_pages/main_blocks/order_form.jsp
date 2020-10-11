<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>
<%@ page import="ua.repair_agency.constants.RepairType" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<c:choose>
    <c:when test="${madeOrder eq null}">
        <div class="col-md-8 offset-md-2 rounded page-content">
            <form action="${pageContext.request.contextPath}${CRAPaths.CREATE_ORDER}" method="post">
                <h3><fmt:message key="cra.repair_order.header"/></h3>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.contains('carBrand')}">
                        <p class="formError">
                            <fmt:message key="cra.order_form.inconsistencies.brand"/>
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="<fmt:message key="cra.order_form.car_brand"/>" name="car_brand"
                           value="${prevForm.carBrand}">
                </div>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.contains('carModel')}">
                        <p class="formError">
                            <fmt:message key="cra.order_form.inconsistencies.model"/>
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="<fmt:message key="cra.order_form.car_model"/>" name="car_model"
                           value="${prevForm.carModel}">
                </div>
                <div class="col-lg-5 form-group">
                    <c:if test="${inconsistencies.contains('carYear')}">
                        <p class="formError">
                            <fmt:message key="cra.order_form.inconsistencies.year"/>
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="<fmt:message key="cra.order_form.car_year"/>"
                           name="car_year" value="${prevForm.carYear}">
                </div>
                <div class="col-lg-5 form-group">
                    <c:if test="${inconsistencies.contains('repairType')}">
                        <p class="formError">
                            <fmt:message key="cra.order_form.inconsistencies.rep_type"/>
                        </p>
                    </c:if>
                    <select name="repair_type" class="custom-select mb-3">
                        <c:choose>
                            <c:when test="${prevForm.repairType eq null}">
                                <option selected><fmt:message key="cra.order_form.rep_type"/></option>
                                <option value="${RepairType.ENGINE_REPAIR}"><fmt:message key="cra.order_form.eng_rep"/></option>
                                <option value="${RepairType.CHASSIS_REPAIR}"><fmt:message key="cra.order_form.chas_rep"/></option>
                                <option value="${RepairType.TRANSMISSION_REPAIR}"><fmt:message key="cra.order_form.tran_rep"/></option>
                                <option value="${RepairType.BATTERY_REPLACEMENT}"><fmt:message key="cra.order_form.bat_rep"/></option>
                                <option value="${RepairType.OIL_CHANGE}"><fmt:message key="cra.order_form.oil_chan"/></option>
                                <option value="${RepairType.PAINTING_WORKS}"><fmt:message key="cra.order_form.paint_works"/></option>
                                <option value="${RepairType.OTHER}"><fmt:message key="cra.order_form.other"/></option>
                            </c:when>
                            <c:otherwise>
                                <option><fmt:message key="cra.order_form.rep_type"/></option>
                                <option ${prevForm.repairType eq RepairType.ENGINE_REPAIR ? 'selected' : ''}
                                        value="${RepairType.ENGINE_REPAIR}"><fmt:message key="cra.order_form.eng_rep"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.CHASSIS_REPAIR ? 'selected' : ''}
                                        value="${RepairType.CHASSIS_REPAIR}"><fmt:message key="cra.order_form.chas_rep"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.TRANSMISSION_REPAIR ? 'selected' : ''}
                                        value="${RepairType.TRANSMISSION_REPAIR}"><fmt:message key="cra.order_form.tran_rep"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.BATTERY_REPLACEMENT ? 'selected' : ''}
                                        value="${RepairType.BATTERY_REPLACEMENT}"><fmt:message key="cra.order_form.bat_rep"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.OIL_CHANGE ? 'selected' : ''}
                                        value="${RepairType.OIL_CHANGE}"><fmt:message key="cra.order_form.oil_chan"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.PAINTING_WORKS ? 'selected' : ''}
                                        value="${RepairType.PAINTING_WORKS}"><fmt:message key="cra.order_form.paint_works"/>
                                </option>
                                <option ${prevForm.repairType eq RepairType.OTHER ? 'selected' : ''}
                                        value="${RepairType.  OTHER}"><fmt:message key="cra.order_form.other"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.contains('repairDescription')}">
                        <p class="formError">
                            <fmt:message key="cra.order_form.inconsistencies.rep_desc"/>
                        </p>
                    </c:if>
                    <textarea class="form-control" name="repair_description"
                              placeholder="<fmt:message key="cra.order_form.rep_desc"/>">${prevForm.repairDescription}</textarea>
                </div>
                <div id="order_footer" class="col-lg-12 row">
                    <div class="col-lg-4">
                        <button type="submit" class="btn btn-block"><fmt:message key="cra.order_form.make_order"/></button>
                    </div>
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <h3><fmt:message key="cra.order_form.success_header"/></h3>
        <cust:order loop_num="" order_object="${madeOrder}"/>
    </c:otherwise>
</c:choose>

