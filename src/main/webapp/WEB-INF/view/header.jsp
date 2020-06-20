<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header-start -->
<header>
    <div class="header-area ">
        <div class="header_top">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-xl-4 col-md-4 d-none d-md-block">
                        <div class="header_links ">
                            <ul>
                                <li></li>
                                <li></li>
                                <li></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-xl-4 col-md-4">
                        <div class="logo">
                            <c:choose>
                                <c:when test="${empty sessionScope.userID}">
                                    <a href="/login">
                                        <img src="/img/logo.png" width="500px" alt="">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/home">
                                        <img src="/img/logo.png" width="500px" alt="">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:if test="${not empty sessionScope.userID}">
                        <div class="col-xl-4 col-md-4 d-none d-md-block">
                            <div class="login_resiter">
                                <p><a href="/presentations/u/${sessionScope.userID}"><i
                                        class="flaticon-user"></i>Профил</a></p>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div id="sticky-header" class="main-header-area white-bg">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-xl-12 col-lg-12">
                        <div class="main-menu  d-none d-lg-block">
                            <nav>
                                <ul id="navigation">
                                    <li><a href="/home">Начало</a></li>
                                    <li><a href="/presentations/upload">Качи Презентации</a></li>
                                    <li><a href="/presentations">Всички Презентации</a></li>
                                </ul>
                            </nav>
                            <!--<a id="export-zip" href="/presentations.zip" download="presentations">Test</a>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- header-end -->