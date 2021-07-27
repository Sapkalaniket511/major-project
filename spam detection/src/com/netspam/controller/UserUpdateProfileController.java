package com.netspam.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.netspam.bean.User;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;

/**
 * Servlet implementation class UserUpdateProfileController
 */
@WebServlet("/UserUpdateProfileController")
@MultipartConfig(maxFileSize=1024*1024*50)
public class UserUpdateProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateProfileController() {
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
		HttpSession session =request.getSession();
		Object uId = session.getAttribute("userId");
		int userId = Integer.parseInt(uId.toString());
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String address = request.getParameter("address");
		String contact = request.getParameter("contact");		
		Part part=request.getPart("uploadImg");
		InputStream profilePic=null;		
		String profileName="";
		File upload=null;
		
		if(part!=null&&part.getContentType()!=null)
		{
			profilePic=part.getInputStream();
			System.out.println("is size:"+profilePic.available());
			profileName=extractFileName(part);
			System.out.println("name:"+profileName);		
			
		}
		UserService userService = new UserServiceImpl();
		
		if(userService.updateProfile(profilePic,profileName,userId,fname,lname,address,contact)){
			User user=userService.selectUser(userId);
			session.setAttribute("User", user.getFname()+" "+user.getLname());
			session.setAttribute("emailMsg", user.getEmail());
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("profilePic", user.getProfilePic());
			session.setAttribute("UserData", user);
			request.setAttribute("SucMsg", "Profile Changed Successfully");
		}
		RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
		rd.forward(request, response);
		
	}
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("contentDisp:"+contentDisp);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
		
	

}
