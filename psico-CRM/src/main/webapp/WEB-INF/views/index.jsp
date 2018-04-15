<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="locale" value="${pageContext.response.locale}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<jsp:include page="includes/header.jsp"/>


<body>

	<header> <nav
		class="navbar navbar-expand-md navbar-dark fixed-top bg-dark"> <a
		class="navbar-brand" href="#">PsicoCRM</a>


	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarCollapse"
		aria-controls="navbarCollapse" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse navbar-collapse-right"
		id="navbarCollapse">
		<div class="float-right">
			<button class="btn float-right">
				<a href="registration"><spring:message code="btn.registrarse" /></a>
			</button>
			<button class="btn float-right">
				<a href="login"><spring:message code="btn.entrar" /></a>
			</button>
		</div>
		<select style="width: 67px;" class="form-control" id="locales">
			<option value="es">ES</option>
			<option value="ca">CA</option>
		</select>
	</div>

	</nav> </header>

	<main role="main"> <!-- Slides with Controls & Captions -->
	<div id="slidercaption" class="carousel slide" data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#slidercaption" data-slide-to="0" class="active"></li>
			<li data-target="#slidercaption" data-slide-to="1"></li>
			<li data-target="#slidercaption" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="carousel-item active">
				<img style="width: 100%;" class="d-block img-fluid"
					src="${contextPath}/resources/static/images/carousel1.jpg"
					alt="Slide1">
				<div class="carousel-caption d-none d-md-block">
					<h3>Here is a caption for slide 1</h3>
					<p>Here is short description for slide 1</p>
				</div>
			</div>
			<div class="carousel-item">
				<img style="width: 100%;" class="d-block img-fluid"
					src="${contextPath}/resources/static/images/carousel2.jpg"
					alt="Slide2">
				<div class="carousel-caption d-none d-md-block">
					<h3>Here is a caption for slide 2</h3>
					<p>Here is short description for slide 2</p>
				</div>
			</div>
			<div class="carousel-item">
				<img style="width: 100%;" class="d-block img-fluid"
					src="${contextPath}/resources/static/images/carousel3.jpg"
					alt="Slide3">
				<div class="carousel-caption d-none d-md-block">
					<h3>Here is a caption for slide 3</h3>
					<p>Here is short description for slide 3</p>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#slidercaption" role="button"
			data-slide="prev"> <span class="carousel-control-prev-icon"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#slidercaption" role="button"
			data-slide="next"> <span class="carousel-control-next-icon"
			aria-hidden="true"></span> <span class="sr-only">Next</span>
		</a>
	</div>
	<br>
	<br>
	</div>


	<!-- Marketing messaging and featurettes
      ================================================== --> <!-- Wrap the rest of the page in another container to center all the content. -->

	<div class="container marketing">

		<!-- Three columns of text below the carousel -->
		<div class="row">
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image" width="140" height="140">
				<h2>Heading</h2>
				<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis
					euismod. Nullam id dolor id nibh ultricies vehicula ut id elit.
					Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
					Praesent commodo cursus magna.</p>
				<p>
					<a class="btn btn-secondary" href="#" role="button">View
						details »</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image" width="140" height="140">
				<h2>Heading</h2>
				<p>Duis mollis, est non commodo luctus, nisi erat porttitor
					ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
					purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,
					tortor mauris condimentum nibh.</p>
				<p>
					<a class="btn btn-secondary" href="#" role="button">View
						details »</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image" width="140" height="140">
				<h2>Heading</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-secondary" href="#" role="button">View
						details »</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->




	</div>
	<!-- /.container --> <!-- FOOTER --> <footer class="container">
	<p class="float-right">
		<a href="#">Back to top</a>
	</p>
	<p>
		© 2017-2018 Company, Inc. · <a href="#">Privacy</a> · <a href="#">Terms</a>
	</p>
	</footer> </main>



	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script>
		$('.carousel').carousel({
			interval : 2500
		})

		$(document).ready(function() {
			$("#locales").val('${locale}');
			
			$("#locales").change(function() {
				var lang = $('#locales').val();
				if (lang != '') {
					window.location.replace('?lang=' + lang);
				}
			});
		});
	</script>

	<svg xmlns="http://www.w3.org/2000/svg" width="500" height="500"
		viewBox="0 0 500 500" preserveAspectRatio="none"
		style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;">
	<defs>
	<style type="text/css"></style>
	</defs> <text x="0" y="25"
		style="font-weight:bold;font-size:25pt;font-family:Arial, Helvetica, Open Sans, sans-serif">500x500</text></svg>
</body>
</html>