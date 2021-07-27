package com.netspam.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.bean.Product;
import com.netspam.model.TopK;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;

/**
 * Servlet implementation class UserSearchController
 */
@WebServlet("/UserSearchController")
public class UserSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Object uId = session.getAttribute("userId");
		int userId = Integer.parseInt(uId.toString());	
		String product= request.getParameter("product");
		String category=request.getParameter("category");
		if(!product.equals("")){
			UserService userService =new UserServiceImpl();
			ArrayList<Product> productList=userService.selectProducts(product,category);
			ArrayList<Product> topList = new ArrayList<Product>();
			if(productList.size()>0){
				TopK top = new TopK();
				topList = top.topKLargest(productList, 5);
				request.setAttribute("topList", topList);				
			}else{
				request.setAttribute("ErrMsg", "Records not found");
			}
			
		}else{
			request.setAttribute("ErrMsg", "Please select product");
		}
		RequestDispatcher rd = request.getRequestDispatcher("user_search.jsp");
		rd.include(request, response);
	}

}
