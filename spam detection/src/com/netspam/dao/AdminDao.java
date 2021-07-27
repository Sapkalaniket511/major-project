package com.netspam.dao;

import java.util.ArrayList;

import com.netspam.bean.Admin;
import com.netspam.bean.Product;
import com.netspam.model.Review;

public interface AdminDao {
	Admin selectAdmin(String email, String password);

	boolean updateAdmin(String oldPassword, String newPassword, String email);

	boolean addProductList(ArrayList<Product> productList);

	ArrayList<Product> selectProductList();

	ArrayList<Review> addReviewList(ArrayList<Review> reviewList);

	ArrayList<String> selectDupUser(String itemID);

	ArrayList<Review> selectDupUser1(String cust_name, String item_id);

	ArrayList<String> selectDupDate(String string);

	ArrayList<Review> selectReviews(String string, String itemID);

	boolean updateReviews(ArrayList<Review> reviewList);
}
