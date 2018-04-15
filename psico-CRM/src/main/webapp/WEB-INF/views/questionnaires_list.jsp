<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

			<div
				class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
				<h1 class="h2">List Of Tests</h1>
				<div class="btn-toolbar mb-2 mb-md-0">
					<div class="btn-group" role="group" aria-label="Basic example">
						<button type="button" class="btn btn-secondary">Left</button>
						<button type="button" class="btn btn-secondary">Middle</button>
						<button type="button" class="btn btn-secondary">Right</button>
					</div>
					<a href="#">
						<button type="button" class="btn btn-info" data-toggle="modal"
							data-target="#typeQModal">
							<i class="fa fa-plus"></i> Add New
						</button>
					</a>

				</div>
			</div>

			<%@ include file="includes/pagination.jsp"%>

			<table class="table table-hover">

				<thead>
					<tr>
						<th scope="col"><b>Tipus</b></th>
						<th scope="col"><b>Data</b></th>
						<th scope="col"><b>Resultat</b></th>
						<th scope="col"><b>Actions</b></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${page.content}" var="qdone">
						<c:set var="result" value="${qdone.result}" />

						<tr>
							<td><c:out value="${qdone.questionnaire.name}"></c:out></td>
							<td><fmt:formatDate value="${qdone.date}"
									pattern="dd/MM/yy HH:mm" /></td>
							<td><c:choose>

									<c:when test="${result <= 20}">
										<span class="text-success"><c:out
												value="Normalidad (${qdone.result})" /></span>
									</c:when>

									<c:when test="${result > 20 && result <= 30}">
										<span class="text-warning"><c:out
												value="Segimiento (${qdone.result})" /></span>
									</c:when>

									<c:otherwise>
										<span class="text-danger"><c:out
												value="Riesgo (${qdone.result})" /></span>
									</c:otherwise>
								</c:choose></td>

							<td><button param="${qdone.id}"
									class="downloadQ btn btn-primary">
									<i class="fa fa-file-pdf"></i>
								</button>
								<button param="${qdone.id}" class="sendQ btn btn-primary">
									<i class="fa fa-envelope"></i>
								</button>
								
								</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>

			</main>
		</div>
	</div>

	<div class="modal fade" id="typeQModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h4 class="modal-title">Nuevo Cuestionario</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body">
					<a href="/questionnaires/new/${student_id}/1"><button
							type="button" class="btn btn-lg  btn-block">Suicidio</button></a> <br>
					<a href="/questionnaires/new/${student_id}/2"><button
							type="button" class="btn btn-lg  btn-block">Maltrato</button></a> <br>
					<a href="/questionnaires/new/${student_id}/3"><button
							type="button" class="btn btn-lg  btn-block">Radicalismo</button></a>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>

	<div class="modal fade" id="sendQModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Enviar Resultado</h4>
					<button type="button" class="close closeBtn" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<p>Indica direcciones de email</p>
					<input type=text class="form-control" id="emails"> Enviar a
					los papas: <input type="checkbox" id=sendParents>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="closeBtn btn btn-danger"
						data-dismiss="modal">Cerrar</button>
					<button type="button" id="sendEmail" param="" class="btn btn-info">Enviar</button>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document).ready(function() {

			$('button.downloadQ').click(function() {

				var url = "/questionnaires/download?qDone=" + $(this).attr("param");
				window.location.href = url;
			});
			
			$('button.sendQ').click(function() {
				qDoneId = $(this).attr("param");
				$('#sendEmail').attr("param", qDoneId);
				$('#sendQModal').modal('show');
			});
			
			$('#sendEmail').click(function() {

				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/questionnaires/send/",
					data : {
						qDone : $(this).attr("param"),
						emails : $("#emails").val(),
						sendParents : $('#sendParents').is(':checked')
					},
					dataType : 'json',
					cache : false,
					timeout : 600000,
					success : function(data) {

						console.log(data);
						$('button.closeBtn').click();
					},
					error : function(e) {

					}
				});

			});
		});
	</script>

</body>
</html>
