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
			<h3>Student Registration</h3>
			<br>
			<form:form action='/students/save' modelAttribute="studentForm"
				method='post'>

				<table class='table table-hover table-responsive table-bordered'>


					<spring:bind path="name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<spring:message code="form.escuela" var="ph_escola" />
							<form:input type="text" path="name" class="form-control"
								placeholder="${ph_escola}" autofocus="true"></form:input>
							<form:errors path="name"></form:errors>
						</div>
					</spring:bind>
					
				<spring:bind path="group">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:select path="group" items="${groupList}" multiple="false"
								itemValue="id" itemLabel="name" class="form-control input-sm" />
							<form:errors path="group"></form:errors>
						</div>
					</spring:bind>

					<form:input type="hidden" path="id" class="form-control"
						autofocus="true" />

					<tr>
						<td></td>
						<td>
							<button type="submit" class="btn btn-primary">Register</button>
						</td>
					</tr>

				</table>

			</form:form> </main>
		</div>
	</div>

	<%@ include file="includes/footer.jsp"%>

</body>
</html>
