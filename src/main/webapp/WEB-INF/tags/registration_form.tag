<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.repair_agency.constants.Role" %>
<%@ tag import="ua.repair_agency.constants.CRAPaths"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>


<c:choose>
    <c:when test="${success eq null}">
        <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq
        CRAPaths.REGISTRATION or requestScope['javax.servlet.forward.servlet_path'] eq
        CRAPaths.MAN_MAS_REGISTRATION ? '' : ' modal-dialog-centered'}">
            <div class="modal-content">
                <div class="modal-header">
                    <c:choose>
                        <c:when test="${user.role.name() eq Role.ADMIN}">
                            <h4 class="modal-title"><fmt:message key="cra.reg_form.header.new_man"/></h4>
                        </c:when>
                        <c:otherwise>
                            <h4 class="modal-title"><fmt:message key="cra.reg_form.header.sign"/></h4>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="modal-body">
                    <form action="<c:choose>
                            <c:when test="${user.role.name() eq Role.ADMIN}">
                                ${pageContext.request.contextPath}${CRAPaths.MAN_MAS_REGISTRATION}
                            </c:when>
                            <c:otherwise>
                                ${pageContext.request.contextPath}${CRAPaths.REGISTRATION}
                            </c:otherwise>
                        </c:choose>" method="post">
                        <c:if test="${inconsistencies.contains('firstName')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.f_name"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.f_name_pl"/>" name="fName"
                                   value="${prevForm.firstName}">
                        </div>
                        <c:if test="${inconsistencies.contains('lastName')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.l_name"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.l_name_pl"/>" name="lName"
                                   value="${prevForm.lastName}">
                        </div>
                        <c:if test="${inconsistencies.contains('email')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.email"/>
                            </p>
                        </c:if>
                        <c:if test="${inconsistencies.contains('notFreeEmail')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.not_free_email"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required placeholder="E-mail" name="email"
                                   value="${prevForm.email}">
                        </div>

                        <c:if test="${inconsistencies.contains('password')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.pass"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="password" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.pass_pl"/>" name="pass">
                        </div>
                        <c:if test="${inconsistencies.contains('passwordConfirmation')}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.pass_conf"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="password" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.pass_conf_pl"/>" name="passConf">
                        </div>
                        <c:choose>
                            <c:when test="${user.role.name() eq Role.ADMIN}">
                                <div class="form-group reg_radio">
                                    <c:if test="${inconsistencies.contains('role')}">
                                        <p class="formError">
                                            <fmt:message key="cra.reg_form.inc.role"/>
                                        </p>
                                    </c:if>
                                    <div class="form-check-inline">
                                        <input type="radio" class="form-check-input" name="role" value="${Role.MASTER}"
                                            ${prevForm.role eq Role.MASTER ? 'checked' : ''}><fmt:message key="cra.reg_form.master"/>
                                    </div>
                                    <div class="form-check-inline">
                                        <input type="radio" class="form-check-input" name="role" value="${Role.MANAGER}"
                                            ${prevForm.role eq Role.MANAGER ? 'checked' : ''}><fmt:message key="cra.reg_form.manager"/>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" value="${Role.CUSTOMER}" name="role">
                            </c:otherwise>
                        </c:choose>
                        <div class="col-sm-6 offset-sm-3 submit-button">
                            <button type="submit" class="btn btn-block"><fmt:message
                                    key="cra.reg_form.reg_btn"/></button>
                        </div>
                    </form>
                </div>
                <div class=" modal-footer">
                    <c:if test="${user.role.name() ne Role.ADMIN}">
                        <div class="col-sm-12">
                            <a <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/registration'}">
                                    href="${pageContext.request.contextPath}${CRAPaths.LOGIN}"
                                </c:when>
                                <c:otherwise>
                                    href="" data-toggle="modal" data-target="#loginForm" data-dismiss="modal"
                                </c:otherwise>
                            </c:choose>><fmt:message key="cra.reg_form.alr_reg_btn"/></a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="scc_reg" class="col-md-12" >
            <c:if test="${param.role eq Role.CUSTOMER}">
                <h3><fmt:message key="cra.reg_form.success.com"/></h3>
                <a href="${pageContext.request.contextPath}${CRAPaths.LOGIN}"><fmt:message key="cra.reg_form.success.go_to"/></a>
            </c:if>
            <c:if test="${param.role eq Role.MASTER}"><h3><fmt:message key="cra.reg_form.success.mas"/></h3></c:if>
            <c:if test="${param.role eq Role.MANAGER}"><h3><fmt:message key="cra.reg_form.success.man"/></h3></c:if>
        </div>
    </c:otherwise>
</c:choose>