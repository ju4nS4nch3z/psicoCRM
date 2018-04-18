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
		<div class="row row-offcanvas row-offcanvas-left">

			<%@ include file="includes/menu_left.jsp"%>

			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

			<div
				class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
				<h1 class="h2">List Of Students</h1>
				<div class="btn-toolbar mb-2 mb-md-0">
					<a href="/students/edit/0">
						<button type="button" class="btn btn-info">
							<i class="fa fa-plus"></i> Add New
						</button>
					</a>

				</div>
			</div>

			<%@ include file="includes/pagination.jsp"%>

			<table class="table table-hover">

				<thead>
					<tr>
						<th><b>Nom</b></th>
						<th><b>Sexe</b></th>
						<th><b>Email</b></th>
						<th><b>Gup</b></th>
						<th><b>Transactions</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.content}" var="student">
						<tr>
							<td><c:out value="${student.name}"></c:out></td>
							<td><c:out value="${student.gender}"></c:out></td>
							<td><c:out value="${student.parentName1}"></c:out></td>
							<td><c:out value="${student.group.name}"></c:out></td>

							<td><a href="/questionnaires/list/0/${student.id}/1">
									<button type="submit" class="btn btn-primary">
										<i class="fa fa-clipboard-list"></i>
									</button>
							</a></td>
							<td><a href="/students/edit/${student.id}">
									<button type="submit" class="btn btn-primary">
										<i class="fa fa-edit"></i>
									</button>
							</a></td>
							<td><a href="/students/delete/${student.id}">
									<button type="submit" class="btn btn-primary">
										<i class="fa fa-trash"></i>
									</button>
							</a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>

			</main>
		</div>
	</div>

	<%@ include file="includes/footer.jsp"%>

</body>
</html>
