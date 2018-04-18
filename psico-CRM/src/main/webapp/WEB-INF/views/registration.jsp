<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="locale" value="${pageContext.response.locale}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ include file="includes/header.jsp"%>

<body background="${contextPath}/resources/static/images/background.jpg">

	<div class="container">

		<div id="registerModal" class="modal" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header" style="margin: 0 auto;">
						<h4>
							<spring:message code="form.escuela" />
						</h4>
					</div>
					<div class="modal-body">
						<form:form method="POST" modelAttribute="userForm"
							class="form-registrtation">

							<spring:bind path="school">
								<div
									class="form-group row ">
									<spring:message code="form.nombre" var="ph_escola" />
									<label for="school" class="col-sm-3 col-form-label">${ph_escola}*</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="text" path="school" class="form-control ${status.error ? 'is-invalid' : ''}"
											placeholder="${ph_escola}" autofocus="true" required=""></form:input>
										<form:errors path="school" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="address">
								<div
									class="form-group row ">
									<spring:message code="form.direccion" var="ph_direccion" />
									<label for="address" class="col-sm-3 col-form-label">${ph_direccion}</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="text" path="address" class="form-control ${status.error ? 'is-invalid' : ''}"
											placeholder="${ph_direccion}" autofocus="true"></form:input>
										<form:errors path="address" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="phone">
								<div
									class="form-group row ">
									<spring:message code="form.telefono" var="ph_telefono" />
									<label for="phone" class="col-sm-3 col-form-label">${ph_telefono}</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="text" path="phone" class="form-control ${status.error ? 'is-invalid' : ''}"
											placeholder="${ph_telefono}" autofocus="true"></form:input>
										<form:errors path="phone" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="cif">
								<div
									class="form-group row">
									<spring:message code="form.cif" var="ph_cif" />
									<label for="cif" class="col-sm-3 col-form-label">${ph_cif}</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="text" path="cif" class="form-control ${status.error ? 'is-invalid' : ''}"
											placeholder="${ph_cif}" autofocus="true"></form:input>
										<form:errors path="cif" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="mail">
								<div
									class="form-group row ">
									<spring:message code="form.mail" var="ph_mail" />
									<label for=mail class="col-sm-3 col-form-label">${ph_mail}*</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="mail" path="mail" class="form-control ${status.error ? 'is-invalid' : ''}"
											placeholder="${ph_mail}" autofocus="true"></form:input>
										<form:errors path="mail" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<spring:bind path="password">
								<div
									class="form-group row ">
									<spring:message code="form.password" var="ph_password" />
									<label for="password" class="col-sm-3 col-form-label">${ph_password}*</label>
									<div class="col-sm-12 col-lg-9">
										<form:input type="password" path="password" required=""
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="${ph_password}" autofocus="true"></form:input>
										<form:errors path="password" class="text-danger"></form:errors>
									</div>
								</div>
							</spring:bind>

							<%--spring:bind path="passwordConfirm">
            <div class="form-group row ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind--%>

							<button class="btn btn-lg btn-primary btn-block" type="submit">
								<spring:message code="btn.aceptar" />
							</button>

							<a href="/" class="btn btn-lg btn-danger btn-block"><spring:message
									code="btn.cancelar" /></a>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document).ready(function() {

			$("#registerModal").modal('show');

		});
	</script>
</body>
</html>