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

			<select id="type" class="form-control input-sm">
				<option value="0">Alumne</option>
				<option value="1">Grup</option>
			</select> <select id="studentsL" class="form-control input-sm">
				<option value="0">Selecciona uno</option>
				<c:forEach items="${studentList}" var="student">
					<option value="${student.id}">${student.name}</option>
				</c:forEach>
			</select> <select id="groupL" class="form-control input-sm" disabled="true">
				<option value="0">Selecciona uno</option>
				<c:forEach items="${groupList}" var="group">
					<option value="${group.id}">${group.name}</option>
				</c:forEach>
			</select> <canvas class="my-4 chartjs-render-monitor" id="statsChart" width="927"
				height="391" style="display: block; width: 927px; height: 391px;"></canvas>



			</main>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/chartjs-plugin-annotation/0.5.7/chartjs-plugin-annotation.min.js"></script>

	<%@ include file="includes/footer.jsp"%>

	<script>
		var config = {};
		var id = 5;
		var type = 1;
		
		var statsChart = null;
		
		function loadChart() {
			if(statsChart != null){
				statsChart.destroy();	
			}
			var ctx = document.getElementById('statsChart').getContext('2d');
			statsChart = new Chart(ctx, config);
			//window.myLine = new Chart(ctx, config);

		}

		function ajaxCall(type, id) {
			$
					.ajax({
						type : "GET",
						//contentType : "application/json",
						url : "${pageContext.request.contextPath}/students/statistics/",
						data : {
							type : type,
							id : id
						},
						dataType : 'json',
						cache : false,
						timeout : 600000,
						success : function(data) {

							console.log(data);
							/*ini*/

							config = {
								type : 'line',
								data : {
									/*
									labels : [ "Sunday", "Monday", "Tuesday",
											"Wednesday", "Thursday", "Friday",
											"Saturday" ],
									 */
									labels : data.labels,
									datasets : [ {
										data : data.suArray,
										//data : [ 39, 34, 18, 24, 23, 20 ],
										lineTension : 0,
										label : 'Suicidio',
										backgroundColor : '#007bff',
										borderColor : '#007bff',
										fill : false,
										pointBackgroundColor : '#007bff'
									}, {
										data : data.maArray,
										//data : [ 33, 13, 8 ],
										lineTension : 0,
										label : 'Maltrato',
										backgroundColor : '#ff6384',
										borderColor : '#ff6384',
										fill : false,
										pointBackgroundColor : '#ff6384'
									}, {
										data : data.maArray,
										//data : [ 5, 3, 33, 28, 40, 23, 19, 20 ],
										lineTension : 0,
										label : 'Radicalismo',
										backgroundColor : '#36a2eb',
										borderColor : '#36a2eb',
										fill : false,
										pointBackgroundColor : '#36a2eb'
									} ]
								},
								options : {
									responsive : true,
									title : {
										display : true,
										text : 'Line Chart'
									},
									tooltips : {
										mode : 'index',
										intersect : false,
									},
									hover : {
										mode : 'nearest',
										intersect : true
									},
									scales : {
										xAxes : [ {
											display : true,
											scaleLabel : {
												display : true,
												labelString : 'Month'
											}
										} ],
										yAxes : [ {
											display : true,
											scaleLabel : {
												display : true,
												labelString : 'Value'
											}
										} ]
									},
									annotation : {
										annotations : [ {
											type : 'line',
											mode : 'horizontal',
											scaleID : 'y-axis-0',
											value : '10',
											borderColor : '#00FF00',
											borderWidth : 1
										}, {
											type : 'line',
											mode : 'horizontal',
											scaleID : 'y-axis-0',
											value : '20',
											borderColor : '#FFFF00',
											borderWidth : 1
										}, {
											type : 'line',
											mode : 'horizontal',
											scaleID : 'y-axis-0',
											value : '30',
											borderColor : '#FF0000',
											borderWidth : 1
										} ],
										drawTime : "afterDraw"
									}
								}
							};

							loadChart();
							/*fin*/
						},
						error : function(e) {

						}
					});
		}

		window.onload = function() {

			$("#studentsL").on('change', function() {
				id = this.value;
				ajaxCall(type, id)
			});

		};
	</script>
</body>
</html>