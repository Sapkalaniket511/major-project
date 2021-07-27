
<%@page import="com.netspam.connection.ConnectionUtils"%>
<%@ page import="java.sql.*,java.io.*,java.util.*" %> 
<%@ page import="com.netspam.bean.*" %>

<%@ page trimDirectiveWhitespaces="true" %>
<%
		int id = Integer.parseInt(request.getParameter("id"));
		String sql = "Select profilepic from tblUser where userid=?";
		PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			InputStream is = rs.getBinaryStream(1);
			
			byte[] profilePic = null;
		   	byte[] buffer = new byte[1024*1024*50];
			int bytesRead;		
			if(is!=null){
				while ((bytesRead =is.read(buffer)) != -1)
				{
				   	response.reset();
					response.setContentType("image/jpg");
					response.setHeader("Content-disposition","attachment; filename=" );
			    	response.getOutputStream().write(buffer, 0, bytesRead);
			    	response.getOutputStream().flush(); 
			 	}
			}		
	
		}		
		
%>