<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty tags}">
    <div class="col-xl-4 col-md-4">
        <div class="section_title mb-33">
            <h3>Тагове</h3>
        </div>
        <div class="tags">
            <ul>
                <c:forEach items="${tags}" var="tag">
                    <li <c:if test="${tag eq selectedTag}">class="active"</c:if>>
                        <a href="?tag=${tag}">${tag}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>