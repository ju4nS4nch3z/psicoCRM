
<nav class="col-md-2 bg-light sidebar sidenav sidebar-offcanvas ">
	<div class="sidebar-sticky">
		<ul class="nav flex-column">

			<c:if test="${user.isAdmin()}">
				<li class="nav-item"><a class="nav-link active"
					href="/teachers/"> <i class="fa fa-user"></i> <spring:message
							code="menu.docentes" /><span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="/groups"> <i
						class="fa fa-users"></i> <spring:message code="menu.grupos" />
				</a></li>
			</c:if>

			<li class="nav-item"><a class="nav-link active" href="/students">
					<i class="fa fa-child"></i> <spring:message code="menu.alumnos" /><span
					class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link active"
				href="/students/stats/0"> <i class="fa fa-chart-bar"></i>
					Statistics <span class="sr-only">(current)</span>
			</a></li>

		</ul>

	</div>
</nav>
<%--
<div class="col-md-3 col-lg-2 sidebar-offcanvas pl-0" id="sidebar" role="navigation">
            <ul class="nav flex-column pl-1">
                <li class="nav-item"><a class="nav-link" href="#">Overview</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="#submenu1" data-toggle="collapse" data-target="#submenu1">Reports▾</a>
                    <ul class="list-unstyled flex-column pl-3 collapse" id="submenu1" aria-expanded="false">
                       <li class="nav-item"><a class="nav-link" href="">Report 1</a></li>
                       <li class="nav-item"><a class="nav-link" href="">Report 2</a></li>
                    </ul>
                </li>
                <li class="nav-item"><a class="nav-link" href="#">Analytics</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Export</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Snippets</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Flexbox</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Layouts</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Templates</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Themes</a></li>
            </ul>
        </div>
        
        --%>