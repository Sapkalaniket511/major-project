package com.netspam.services;

import java.util.ArrayList;

import com.netspam.bean.Admin;
import com.netspam.bean.Product;
import com.netspam.dao.AdminDao;
import com.netspam.dao.AdminDaoImpl;
import com.netspam.model.Review;


public class AdminServiceImpl implements AdminService{
	AdminDao adminDao = new AdminDaoImpl();
	@Override
	public Admin selectAdmin(String email, String password) {
		// TODO Auto-generated method stub
		
		return adminDao.selectAdmin(email, password);
	}

	@Override
	public boolean updateAdmin(String oldPassword, String newPassword,String email) {
		// TODO Auto-generated method stub
		
		return adminDao.updateAdmin(oldPassword, newPassword, email);
	}

	@Override
	public boolean addProductList(ArrayList<Product> productList) {
		// TODO Auto-generated method stub
		return adminDao.addProductList(productList);
	}

	@Override
	public ArrayList<Product> selectProductList() {
		// TODO Auto-generated method stub
		return adminDao.selectProductList();
	}

	@Override
	public ArrayList<Review> addReviewList(ArrayList<Review> reviewList) {
		// TODO Auto-generated method stub
		return adminDao.addReviewList(reviewList);
	}

	@Override
	public ArrayList<String> selectDupUser(String itemID) {
		// TODO Auto-generated method stub
		return adminDao.selectDupUser(itemID);
	}

	@Override
	public ArrayList<Review> selectDupUser1(String cust_name, String item_id) {
		// TODO Auto-generated method stub
		return adminDao.selectDupUser1(cust_name,item_id);
	}

	@Override
	public ArrayList<String> selectDupDate(String string) {
		// TODO Auto-generated method stub
		return adminDao.selectDupDate(string);
	}

	@Override
	public ArrayList<Review> selectReviews(String string,String itemID) {
		// TODO Auto-generated method stub
		return adminDao.selectReviews(string,itemID);
	}

	@Override
	public boolean updateReviews(ArrayList<Review> reviewList) {
		// TODO Auto-generated method stub
		return adminDao.updateReviews(reviewList);
	}

	
	
}
