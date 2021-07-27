package com.netspam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.bean.User;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;



/**
 * Servlet implementation class UserViewProfileController
 */
@WebServlet("/UserViewProfileController")
public class UserViewProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserViewProfileController() {
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
		
		UserService userService = new UserServiceImpl();
		User user = userService.selectUser(userId);
		if(user!=null){			
			session.setAttribute("UserData", user);
			RequestDispatcher rd = request.getRequestDispatcher("user_profile.jsp");
			rd.forward(request, response);
		}
	}

}
