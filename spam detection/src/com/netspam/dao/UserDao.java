package com.netspam.dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.netspam.bean.Product;
import com.netspam.bean.User;
import com.netspam.model.Review;

public interface UserDao {
	boolean createUser(User user);
	boolean isAlreadyAvailable(User user);
	User selectUser(String email,String password);
	User selectUser(String email);
	boolean updateUser(String oldPassword, String newPassword, String email);
	ResultSet selectUser();
	boolean updateUser(int userId, String status);
	ArrayList<Product> selectProductList();
	ArrayList<Product> selectProductRecords(String product, String category,
			String sort, double price);
	ArrayList<Product> selectProducts(String product, String category);
	ArrayList<Review> selectReviews(String itemId);
	User selectUser(int userId);
	boolean updateProfile(InputStream profilePic, String profileName,
			int userId, String fname, String lname, String address,
			String contact);

}
