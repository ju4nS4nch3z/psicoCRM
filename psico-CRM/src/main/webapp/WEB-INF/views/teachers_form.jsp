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

<body>

	<%@ include file="includes/menu_top.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="includes/menu_left.jsp"%>

			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">


			<h3>Docent Registration</h3>
			<br>
			<form:form action='/teachers/save' modelAttribute="teacherForm"
				method='post'>

				<table class='table table-hover table-responsive table-bordered'>

					<spring:bind path="mail">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="mail" class="form-control"
								placeholder="Email" autofocus="true"></form:input>
							<form:errors path="mail"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<spring:message code="form.escuela" var="ph_escola" />
							<form:input type="text" path="name" class="form-control"
								placeholder="${ph_escola}" autofocus="true"></form:input>
							<form:errors path="name"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="surname1">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="surname1" class="form-control"
								placeholder="surname1" autofocus="true"></form:input>
							<form:errors path="surname1"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="surname2">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="surname2" class="form-control"
								placeholder="surname2" autofocus="true"></form:input>
							<form:errors path="surname2"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="phone">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="phone" class="form-control"
								placeholder="Telèfon" autofocus="true"></form:input>
							<form:errors path="phone"></form:errors>
						</div>
					</spring:bind>

					<spring:bind path="groups">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:select path="groups" items="${groupList}" multiple="true"
								itemValue="id" itemLabel="name" class="form-control input-sm" />
							<form:errors path="groups"></form:errors>
						</div>
					</spring:bind>

					<form:input type="hidden" path="password"/>
					<form:input type="hidden" path="administrator.id"/>
					<form:input type="hidden" path="id"/>

					<tr>
						<td></td>
						<td>
							<button type="submit" class="btn btn-primary">Register</button>
						</td>
					</tr>

				</table>

			</form:form>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document).ready(function() {

			var tg = '${teacherGroups}';
			if (typeof tg != 'undefined' && tg != '') {
				var tgJSON = $.parseJSON(tg);
				$.each(tgJSON, function(index, value) {
					var id = value.id;
					$("#groups option").each(function() {
						if ($(this).val() == id) {
							$(this).prop("selected", true);
						}
					});
				});
			}

		});
	</script>
</body>
</html>
