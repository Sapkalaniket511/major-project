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
 * Servlet implementation class UserInterestController
 */
@WebServlet("/UserInterestController")
public class UserInterestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInterestController() {
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
		String sort=request.getParameter("sort");
		double price=Double.parseDouble(request.getParameter("price"));
		UserService userService = new UserServiceImpl();
		if(!product.equals("")){
			ArrayList<Product> productList1=userService.selectProductRecords(product,category,sort,price);
			session.setAttribute("ProductListRec", productList1);
			RequestDispatcher rd = request.getRequestDispatcher("user_search.jsp");
			rd.forward(request, response);
		}
	}

}
