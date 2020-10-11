<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>


<div class="col-md-10 offset-md-1 rounded">
    <h1><fmt:message key="cra.404.page_not_found"/></h1>
</div>
