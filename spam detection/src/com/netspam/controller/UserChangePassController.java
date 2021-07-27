package com.netspam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;


/**
 * Servlet implementation class UserChangePassController
 */
@WebServlet("/UserChangePassController")
public class UserChangePassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserChangePassController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("newpassword1");
		
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("emailMsg");
		UserService userService = new UserServiceImpl();
		if(userService.updateUSer(oldPassword, newPassword, email))
		{
			request.setAttribute("SucMsg", "Password changed successfully");
			RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "Password does not changed");
			RequestDispatcher rd = request.getRequestDispatcher("user_changepassword.jsp");
			rd.include(request, response);
		}
	}

}
