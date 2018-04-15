<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="locale" value="${pageContext.response.locale}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Psico CRM</title>

<link rel="stylesheet" href="${contextPath}/resources/static/bootstrap-4.0.0/css/bootstrap.css">

</head>

<body>

	<div class="container">

		<form:form method="POST" modelAttribute="userForm" class="form-signin">
			<h2 class="form-signin-heading">Create your <spring:message code="form.escuela" /></h2>

			<spring:bind path="school">
				<div class="form-group ${status.error ? 'has-error' : ''}">
				<spring:message code="form.escuela" var="ph_escola" />
					<form:input type="text" path="school" class="form-control"
						placeholder="${ph_escola}" autofocus="true"></form:input>
					<form:errors path="school"></form:errors>
				</div>
			</spring:bind>

			<spring:bind path="address">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="address" class="form-control"
						placeholder="Adereça" autofocus="true"></form:input>
					<form:errors path="address"></form:errors>
				</div>
			</spring:bind>

			<spring:bind path="phone">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="phone" class="form-control"
						placeholder="Telèfon" autofocus="true"></form:input>
					<form:errors path="phone"></form:errors>
				</div>
			</spring:bind>

			<spring:bind path="cif">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="cif" class="form-control"
						placeholder="CIF" autofocus="true"></form:input>
					<form:errors path="cif"></form:errors>
				</div>
			</spring:bind>

			<spring:bind path="mail">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="text" path="mail" class="form-control"
						placeholder="mail" autofocus="true"></form:input>
					<form:errors path="mail"></form:errors>
				</div>
			</spring:bind>

			<spring:bind path="password">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<form:input type="password" path="password" class="form-control"
						placeholder="Password"></form:input>
					<form:errors path="password"></form:errors>
				</div>
			</spring:bind>

			<%--spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind--%>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
			<button class="btn btn-lg btn-primary btn-block" type="submit"><a href="/">Cancelar</a></strong> </button>
		</form:form>

	</div>
	<!-- /container -->
	<script
		src="${contextPath}/resources/static/jquery/jquery-3.3.1.min.js"></script>
	<script
		src="${contextPath}/resources/static/bootstrap-4.0.0/js/bootstrap.min.js"></script>
</body>
</html>