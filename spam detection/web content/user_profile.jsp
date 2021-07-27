<%@page import="com.netspam.bean.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NetSpam Framework</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all">
<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
<!--js-->
<script src="js/jquery-2.1.1.min.js"></script> 
<!--icons-css-->
<link href="css/font-awesome.css" rel="stylesheet"> 
<!--Google Fonts-->
<link href='//fonts.googleapis.com/css?family=Carrois+Gothic' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Work+Sans:400,500,600' rel='stylesheet' type='text/css'>
<!--static chart-->
<script src="js/Chart.min.js"></script>
<script src="validation.js"></script>
<!--//charts-->
<!-- geo chart -->
    <script src="//cdn.jsdelivr.net/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script>window.modernizr || document.write('<script src="lib/modernizr/modernizr-custom.js"><\/script>')</script>
    <!--<script src="lib/html5shiv/html5shiv.js"></script>-->
     <!-- Chartinator  -->
    <script src="js/chartinator.js" ></script>
    <script type="text/javascript">
        jQuery(function ($) {

            var chart3 = $('#geoChart').chartinator({
                tableSel: '.geoChart',

                columns: [{role: 'tooltip', type: 'string'}],
         
                colIndexes: [2],
             
                rows: [
                    ['China - 2015'],
                    ['Colombia - 2015'],
                    ['France - 2015'],
                    ['Italy - 2015'],
                    ['Japan - 2015'],
                    ['Kazakhstan - 2015'],
                    ['Mexico - 2015'],
                    ['Poland - 2015'],
                    ['Russia - 2015'],
                    ['Spain - 2015'],
                    ['Tanzania - 2015'],
                    ['Turkey - 2015']],
              
                ignoreCol: [2],
              
                chartType: 'GeoChart',
              
                chartAspectRatio: 1.5,
             
                chartZoom: 1.75,
             
                chartOffset: [-12,0],
             
                chartOptions: {
                  
                    width: null,
                 
                    backgroundColor: '#fff',
                 
                    datalessRegionColor: '#F5F5F5',
               
                    region: 'world',
                  
                    resolution: 'countries',
                 
                    legend: 'none',

                    colorAxis: {
                       
                        colors: ['#679CCA', '#337AB7']
                    },
                    tooltip: {
                     
                        trigger: 'focus',

                        isHtml: true
                    }
                }

               
            });                       
        });
    </script>
<!--geo chart-->

<!--skycons-icons-->
<script src="js/skycons.js"></script>
<!--//skycons-icons-->
</head>
<body>	
<div class="page-container">	
   <div class="left-content">
	   <div class="mother-grid-inner">
            <!--header start here-->
				<div class="header-main">
					<div class="header-left">
							<div class="logo-name">
									 <h1><a href="home.jsp"> NetSpam Framework 
									<!--<img id="logo" src="" alt="Logo"/>--> 
								  </a></h1> 								
							</div>							
							<div class="clearfix"> </div>
						 </div>
						 <div class="header-right">
							<%
			String emailMsg = (String)session.getAttribute("emailMsg");
			Object uId = session.getAttribute("userId");
			int userId = Integer.parseInt(uId.toString());		
            String name=(String)session.getAttribute("User");
			if(name!=null && name!=""){
		%>
							<!--notification menu end -->
							<div class="profile_details">		
								<ul>
									<li class="dropdown profile_details_drop">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
											<div class="profile_img">	
												<span class="prfil-img"><img alt="Profile Pic" class="img-circle" src="profilepic.jsp?id=<%=userId %>"  width="100px" height="70px"/></span> 
												<div class="user-name">
												<span>Welcome</span>
													<p><%=name %></p>
													
												</div>
												<i class="fa fa-angle-down lnr"></i>
												<i class="fa fa-angle-up lnr"></i>
												<div class="clearfix"></div>	
											</div>	
										</a>
										<ul class="dropdown-menu drp-mnu">											
											<li> <a href="UserViewProfileController"><i class="fa fa-user"></i> Profile</a> </li>
											<li> <a href="user_changepassword.jsp"><i class="fa fa-cog"></i> Change Password</a> </li>  
											<li> <a href="UserLogoutController"><i class="fa fa-sign-out"></i> Logout</a> </li>
										</ul>
									</li>
								</ul>
							</div>
							<%} %>
							<div class="clearfix"> </div>				
						</div>
				</div>
