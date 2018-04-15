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

			<h4>
				<c:out value="${qDoneForm.questionnaire.name}"></c:out>
			</h4>

			<div class="row clearfix"></div>

			<div class="col-md-10 column">
				<p class="lead">
					Completo al <span class="progressbarNum">0</span>%
				</p>
				<div class="progress">
					<div class="progress-bar" id="progressbar" role="progressbar"
						style="width: 0%;"></div>
				</div>
			</div>

			<br>

			<c:forEach items="${qDoneForm.questionnaire.questions}"
				var="question" varStatus="qstatus">

				<div class="col-md-10 column d-none question"
					id="quest${qstatus.index}">
					<div class="card card-primary">
						<div class="card-heading">
							<h4 class="card-title" style="padding: 5px;">
								<c:out value="${question.text}"></c:out>
							</h4>
						</div>
						<div class="card-body">
							<c:forEach items="${question.options}" var="option">
								<div class="radio">
									<label> <input type="radio"
										name="optionsRadios_${question.id}" question="${question.id}"
										optionId="${option.id}"
										id="optionsRadios_${option.id}_${option.value}"
										value="${option}"> <c:out value="${option.text}"></c:out>
									</label>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>

			</c:forEach> <form:form action='/questionnaires/save' name="qDoneForm"
				modelAttribute="qDoneForm" method='post'>

				<form:input type="hidden" path="id" />
				<form:input type="hidden" path="questionnaire" />
				<form:input type="hidden" path="student" />
				<form:input type="hidden" path="date" />
				<form:input type="hidden" path="language" />

				<c:forEach items="${qDoneForm.answers}" varStatus="awstatus">

					<form:input type="hidden"
						path="answers[${awstatus.index}].questionnaire_done" />
					<form:input type="hidden"
						path="answers[${awstatus.index}].question.id" />
					<form:input type="hidden"
						path="answers[${awstatus.index}].option.id" />

				</c:forEach>

				<div class="btn-group">
					<button type="button" id="qprev" class="btn btn-primary"
						disabled="true">Anterior</button>
					<button type="button" id="qnext" class="btn btn-primary">Siguiente</button>
				</div>

				<button type="submit" id="saveQ" class="btn btn-success"
					disabled="true">Guardar</button>

			</form:form> </main>
		</div>
	</div>

	<div class="modal fade" id="resultModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h4 class="modal-title">Resultado</h4>
					<button type="button" class="close closeBtn" data-dismiss="modal">&times;</button>
				</div>

				<div class="modal-body">
					<p>
						<c:out value="${testResult}"></c:out>
					</p>
				</div>

				<div class="modal-footer">
					<button type="button" class="closeBtn btn btn-danger"
						data-dismiss="modal">Aceptar</button>
					<button type="button" class="btn btn-info" data-dismiss="modal" data-toggle="modal"
						data-target="#sendQModal">Enviar resultado</button>
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
					<button type="button" id="sendBtn" class="btn btn-info">Enviar</button>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="includes/footer.jsp"%>

	<script>
		$(document)
				.ready(
						function() {

							$('#sendBtn').click(function() {

								$.ajax({
									type : "GET",
									url : "${pageContext.request.contextPath}/questionnaires/send/",
									data : {
										qDone : "${qDoneForm.id}",
										student : "${qDoneForm.student.id}",
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

							$('button.closeBtn').click(function() {
								var url = "/questionnaires/list/" 
								+ ${qDoneForm.questionnaire.id}
								+"/" + ${qDoneForm.student.id}
								+"/1";
								window.location.href = url;
							});

							var result = "${testResult}";

							if (typeof result != 'undefined' && result != '') {
								console.log('result:' + result);
								$('#qprev #qnext #saveQ')
										.prop('disabled', true);
								$('input[type=radio]').prop('disabled', true);
								$('#resultModal').modal('show');
							}

							var map = {};
							var page = Number(0);
							updatePage(page);

							function updatePage(page) {

								$('#qprev').prop('disabled', (page <= 0));
								$('#qnext').prop('disabled', (page >= 18));

								$('.question').addClass('d-none');

								$('#quest' + page).removeClass('d-none');
								$('#quest' + (page + 1)).removeClass('d-none');

							}

							$('#qprev').click(function() {
								page = page - 2;
								updatePage(page);
							});

							$('#qnext').click(function() {
								page = page + 2;
								updatePage(page);
							});

							$('input[type=radio]')
									.change(
											function() {
												var optionVal = $(this).val();
												var optionId = $(this).attr(
														'optionId');
												var questionNum = $(this).attr(
														'question');
												var q = Number(questionNum) - 1;

												map[q] = optionVal;

												updateProgress(map);

												$(
														"input[name='answers["
																+ q
																+ "].option.id']")
														.val(optionId);
												$(
														"input[name='answers["
																+ q
																+ "].option.value']")
														.val(optionVal);

											});

							function updateProgress(map) {
								var bar = 0;
								for (var i = 0; i < 20; i++) {
									if (map[i] != null) {
										bar++;
									}
								}
								var width = Number(bar) * 5;
								$('.progressbarNum').html(width);
								$("#progressbar").css("width", width + "%");

								if (width == 100) {
									$('#saveQ').prop('disabled', false);
								}
							}

						});
	</script>
</body>
</html>