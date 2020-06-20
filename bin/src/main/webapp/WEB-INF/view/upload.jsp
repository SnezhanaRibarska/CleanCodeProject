<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html class="no-js" lang="bg" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Presentations</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <!-- CSS here -->
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css">
    <link rel="stylesheet" href="/css/magnific-popup.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/themify-icons.css">
    <link rel="stylesheet" href="/css/nice-select.css">
    <link rel="stylesheet" href="/css/flaticon.css">
    <link rel="stylesheet" href="/css/animate.css">
    <link rel="stylesheet" href="/css/slicknav.css">
    <link rel="stylesheet" href="/css/style.css">
    <!-- <link rel="stylesheet" href="css/responsive.css"> -->
</head>

<body>
<!-- Header -->
<%@include file='header.jsp' %>

<div class="whole-wrap">
    <div class="container box_1170">

        <div class="section-top-border">
            <h3 class="mb-30">Форма за качване на презентации</h3>
            <div class="row">
                <div class="col-lg-12">
                    <blockquote class="generic-blockquote">
                        Презентациите трябва да са във формат .ppt, .pptx. Може да качите една презентация или да изберете няколко презентации
                        и да ги архивирате в .zip файл, който да качите, чрез формата.
                    </blockquote>
                </div>
            </div>
        </div>

        <div class="section-top-border">
            <div class="row">
                <div class="col-lg-12 col-md-12">
                    <h3 class="mb-30">Качване на презентации</h3>


                    <p class="sample-text">
                        <c:if test="${not empty message}">
                            ${message}
                        </c:if>
                    </p>


                    <form action="/presentations/upload" th:object="${form}" method="POST" enctype="multipart/form-data"
                          name="form">
                        <div class="mt-10">
                            <input type="text" name="name" placeholder="Име на файла"
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Име'" required
                                   class="single-input">
                        </div>

                        <div class="mt-10">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" name="zipFile" placeholder="Търси">
                                <label class="custom-file-label">Избери файл</label>
                            </div>
                        </div>
                        <div class="mt-10">
                            <input type="text" name="tags" placeholder="Тагове"
                                   onfocus="this.placeholder = ''"
                                   onblur="this.placeholder = 'Тагове, разделени със запетая'" required
                                   class="single-input">
                        </div>
                        <div class="button-group-area mt-40">
                            <input class="genric-btn info circle" type="submit" value="Качване"/>
                            <input class="genric-btn danger circle right" type="reset" value="Изчисти"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<%@include file='footer.jsp' %>

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

<script>
    $(".custom-file-input").on("change", function () {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
</script>

</body>

</html>