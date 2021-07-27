package com.netspam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.services.AdminService;
import com.netspam.services.AdminServiceImpl;


/**
 * Servlet implementation class AdminPasswordController
 */
@WebServlet("/AdminPasswordController")
public class AdminPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPasswordController() {
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
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("newpassword1");
		String email = request.getParameter("email");
		AdminService adminService = new AdminServiceImpl();
		if(adminService.updateAdmin(oldPassword, newPassword, email))
		{
			request.setAttribute("SucMsg", "Password changed successfully");
			RequestDispatcher rd = request.getRequestDispatcher("admin_home.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "Password does not changed");
			RequestDispatcher rd = request.getRequestDispatcher("admin_changepassword.jsp");
			rd.include(request, response);
		}
	}

}
