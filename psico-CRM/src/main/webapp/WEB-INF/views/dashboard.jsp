<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="locale" value="${pageContext.response.locale}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ include file="includes/header.jsp" %>

<body>

	<%@ include file="includes/menu_top.jsp" %>


	<div class="container-fluid">
		<div class="row">

			<%@ include file="includes/menu_left.jsp" %>
			
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

			</main>
		</div>
	</div>

	<%@ include file="includes/footer.jsp" %>

</body>
</html>