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

import com.netspam.model.Item;
import com.netspam.model.Review;
import com.netspam.services.AdminService;
import com.netspam.services.AdminServiceImpl;

/**
 * Servlet implementation class AdminExtractReviewController
 */
@WebServlet("/AdminExtractReviewController")
public class AdminExtractReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminExtractReviewController() {
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
		String item_id=request.getParameter("item_id");
		Item an_item = new Item(item_id);
		an_item.fetchReview();
		ArrayList<Review> reviewList = an_item.reviews;
		System.out.println(reviewList.size());
		if(reviewList.size()>0 && reviewList!=null){
			AdminService adminService= new AdminServiceImpl();
			ArrayList<Review> reviewList2=adminService.addReviewList(reviewList);
			
				HttpSession session = request.getSession();
				session.setAttribute("ReviewList", reviewList2);
				RequestDispatcher rd = request.getRequestDispatcher("admin_reviewlist.jsp");
				rd.forward(request, response);
			
		}else{
			request.setAttribute("ErrMsg", "There is no one reviews");
			RequestDispatcher rd = request.getRequestDispatcher("admin_home.jsp");
			rd.forward(request, response);
		}
	}

}