<!--heder end here-->
<!-- script-for sticky-nav -->
		<script>
		$(document).ready(function() {
			 var navoffeset=$(".header-main").offset().top;
			 $(window).scroll(function(){
				var scrollpos=$(window).scrollTop(); 
				if(scrollpos >=navoffeset){
					$(".header-main").addClass("fixed");
				}else{
					$(".header-main").removeClass("fixed");
				}
			 });
			 
		});
		</script>
		<!-- /script-for sticky-nav -->
<!--inner block start here-->
<br>
<br>
				<div class="inner-block">
					<%
						String sucMsg = (String)request.getAttribute("SucMsg");
						if(sucMsg!=null && sucMsg!=""){
					%>
					<p style="color: green"><%=sucMsg %></p>
					<%
						}
					%>
					<%
						String errMsg = (String)request.getAttribute("ErrMsg");
						if(errMsg!=null && errMsg!=""){
					%>
					<p style="color: red"><%=errMsg %></p>
					<%
						}
					%>

					<div class="login-page">
						<div class="login-main">
							<div class="login-head">
								<h1>User Profile </h1>
							</div>
							<div class="login-block">
								<form action="UserUpdateProfileController" method="post" enctype="multipart/form-data">
									<%
                	User userBean =(User)session.getAttribute("UserData");
                	%>
									<div class="form-group">
										<label>First Name</label> <input type="text"
											class="form-control" placeholder="First Name" name="fname"
											value="<%=userBean.getFname()%>" pattern="[A-Za-z]+"
											title="Name must be alphabets only" required="required">
									</div>
									<div class="form-group">
										<label>Last Name</label> <input type="text"
											class="form-control" placeholder="Last Name" name="lname"
											value="<%=userBean.getLname()%>" pattern="[A-Za-z]+"
											title="Name must be alphabets only" required="required">
									</div>
									<div class="form-group">
										<label>Email-ID</label> <input type="email"
											class="form-control" name="email"
											value="<%=userBean.getEmail()%>" disabled="disabled">
									</div>
									<div class="form-group">
										<label>Mobile No.</label> <input type="text"
											class="form-control" placeholder="Mobile No." name="contact"
											value="<%=userBean.getContact()%>"
											pattern="[7-9]{1}[0-9]{9}"
											title="Mobile number must be starts with 7, 8 or 9 digit and total number of digits are 10"
											required="required">
									</div>
									<div class="form-group">
										<label>Address</label>
										<textarea class="form-control" placeholder="Address"
											name="address" rows="1"><%=userBean.getAddress()%></textarea>
									</div>
									
									<div class="form-group">
										<label>Change
											your profile pic</label>
										<div align="center">
											<br>
											<img alt="Profile Pic" class="img-circle"
												src="profilepic.jsp?id=<%=userBean.getUserId()%>" width="200px"
												height="150px" />
										</div>
									</div>
									<div class="form-group">
										<input type="file" name="uploadImg">
										<p class="help-block">(Examples of images .jpg, .jpeg,
											.png, etc format)</p>
									</div>
									<hr>

									<div class="row">
										<div class="col-md-6">
											<button type="submit" class="btn btn-danger">Update</button>
										</div>
										
									</div>

								</form>
							</div>
						</div>
					</div>
				</div>
				<!--inner block end here-->


</div>
</div>
<!--slider menu-->
    <div class="sidebar-menu">
		  	<div class="logo"> <a href="#" class="sidebar-icon"> <span class="fa fa-bars"></span> </a> <a href="#"> <span id="logo" ></span> 
			      <!--<img id="logo" src="" alt="Logo"/>--> 
			  </a> </div>		  
		    <div class="menu">
		      <ul id="menu" >
		        <li id="menu-home" ><a href="user_home.jsp"><i class="fa fa-tachometer"></i><span>Home</span></a></li>
		        <li><a href="UserProductListController"><i class="fa fa-cogs"></i><span>Product List</span></a></li>
		        	          
		      </ul>
		    </div>
	 </div>
	<div class="clearfix"> </div>
</div>
<!--slide bar menu end here-->

<script>
var toggle = true;
            
$(".sidebar-icon").click(function() {                
  if (toggle)
  {
    $(".page-container").addClass("sidebar-collapsed").removeClass("sidebar-collapsed-back");
    $("#menu span").css({"position":"absolute"});
  }
  else
  {
    $(".page-container").removeClass("sidebar-collapsed").addClass("sidebar-collapsed-back");
    setTimeout(function() {
      $("#menu span").css({"position":"relative"});
    }, 400);
  }               
                toggle = !toggle;
            });
</script>

<!--scrolling js-->
		
		<script src="js/scripts.js"></script>
		<!--//scrolling js-->
<script src="js/bootstrap.js"> </script>
<!-- mother grid end here-->
</body>
</html>                     