package com.netspam.controller;

import java.io.IOException;
import java.sql.ResultSet;

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
 * Servlet implementation class UserStatusController
 */
@WebServlet("/UserStatusController")
public class UserStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(request.getParameter("userId"));
		String status = request.getParameter("status");
		UserService userService = new UserServiceImpl();
		if(userService.updateUser(userId, status)){
			ResultSet rs = userService.selectUser();
			if(rs!=null){
				HttpSession session = request.getSession();
				session.setAttribute("listUsers", rs);
				RequestDispatcher rd = request.getRequestDispatcher("admin_listusers.jsp");
				rd.forward(request, response);
			}else{
				request.setAttribute("ErrMsg", "Records are not found");
				RequestDispatcher rd = request.getRequestDispatcher("admin_home.jsp");
				rd.forward(request, response);
			}		
		}
	}

}
