<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>${presentation.name}</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS here -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/themify-icons.css" rel="stylesheet">
    <link href="/css/nice-select.css" rel="stylesheet">
    <link href="/css/flaticon.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/slicknav.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/owl.carousel.min.css" rel="stylesheet">
</head>

<body>
<!-- Header -->
<jsp:include page="header.jsp"/>

<!-- photo_gallery_start -->
<div class="photo_gallery">
    <div class="container">
        <div class="row">
            <div class="col-xl-12">
                <div class="section_title mb-33">
                    <h3>${presentation.name}</h3>
                </div>
            </div>
            <div class="col-xl-12">
                <div class="photo_gallery_active owl-carousel">
                    <c:forEach items="${presentation.slides}" var="slide">
                        <a href="${slide.imageUrl}">
                            <div
                                    class="single_photo_gallery gallery_bg_${slide.index}"
                                    style="background-image: url('${slide.imageUrl}');background-size: cover">
                                <div class="photo_caption">
                                    <h3>${presentation.name} Slide ${slide.index}</h3>
                                </div>
                                <img style="width:50px;height:50px;position: absolute;right: 0;bottom: 0;"
                                     src="${slide.qrCodeUrl}"/>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- photo_gallery_end -->


<jsp:include page="footer.jsp"/>

<!-- JS here -->
<script src="/js/vendor/modernizr-3.5.0.min.js"></script>
<script src="/js/vendor/jquery-1.12.4.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/isotope.pkgd.min.js"></script>
<script src="/js/ajax-form.js"></script>
<script src="/js/waypoints.min.js"></script>
<script src="/js/jquery.counterup.min.js"></script>
<script src="/js/imagesloaded.pkgd.min.js"></script>
<script src="/js/scrollIt.js"></script>
<script src="/js/jquery.scrollUp.min.js"></script>
<script src="/js/wow.min.js"></script>
<script src="/js/nice-select.min.js"></script>
<script src="/js/jquery.slicknav.min.js"></script>
<script src="/js/jquery.magnific-popup.min.js"></script>
<script src="/js/plugins.js"></script>

<script src="/js/main.js"></script>

</body>

</html>