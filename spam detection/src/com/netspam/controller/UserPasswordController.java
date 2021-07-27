package com.netspam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netspam.bean.User;
import com.netspam.mailutils.ForgotPassword;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;


/**
 * Servlet implementation class UserPasswordController
 */
@WebServlet("/UserPasswordController")
public class UserPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPasswordController() {
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
		/*String email= request.getParameter("email");
		UserService userService = new UserServiceImpl();
		User user = userService.selectUser(email);
		if(user.getPassword()!=null && user.getPassword()!=""){
			request.setAttribute("SucInfoMsg", "Your password is: "+user.getPassword());
			RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg2", "Your account is not registered....");
			RequestDispatcher rd = request.getRequestDispatcher("user_forgotpassword.jsp");
			rd.include(request, response);

		}*/
		String email= request.getParameter("email");
		UserService userService = new UserServiceImpl();
		User user = userService.selectUser(email);
		if(user.getPassword()!=null && user.getPassword()!=""){
			ForgotPassword forgotPass=new ForgotPassword();
			forgotPass.emailUtility(email, user.getPassword());
			request.setAttribute("SucInfoMsg", "Your password is sent successfully");
			RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg2", "Your account is not registered....");
			RequestDispatcher rd = request.getRequestDispatcher("user_forgotpassword.jsp");
			rd.include(request, response);

		}
	}

}
