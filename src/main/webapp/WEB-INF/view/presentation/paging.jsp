<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="d-block">
    <c:if test="${pages>0}">
        <c:forEach begin="0" end="${pages-1}" var="page">
            <a <c:if
                    test="${pageNumber==page}"> style="color:#fd7e14" </c:if>href="?page=${page}&tag=${selectedTag}">${page+1}</a>
        </c:forEach>
    </c:if>
</div>