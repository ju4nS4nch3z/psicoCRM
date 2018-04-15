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


		<div id="loginModal" class="modal" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header" style="margin: 0 auto;">
						<h4>Login</h4>
					</div>
					<div class="modal-body">
						<form method="POST" action="login" class="form-signin">
							<div class="form-group">
								<c:if test="${error != null}">
									<div class="alert alert-danger">
										<span>${error}</span>
									</div>
								</c:if>
								<c:if test="${success != null}">
									<div class="alert alert-success">
										<span>${success}</span>
									</div>
								</c:if>

								<input name="mail" type="text" class="form-control"
									placeholder="Email" autofocus="true" required="" /> <input
									name="password" type="password" class="form-control"
									placeholder="Clau" required="" /> <input type="hidden"
									name="${_csrf.parameterName}" value="${_csrf.token}" />

								<button class="btn btn-lg btn-primary btn-block" type="submit">Log
									In</button>

								<a href="/" class="btn btn-lg btn-danger btn-block"><spring:message
										code="btn.cancelar" /></a>

							</div>

						</form>
					</div>
					<div>
						<p class="text-center">
							<a href="${contextPath}/registration">Create an account</a> or <a
								href="${contextPath}/resetPassword">reset password</a>
						</p>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document).ready(function() {

			$("#loginModal").modal('show');

		});
	</script>

</body>
</html>

