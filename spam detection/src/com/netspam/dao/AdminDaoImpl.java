package com.netspam.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.netspam.bean.Admin;
import com.netspam.bean.Product;
import com.netspam.connection.ConnectionUtils;
import com.netspam.model.Review;


public class AdminDaoImpl implements AdminDao {

	@Override
	public Admin selectAdmin(String email, String password) {
		// TODO Auto-generated method stub
		Admin admin = new Admin();
		String sql = "Select * from tblAdmin where emailid ='"+email.toLowerCase()+"' and password='"+password+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				
				admin.setEmail(rs.getString(2));
				admin.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public boolean updateAdmin(String oldPassword, String newPassword,String email) {
		// TODO Auto-generated method stub
		boolean flag=false;
		
		String sql = "Update tblAdmin set password=? where password=? and emailid=?";
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
	public boolean addProductList(ArrayList<Product> productList) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql1 = "DROP TABLE IF EXISTS tblproduct";
		try {
			PreparedStatement pstmt1 = ConnectionUtils.getConnection().prepareStatement(sql1);
			boolean f1=pstmt1.execute(sql1);
			if(f1)
				System.out.println("Deleted....");
			pstmt1.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql2 = "CREATE TABLE IF Not Exists tblproduct (id int(10) unsigned NOT NULL AUTO_INCREMENT,"
				+" product varchar(100) NOT NULL, item_id varchar(45) NOT NULL, item_name varchar(255) NOT NULL,"
				+" price double NOT NULL, offer double NOT NULL, discount int(10) unsigned NOT NULL, rating double NOT NULL,"
				+" reviews int(10) unsigned NOT NULL, category varchar(255) NOT NULL, img_url varchar(1000) NOT NULL,"
				+" PRIMARY KEY (id))";
		try {
			PreparedStatement pstmt2 = ConnectionUtils.getConnection().prepareStatement(sql2);
			boolean f2 = pstmt2.execute();
			if(f2){
				System.out.println("Created.....");
			}
			pstmt2.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int count =0;
		for(int i=0;i<productList.size();i++){
			String sql ="Insert Into tblProduct Values(?,?,?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setInt(1, productList.get(i).getId());
				pstmt.setString(2, productList.get(i).getProduct());
				pstmt.setString(3, productList.get(i).getItem_id());
				pstmt.setString(4, productList.get(i).getItem_name());
				pstmt.setDouble(5, productList.get(i).getPrice());
				pstmt.setDouble(6, productList.get(i).getOffer());
				pstmt.setInt(7, productList.get(i).getDiscount());
				pstmt.setDouble(8, productList.get(i).getRating());
				pstmt.setInt(9, productList.get(i).getReviews());
				pstmt.setString(10, productList.get(i).getCategory());
				pstmt.setString(11, productList.get(i).getImg_url());
				int index=pstmt.executeUpdate();
				if(index>0){
					count++;
				}
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count==productList.size()){
			flag=true;
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
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productList;
	}

	@Override
	public ArrayList<Review> addReviewList(ArrayList<Review> reviewList) {
		// TODO Auto-generated method stub
		boolean flag =false;
		ArrayList<Review> reviewList1 = new ArrayList<Review>();
		int count =0;
		for(int i=0;i<reviewList.size();i++){
			String sql ="Insert Into tblReviews(id, review_id, item_id, customer_name, customerid, title, rating, fullrating, helpfulvotes, totalvotes, verified_purchase, real_name, review_date, content) Values(?,?,?,?,?,?,?,?, ?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setInt(1, 0);
				pstmt.setString(2, reviewList.get(i).getReviewID());
				pstmt.setString(3, reviewList.get(i).getItemID());
				pstmt.setString(4, reviewList.get(i).getCustomerName());
				pstmt.setString(5, reviewList.get(i).getCustomerID());
				pstmt.setString(6, reviewList.get(i).getTitle());
				pstmt.setDouble(7, reviewList.get(i).getRating());
				pstmt.setDouble(8, reviewList.get(i).getFullRating());
				pstmt.setInt(9, reviewList.get(i).getHelpfulVotes());
				pstmt.setInt(10, reviewList.get(i).getTotalVotes());
				pstmt.setString(11, reviewList.get(i).getVerifiedPurchase());
				pstmt.setString(12, reviewList.get(i).getRealName());
				pstmt.setString(13, reviewList.get(i).getReviewDate());
				pstmt.setString(14, reviewList.get(i).getContent());
				int index=pstmt.executeUpdate();
				if(index>0){
					count++;
				}
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String sql2="Select * from tblReviews where item_id=?";
		try {
			PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql2);
			pstmt.setString(1, reviewList.get(0).getItemID());
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
				reviewList1.add(review);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return reviewList1;		
	}
	
	@Override
	public ArrayList<String> selectDupUser(String itemId) {
		// TODO Auto-generated method stub
		ArrayList<String> dupUserList = new ArrayList<String>();
		
		String sql="select id, customer_name, count(*) from netspamproduct.tblreviews where item_id=? group by customer_name having count(*) > 1 ";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, itemId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				dupUserList.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dupUserList;
	}

	@Override
	public ArrayList<Review> selectDupUser1(String cust_name, String itemID) {
		// TODO Auto-generated method stub
		ArrayList<Review> dupUser=new ArrayList<Review>();
		String sql="select t.* from tblreviews t where t.customer_name=? and t.item_id=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, cust_name);
			pstmt.setString(2, itemID);
			ResultSet rs=pstmt.executeQuery();
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
				dupUser.add(review);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dupUser;
	}

	@Override
	public ArrayList<String> selectDupDate(String itemId) {
		// TODO Auto-generated method stub
		ArrayList<String> dateList = new ArrayList<String>();
		String sql = "select id, customer_name, review_date, count(*) from netspamproduct.tblreviews "
				+ " where item_id=? group by review_date having count(*) > 1 ";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				dateList.add(rs.getString(3));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateList;
	}

	@Override
	public ArrayList<Review> selectReviews(String string,String itemID) {
		// TODO Auto-generated method stub
		ArrayList<Review> reviewList=new ArrayList<Review>();
		String sql = "Select * from tblreviews where review_date=? and item_id=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, string);
			pstmt.setString(2, itemID);
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewList;
	}

	@Override
	public boolean updateReviews(ArrayList<Review> reviewList) {
		// TODO Auto-generated method stub
		boolean flag=false;
		int count=0;
		for(int i=0;i<reviewList.size();i++){
			String sql = "Update tblreviews Set classify=? Where id=?";
			try {
				PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
				pstmt.setString(1, reviewList.get(i).getClassify());
				pstmt.setInt(2, reviewList.get(i).getId());
				int index  = pstmt.executeUpdate();
				if(index>0){
					count++;
				}
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count==reviewList.size()){
			flag=true;
		}
		
		return flag;
	}
}
