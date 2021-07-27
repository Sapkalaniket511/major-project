package com.netspam.dao;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.netspam.bean.Product;
import com.netspam.bean.User;
import com.netspam.connection.ConnectionUtils;
import com.netspam.model.Review;


public class UserDaoImpl implements UserDao{

	@Override
	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql = "Insert into tblUser values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, user.getFname());
			pstmt.setString(3, user.getLname());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getPassword());
			pstmt.setDate(6, new Date(user.getDob().getTime()));
			pstmt.setString(7, user.getGender());
			pstmt.setString(8, user.getAddress());
			pstmt.setString(9, user.getContact());
			pstmt.setString(10, user.getSecQuestion());
			pstmt.setString(11, user.getSecAnswer());
			pstmt.setString(12, user.getIpAddress());
			pstmt.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
			pstmt.setBlob(14, user.getProfilePic());
			pstmt.setString(15, user.getProfileName());
			pstmt.setString(16, user.getStatus());
			int index = pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isAlreadyAvailable(User user) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql = "Select * from tblUser where emailid ='"+user.getEmail()+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public User selectUser(String email, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "Select * from tblUser where emailid ='"+email.toLowerCase()+"' and password='"+password+"' and status='Active'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setSecQuestion(rs.getString(10));
				user.setSecAnswer(rs.getString(11));
				user.setIpAddress(rs.getString(12));
				user.setRegTime(rs.getTimestamp(13));
				user.setProfilePic(rs.getBinaryStream(14));				
				user.setProfileName(rs.getString(15));
				user.setStatus(rs.getString(16));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User selectUser(String email) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "Select * from tblUser where emailid ='"+email.toLowerCase()+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setSecQuestion(rs.getString(10));
				user.setSecAnswer(rs.getString(11));
				user.setIpAddress(rs.getString(12));
				user.setRegTime(rs.getTimestamp(13));
				user.setProfilePic(rs.getBinaryStream(14));				
				user.setProfileName(rs.getString(15));
				user.setStatus(rs.getString(16));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean updateUser(String oldPassword, String newPassword, String email) {
		// TODO Auto-generated method stub
		boolean flag=false;
		
		String sql = "Update tblUser set password=? where password=? and emailid=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, oldPassword);
			pstmt.setString(3, email);
			int index = pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ResultSet selectUser() {
		// TODO Auto-generated method stub
		ResultSet rs= null;
		String sql ="Select * from tblUser";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}

	@Override
	public boolean updateUser(int userId, String status) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(status.equalsIgnoreCase("Inactive"))
			status="Active";
		else
			status = "Inactive";
		
		String sql = "Update tblUser Set status=? Where userid=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, userId);
			int index=pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public ArrayList<Product> selectProductList() {
		// TODO Auto-generated method stub
		ArrayList<Product> productList = new ArrayList<Product>();
		String sql="Select * from tblProduct";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Product prod =new Product();
				prod.setId(rs.getInt(1));
				prod.setProduct(rs.getString(2));
				prod.setItem_id(rs.getString(3));
				prod.setItem_name(rs.getString(4));
				prod.setPrice(rs.getDouble(5));
				prod.setOffer(rs.getDouble(6));
				prod.setDiscount(rs.getInt(7));
				prod.setRating(rs.getDouble(8));
				prod.setReviews(rs.getInt(9));
				prod.setCategory(rs.getString(10));
				prod.setImg_url(rs.getString(11));
				productList.add(prod);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productList;
	}

	@Override
	public ArrayList<Product> selectProductRecords(String product,
			String category, String sort, double price) {
		// TODO Auto-generated method stub
		ArrayList<Product> productList = new ArrayList<Product>();
		if(sort.equals("Rating")){
			String sql="Select * from tblProduct Where product=? and category=? and price<=? Order by rating desc";
			try {
				PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, product);
				pstmt.setString(2, category);
				pstmt.setDouble(3, price);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()){
					Product prod =new Product();
					prod.setId(rs.getInt(1));
					prod.setProduct(rs.getString(2));
					prod.setItem_id(rs.getString(3));
					prod.setItem_name(rs.getString(4));
					prod.setPrice(rs.getDouble(5));
					prod.setOffer(rs.getDouble(6));
					prod.setDiscount(rs.getInt(7));
					prod.setRating(rs.getDouble(8));
					prod.setReviews(rs.getInt(9));
					prod.setCategory(rs.getString(10));
					prod.setImg_url(rs.getString(11));
					productList.add(prod);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(sort.equals("Reviews")){
			String sql="Select * from tblProduct Where product=? and category=? and price<=? Order by reviews desc";
			try {
				PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, product);
				pstmt.setString(2, category);
				pstmt.setDouble(3, price);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()){
					Product prod =new Product();
					prod.setId(rs.getInt(1));
					prod.setProduct(rs.getString(2));
					prod.setItem_id(rs.getString(3));
					prod.setItem_name(rs.getString(4));
					prod.setPrice(rs.getDouble(5));
					prod.setOffer(rs.getDouble(6));
					prod.setDiscount(rs.getInt(7));
					prod.setRating(rs.getDouble(8));
					prod.setReviews(rs.getInt(9));
					prod.setCategory(rs.getString(10));
					prod.setImg_url(rs.getString(11));
					productList.add(prod);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(sort.equals("Discount")){
			String sql="Select * from tblProduct Where product=? and category=? and price<=? Order by discount desc";
			try {
				PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, product);
				pstmt.setString(2, category);
				pstmt.setDouble(3, price);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()){
					Product prod =new Product();
					prod.setId(rs.getInt(1));
					prod.setProduct(rs.getString(2));
					prod.setItem_id(rs.getString(3));
					prod.setItem_name(rs.getString(4));
					prod.setPrice(rs.getDouble(5));
					prod.setOffer(rs.getDouble(6));
					prod.setDiscount(rs.getInt(7));
					prod.setRating(rs.getDouble(8));
					prod.setReviews(rs.getInt(9));
					prod.setCategory(rs.getString(10));
					prod.setImg_url(rs.getString(11));
					productList.add(prod);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return productList;
	}

	@Override
	public ArrayList<Product> selectProducts(String product, String category) {
		// TODO Auto-generated method stub
		ArrayList<Product> productList = new ArrayList<Product>();
		if(category.equals("All")){
			String sql="Select * from tblProduct Where product=? Order by reviews desc";
			try {
				PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, product);				
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()){
					Product prod =new Product();
					prod.setId(rs.getInt(1));
					prod.setProduct(rs.getString(2));
					prod.setItem_id(rs.getString(3));
					prod.setItem_name(rs.getString(4));
					prod.setPrice(rs.getDouble(5));
					prod.setOffer(rs.getDouble(6));
					prod.setDiscount(rs.getInt(7));
					prod.setRating(rs.getDouble(8));
					prod.setReviews(rs.getInt(9));
					prod.setCategory(rs.getString(10));
					prod.setImg_url(rs.getString(11));
					productList.add(prod);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String sql="Select * from tblProduct Where product=? and category=? Order by reviews desc";
			try {
				PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, product);
				pstmt.setString(2, category);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()){
					Product prod =new Product();
					prod.setId(rs.getInt(1));
					prod.setProduct(rs.getString(2));
					prod.setItem_id(rs.getString(3));
					prod.setItem_name(rs.getString(4));
					prod.setPrice(rs.getDouble(5));
					prod.setOffer(rs.getDouble(6));
					prod.setDiscount(rs.getInt(7));
					prod.setRating(rs.getDouble(8));
					prod.setReviews(rs.getInt(9));
					prod.setCategory(rs.getString(10));
					prod.setImg_url(rs.getString(11));
					productList.add(prod);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return productList;
	}

	@Override
	public ArrayList<Review> selectReviews(String itemId) {
		// TODO Auto-generated method stub
		ArrayList<Review> reviewList = new ArrayList<Review>();
		String sql="Select * from tblReviews where item_id=? and classify='Non-spam'";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Review review = new Review();
				review.setId(rs.getInt(1));
				review.setReviewID(rs.getString(2));
				review.setItemID(rs.getString(3));
				review.setCustomerName(rs.getString(4));
				review.setCustomerID(rs.getString(5));
				review.setTitle(rs.getString(6));
				review.setRating(rs.getDouble(7));
				review.setFullRating(rs.getDouble(8));
				review.setHelpfulVotes(rs.getInt(9));
				review.setTotalVotes(rs.getInt(10));
				review.setVerifiedPurchase(rs.getString(11));
				review.setRealName(rs.getString(12));
				review.setReviewDate(rs.getString(13));				
				review.setContent(rs.getString(14));	
				reviewList.add(review);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviewList;
	}

	@Override
	public User selectUser(int userId) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "Select * from tblUser where userid="+userId;
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setSecQuestion(rs.getString(10));
				user.setSecAnswer(rs.getString(11));
				user.setIpAddress(rs.getString(12));
				user.setRegTime(rs.getTimestamp(13));
				user.setProfilePic(rs.getBinaryStream(14));				
				user.setProfileName(rs.getString(15));
				user.setStatus(rs.getString(16));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean updateProfile(InputStream profilePic, String profileName,
			int userId, String fname, String lname, String address,
			String contact) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql = "Update tblUser set profilepic=?, profilename=?, firstname=?,"
				+"lastname=?, address=?, contact=? where userid=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setBlob(1, profilePic);
			pstmt.setString(2, profileName);
			pstmt.setString(3, fname);
			pstmt.setString(4, lname);
			pstmt.setString(5, address);
			pstmt.setString(6, contact);
			pstmt.setInt(7, userId);
			int index=pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

}
