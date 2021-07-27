package com.netspam.services;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.netspam.bean.Product;
import com.netspam.bean.User;
import com.netspam.model.Review;


public interface UserService {
	boolean createUser(User user);
	boolean isAlreadyAvailable(User user);
	boolean updateUSer(String oldPassword,String newPassword,String email);
	User selectUser(String email, String Password);
	User selectUser(String email);
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
