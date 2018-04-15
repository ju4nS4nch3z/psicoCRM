<c:url var="firstUrl" value="${path}1" />
<c:url var="lastUrl" value="${path}${page.totalPages}" />
<c:url var="prevUrl" value="${path}${currentIndex - 1}" />
<c:url var="nextUrl" value="${path}${currentIndex + 1}" />

			<div >
				<ul class="pagination">
					<c:choose>
						<c:when test="${currentIndex == 1}">
							<li class="page-item disabled"><a class="page-link" href="#">&lt;&lt;</a></li>
							<li class="page-item disabled"><a class="page-link" href="#">&lt;</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="${firstUrl}">&lt;&lt;</a></li>
							<li class="page-item"><a class="page-link" href="${prevUrl}">&lt;</a></li>
						</c:otherwise>
					</c:choose>
					<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
						<c:url var="pageUrl" value="${path}${i}" />
						<c:choose>
							<c:when test="${i == currentIndex}">
								<li class="page-item active"><a class="page-link" href="${pageUrl}"><c:out
											value="${i}" /></a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link" href="${pageUrl}"><c:out value="${i}" /></a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${currentIndex == page.totalPages}">
							<li class="page-item disabled"><a class="page-link" href="#">&gt;</a></li>
							<li class="page-item disabled"><a class="page-link" href="#">&gt;&gt;</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a  class="page-link" href="${nextUrl}">&gt;</a></li>
							<li class="page-item"><a class="page-link" href="${lastUrl}">&gt;&gt;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>