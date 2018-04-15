
<nav class="col-md-2 d-none d-md-block bg-light sidebar">
	<div class="sidebar-sticky">
		<ul class="nav flex-column">
			<c:choose>
				<c:when test="${user.isAdmin()}">
					<li class="nav-item"><a class="nav-link active"
						href="/teachers/"> <i class="fa fa-user"></i> <spring:message
								code="menu.docentes" /><span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="/groups"> <i
							class="fa fa-users"></i> <spring:message code="menu.grupos" />
					</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link active"
						href="/students"> <i class="fa fa-user"></i> <spring:message
								code="menu.alumnos" /><span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="/students/stats/0"> <i class="fa fa-chart-bar"></i> Statistics <span
							class="sr-only">(current)</span>
					</a></li>
				</c:otherwise>
			</c:choose>

		</ul>

	</div>
</nav>