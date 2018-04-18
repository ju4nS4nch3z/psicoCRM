
<header>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	<!-- a class="navbar-brand" href="#">${user.getOrganization()}</a-->
	
	<div class="flex-row d-flex">
        <a class="navbar-brand" href="#" >${user.getOrganization()}</a>
        <button type="button" class="navbar-toggler" data-toggle="offcanvas" title="Toggle responsive left sidebar">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>

	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarCollapse"
		aria-controls="navbarCollapse" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<div class="collapse navbar-collapse navbar-collapse-right"
		id="navbarCollapse">

		<ul class="navbar-nav ml-auto">

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="accountDropdown"
				data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${user.mail}</a>
				<div class="dropdown-menu" aria-labelledby="accountDropdown">
					<a class="dropdown-item" href="/user/edit/${user.id}">Edit
						Acount</a> <a class="dropdown-item" href="/logout"><spring:message
							code="menu.salir" /></a>
				</div></li>

		</ul>
	</div>

</nav>
</header>
<%--

<nav class="navbar fixed-top navbar-expand-md navbar-dark bg-primary mb-3">
    <div class="flex-row d-flex">
        <a class="navbar-brand" href="#" title="Free Bootstrap 4 Admin Template">Admin</a>
        <button type="button" class="navbar-toggler" data-toggle="offcanvas" title="Toggle responsive left sidebar">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse collapse" id="collapsingNavbar">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">Home</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="//www.codeply.com">Codeply</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#myAlert" data-toggle="collapse">Alert</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="" data-target="#myModal" data-toggle="modal">About</a>
            </li>
        </ul>
    </div>
</nav>
--%>