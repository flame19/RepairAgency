<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.repair_agency.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust"%>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<h3><fmt:message key="cra.home_common.about_us.header"/>:</h3>
<div class="container" id="aboutUs">
    <div class="col-md-10 offset-md-1 rounded page-content">
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">1</span> <fmt:message
                    key="cra.home_common.about_us.first"/>
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">6</span> <fmt:message
                    key="cra.home_common.about_us.six"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">2</span> <fmt:message
                    key="cra.home_common.about_us.second"/>
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">7</span> <fmt:message
                    key="cra.home_common.about_us.seventh"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">3</span> <fmt:message
                    key="cra.home_common.about_us.third"/>
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">8</span> <fmt:message
                    key="cra.home_common.about_us.eighth"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">4</span> <fmt:message
                    key="cra.home_common.about_us.fourth"/>
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">9</span> <fmt:message
                    key="cra.home_common.about_us.ninth"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">5</span> <fmt:message
                    key="cra.home_common.about_us.fifth"/>
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">10</span> <fmt:message
                    key="cra.home_common.about_us.tenth"/>
            </div>
        </div>
    </div>
</div>

<div class="container" id="suggestions">
    <h4><fmt:message key="cra.home_common.suggestions.header"/>:</h4>
    <div class="row col-md-12">
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.eng_rep"/></h5>
            <div class="movableIcon">
                <img src="static/img/engine.png" alt="chassis picture">
            </div>
        </div>
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.ch_rep"/></h5>
            <div class="movableIcon">
                <img src="static/img/chassis.png" alt="engine pictre">
            </div>
        </div>
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.tr_rep"/></h5>
            <div class="movableIcon">
                <img src="static/img/gearshift.png" alt="suspension picture">
            </div>
        </div>
    </div>
    <div class="row col-md-12">
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.bat_repl"/></h5>
            <div class="movableIcon">
                <img src="static/img/accumulator.png" alt="chassis picture">
            </div>
        </div>
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.oil_ch"/></h5>
            <div class="movableIcon">
                <img src="static/img/oil.png" alt="engine pictre">
            </div>
        </div>
        <div class="col-md-4">
            <h5><fmt:message key="cra.home_common.suggestions.painting"/></h5>
            <div class="movableIcon">
                <img src="static/img/car-painting.png" alt="suspension picture">
            </div>
        </div>
    </div>
</div>

<div class="row col-md-12" id="reviewsCards">
    <div class="col-md-12" id="reviewsHeader">
        <h2><fmt:message key="cra.home_common.reviews.header"/></h2>
    </div>
    <div class="row col-md-12">
        <c:forEach var="review" items="${reviews}">
            <div class="card col-md-3">
                <div class="card-body">
                    <h5>${review.customer.firstName}</h5>
                    <p>${review.reviewContent}</p>
                    <p class="reviewsDate">
                        <small><cust:date-formatter localDateTime="${review.dateTime}" pattern="yyyy-MM-dd"/></small></p>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="row col-md-12" id="reviewsLink">
        <div class="col-md-5 offset-md-7">
            <a href="${pageContext.request.contextPath}${CRAPaths.REVIEWS}">
                <fmt:message key="cra.home_common.reviews.view_all"/></a>
        </div>
    </div>
</div>

<div class="row" id="contacts">
    <div class="col-md-12">
        <h4><fmt:message key="cra.home_common.contacts.header"/>:</h4>
        <hr>
    </div>
    <div class="col-md-6">
        <p><fmt:message key="cra.home_common.contacts.sch_ser"/></p>
        <p><fmt:message key="cra.home_common.contacts.mon_fri"/>: 09:00-18:00</p>
        <p><fmt:message key="cra.home_common.contacts.sat"/>: 10:00-15:00</p>
    </div>
    <div class="col-md-6">
        <p><fmt:message key="cra.home_common.contacts.sch_cent"/></p>
        <p><fmt:message key="cra.home_common.contacts.mon_fri"/>: 08:00-21:00</p>
        <p><fmt:message key="cra.home_common.contacts.sat_sun"/>: 10:00-20:00</p>
    </div>
    <div class="col-md-4 offset-md-4">
        <hr>
        <h5><i class="fas fa-phone-alt"></i> (044) 333-44-55 </h5>
        <h5><i class="fas fa-envelope"></i>cra.official@gmail.com</h5>
        <hr>
        <h4><fmt:message key="cra.home_common.contacts.address"/></h4>
        <p><i class="fas fa-map-marker-alt"></i><fmt:message key="cra.home_common.contacts.kv"/> 15</p>
        <br>
        <p><i class="fas fa-map-marker-alt"></i><fmt:message key="cra.home_common.contacts.chr"/> 38</p>
        <br>
        <p><i class="fas fa-map-marker-alt"></i><fmt:message key="cra.home_common.contacts.pl"/> 112</p>
    </div>
</div>