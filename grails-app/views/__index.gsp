<!DOCTYPE html>
<%@ page import="com.sarathi.domain.Product" %>
<html class="csstransforms no-csstransforms3d csstransitions"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>Prizy</title>
	<link rel="stylesheet" type="text/css" href="css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="css/menu.css">
    
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/function.js"></script>

</head>
<body>
<div id="wrap">
	<header>
		<div class="inner relative">
			<a class="logo" href="#"><img src="images/logo.png" alt="fresh design web"></a>
			<a id="menu-toggle" class="button dark" href="#"><i class="icon-reorder"></i></a>
			<nav id="navigation">
				<ul id="main-menu">
					<li class="current-menu-item"><a href="#">Home</a></li>
					<li class="parent">
						<a href="#">Products</a>
						<ul class="sub-menu">
							<li><a href="/my-prizy/product/index"><i class="icon-wrench"></i> List</a></li>
							<li><a href="#"><i class="icon-credit-card"></i>  </a></li>
							<li><a href="#"><i class="icon-gift"></i> </a></li>
							<li>
								<a class="parent" href="#"><i class="icon-file-alt"></i> Pages</a>
								<ul class="sub-menu">
									<li><a href="#">Full Width</a></li>
									<li><a href="#">Left Sidebar</a></li>
									<li><a href="#">Right Sidebar</a></li>
									<li><a href="#">Double Sidebar</a></li>
								</ul>
							</li>
						</ul>
					</li>
					<li><a href="#">Prices</a></li>
					<%--<li class="parent">
						<a href="#">Blog</a>
						<ul class="sub-menu">
							<li><a href="#">Large Image</a></li>
							<li><a href="#">Medium Image</a></li>
							<li><a href="#">Masonry</a></li>
							<li><a href="#">Double Sidebar</a></li>
							<li><a href="#">Single Post</a></li>
						</ul>
					</li>
					--%><li><a href="#">Contact</a></li>
				</ul>
			</nav>
			<div class="clear"></div>
		</div>
	</header>	
</div>
<div>
			<div id="result-div" style="width: 100%;" >
				
			</div>
		</div>
<script type="text/javascript">
	$( window ).load(function() {
		$.ajax({
            type: 'POST',
            url: '${createLink(controller: 'product', action: 'listProducts')}',
            data: {	
            	searchText: $("#searchText").val(),
            	max: 10
            }, success: function(data) {
            	$('#result-div').replaceWith(data);
            }
        });
	});
</script>
</body></html>