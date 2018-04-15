
<style>
/* Stackoverflow preview fix, please ignore */
.navbar-nav {
	flex-direction: row;
}

.nav-link {
	padding-right: .5rem !important;
	padding-left: .5rem !important;
}

/* Fixes dropdown menus placed on the right side */
.ml-auto .dropdown-menu {
	left: auto !important;
	right: 0px;
}
</style>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top p-0">
	<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">${user.getOrganization()}</a>

	<!-- ul class="navbar-nav mr-auto">
		<li class="nav-item active"><a class="nav-link">Left Link 1</a></li>
		<li class="nav-item"><a class="nav-link">Left Link 2</a></li>
	</ul-->


	<ul class="navbar-nav ml-auto px-3">
		<!-- li class="nav-item"><a class="nav-link">Right Link 1</a></li-->
		<li class="nav-item dropdown text-nowrap">
			<a class="nav-link dropdown-toggle"
			href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false">${user.mail}</a>
			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
				<a class="dropdown-item" href="/user/edit/${user.id}">Edit Acount</a> 
				<a class="dropdown-item" href="/logout"><spring:message code="menu.salir"/></a>
			</div>
		</li>
	</ul>

	<%--	<ul class="navbar-nav px-3">
		<li class="nav-item text-nowrap"><a class="nav-link"
			href="/logout"><spring:message code="menu.salir" /></a></li>
	</ul>
	--%>
</nav>