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
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;

/**
 * Servlet implementation class UserProductListController
 */
@WebServlet("/UserProductListController")
public class UserProductListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProductListController() {
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
		UserService userService = new UserServiceImpl();
		ArrayList<Product> productList = userService.selectProductList();
		 if(productList!=null && productList.size()>0){
			HttpSession session = request.getSession();
			session.setAttribute("ProductList", productList);
			RequestDispatcher rd = request.getRequestDispatcher("user_listproducts.jsp");
			rd.forward(request, response);
	     }else{
			request.setAttribute("ErrMsg", "There is no record");
			RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
			rd.include(request, response);
	     }
	}

}
