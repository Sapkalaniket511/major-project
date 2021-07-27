package com.netspam.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netspam.model.Review;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;

/**
 * Servlet implementation class ListReviewController
 */
@WebServlet("/ListReviewController")
public class ListReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListReviewController() {
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
		String itemId=request.getParameter("id");
		UserService userService = new UserServiceImpl();
		ArrayList<Review> reviewList = userService.selectReviews(itemId);
		System.out.println("ReviewList----------------"+reviewList);
		if(reviewList.size()>0){
			request.setAttribute("ReviewList", reviewList);
			RequestDispatcher rd = request.getRequestDispatcher("user_reviews.jsp");
			rd.forward(request, response);
		}
		else{
			request.setAttribute("ErrMsg", "Could not load reviews");
			RequestDispatcher rd = request.getRequestDispatcher("user_search.jsp");
			rd.include(request, response);
		}
	}

}
