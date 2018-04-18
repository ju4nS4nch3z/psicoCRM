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

		<div id="resetPswModal" class="modal" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header" style="margin: 0 auto;">
						<h4>Reset Passsword</h4>
					</div>
					<div class="modal-body">

						<form:form method="POST" modelAttribute="userForm"
							class="form-signin">
							<div class="form-group">

								<spring:bind path="mail">
									<div class="form-group">
										<spring:message code="form.mail" var="ph_mail" />
										<label for="mail" class="col-sm-3 col-form-label">${ph_mail}</label>
										<form:input type="mail" path="mail"
											class="form-control ${error != null ? 'is-invalid' : ''}"
											placeholder="${ph_mail}" autofocus="true"></form:input>
									</div>

								</spring:bind>

								<c:if test="${error != null}">
									<div class="text-danger">
										<span>${error}</span>
									</div>
								</c:if>

								<button class="btn btn-lg btn-primary btn-block" type="submit">
									<spring:message code="btn.enviar" />
								</button>

								<a href="/login" class="btn btn-lg btn-danger btn-block"><spring:message
										code="btn.cancelar" /></a>

							</div>

						</form:form>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document).ready(function() {

			$("#resetPswModal").modal('show');

		});
	</script>

</body>
</html>

