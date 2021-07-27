<%@page import="com.netspam.bean.Product"%>
<%@page import="java.util.ArrayList"%>
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
<!--//charts-->
<!-- geo chart -->
    <script src="//cdn.jsdelivr.net/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script>window.modernizr || document.write('<script src="lib/modernizr/modernizr-custom.js"><\/script>')</script>
    <!--<script src="lib/html5shiv/html5shiv.js"></script>-->
     <!-- Chartinator  -->
    <script src="js/chartinator.js" ></script>
    <style type="text/css">
 /* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 20%;
    top: 10%;
    width: 70%; /* Full width */
    height: 70%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content/Box */
.modal-content {
    background-color: #fefefe;
    margin: 15% auto; /* 15% from the top and centered */
    padding: 20px;
    border: 1px solid #888;
    width: 70%; /* Could be more or less, depending on screen size */
}

/* The Close Button */
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
</style>
        
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
						<p style="color:green"><%=sucMsg %></p>
					<%
						}
					%>
					<%
						String errMsg = (String)request.getAttribute("ErrMsg");
						if(errMsg!=null && errMsg!=""){
					%>
						<p style="color:red"><%=errMsg %></p>
					<%
						}
					%>
<div class="signup-page-main">
    <div class="signup-main">  	
    	 <div class="signup-head">
				<h1>User Search Product </h1>
			</div>
			<div class="signup-block">
								<form action="UserSearchController" method="post">
									<div class="form-group">
										<label> Select Product</label>										
											<select class="form-control" id="product" name="product" onchange="populateCategories();">
											<option value="">Select Product</option>
												<option value="Mobile Accessories">Mobile Accessories</option>
												<option value="Jewellery">Jewellery</option>
												<option value="Men's Clothing">Men's Clothing</option>
												<option value="Women's Clothing">Women's Clothing</option>
												<option value="Laptops">Laptops</option>												
											</select>										
										</div>
										<div class="form-group">
										<label> Select Category</label>										
											<select class="form-control" id="category" name="category">
																					
											</select>										
										</div>
												
									<div class="row">
										<div class="col-md-6">
											<button type="submit" class="btn btn-primary">Search</button>
										</div>
										
									</div>
																	<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <div>
    <%
						ArrayList<Product> productList = (ArrayList<Product>)session.getAttribute("ProductListRec");
						if(productList!=null&&productList.size()>0){
							for(int i=0;i<1;i++){
					%>
						<hr>
						<div><img alt="Product Pic" class="img-responsive" src="<%=productList.get(i).getImg_url() %>"/></div>
						<div><b style="color: blue;"><%=productList.get(i).getItem_name() %></b></div>
						<div style="color: orange;">Ratings :&nbsp;<%=productList.get(i).getRating() %></div>						
						<div style="color: maroon;">Rs. &nbsp;<%=productList.get(i).getPrice() %></div>
						<div style="color: olive;">Rs. &nbsp;<%=productList.get(i).getOffer() %></div>
						<div style="color: red;">(<%=productList.get(i).getDiscount() %>%)</div>
						<div style="color: purple;"><%=productList.get(i).getReviews() %> &nbsp; <a href="ListReviewController?id=<%=productList.get(i).getItem_id()%>">reviews</a></div>
						
					<%			
							}
						}
					%>
    </div>
  </div>

</div>			

					<%
						ArrayList<Product> topList = (ArrayList<Product>)request.getAttribute("topList");
						if(topList!=null){
							for(int i=0;i<topList.size();i++){
					%>
						<hr>
						<div><img alt="Product Pic" class="img-responsive" src="<%=topList.get(i).getImg_url() %>"/></div>
						<div><b style="color: blue;"><%=topList.get(i).getItem_name() %></b></div>
						<div style="color: orange;">Ratings :&nbsp;<%=topList.get(i).getRating() %></div>						
						<div style="color: maroon;">Rs. &nbsp;<%=topList.get(i).getPrice() %></div>
						<div style="color: olive;">Rs. &nbsp;<%=topList.get(i).getOffer() %></div>
						<div style="color: red;">(<%=topList.get(i).getDiscount() %>%)</div>
						<div style="color: purple;"><%=topList.get(i).getReviews() %> &nbsp; <a href="ListReviewController?id=<%=productList.get(i).getItem_id()%>">reviews</a></div>
						
					<%			
							}
						}
					%>
								</form>
										<div class="col-md-6">
											<button id="myBtn" class="btn btn-primary">Recommendation</button>
										</div>
								
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
<script type="text/javascript">
function populateCategories(){
	// given the id of the <select> tag as function argument, it inserts <option> tags
	var selectedProduct = document.getElementById("product");
	var categoryElement = document.getElementById("category");
	var productVal=selectedProduct.options[selectedProduct.selectedIndex].value;
	if(productVal==='Mobile Accessories'){
		categoryElement.length=0;
		categoryElement.options[0]=new Option('All', 'All');
		categoryElement.options[1]=new Option('Mobile', 'Mobile');
		categoryElement.options[2]=new Option('Headphones', 'Headphones');
		categoryElement.options[3]=new Option('Memory Adapter', 'Memory Adapter');
		categoryElement.options[4]=new Option('Power Bank', 'Power Bank');
	}else if(productVal==='Jewellery'){
		categoryElement.length=0;
		categoryElement.options[0]=new Option('All', 'All');
		categoryElement.options[1]=new Option('Necklace set', 'Necklace set');
		categoryElement.options[2]=new Option('Jewellery Box', 'Jewellery Box');
		categoryElement.options[3]=new Option('Earrings', 'Earrings');
		categoryElement.options[4]=new Option('Bangle Set', 'Bangle Set');
		categoryElement.options[5]=new Option('Hair Chain', 'Hair Chain');
	}else if(productVal==="Men's Clothing"){
		categoryElement.length=0;
		categoryElement.options[0]=new Option('All', 'All');
		categoryElement.options[1]=new Option('Shirt', 'Shirt');
		categoryElement.options[2]=new Option('Tshirt', 'Tshirt');
		categoryElement.options[3]=new Option('Jeans', 'Jeans');		
	}else if(productVal==="Women's Clothing"){
		categoryElement.length=0;
		categoryElement.options[0]=new Option('All', 'All');
		categoryElement.options[1]=new Option('Saree', 'Saree');
		categoryElement.options[2]=new Option('Kurti', 'Kurti');
		categoryElement.options[3]=new Option('Salwar Suit', 'Salwar Suit');		
	}else if(productVal==="Laptops"){
		categoryElement.length=0;
		categoryElement.options[0]=new Option('All', 'All');
		categoryElement.options[1]=new Option('Laptop', 'Laptop');		
	}
	
	
}

</script>
<script type="text/javascript">
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>
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