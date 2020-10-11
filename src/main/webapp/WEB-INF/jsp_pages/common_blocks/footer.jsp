<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust"%>

<div class="container-fluid" id="footer">
    <p><span>© CRA</span><cust:date-formatter localDateTime="${LocalDateTime.now()}" pattern="dd-MM-yyyy"/></p>
</div>