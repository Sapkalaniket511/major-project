package com.netspam.services;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.netspam.bean.Product;
import com.netspam.bean.User;
import com.netspam.dao.UserDao;
import com.netspam.dao.UserDaoImpl;
import com.netspam.model.Review;


public class UserServiceImpl implements UserService {
	UserDao userDao =new UserDaoImpl();
	@Override
	public boolean createUser(User user) {
		// TODO Auto-generated method stub		
		return userDao.createUser(user);		
	}

	@Override
	public boolean isAlreadyAvailable(User user) {
		// TODO Auto-generated method stub	
		return userDao.isAlreadyAvailable(user);
	}

	@Override
	public User selectUser(String email, String password) {
		// TODO Auto-generated method stub
		
		return userDao.selectUser(email, password);
	}

	@Override
	public User selectUser(String email) {
		// TODO Auto-generated method stub
		
		return userDao.selectUser(email);
	}

	@Override
	public boolean updateUSer(String oldPassword, String newPassword,
			String email) {
		// TODO Auto-generated method stub
		
		return userDao.updateUser(oldPassword,newPassword,email);
	}

	@Override
	public ResultSet selectUser() {
		// TODO Auto-generated method stub
		return userDao.selectUser();
	}

	@Override
	public boolean updateUser(int userId, String status) {
		// TODO Auto-generated method stub
		return userDao.updateUser(userId, status);
	}

	@Override
	public ArrayList<Product> selectProductList() {
		// TODO Auto-generated method stub
		return userDao.selectProductList();
	}

	@Override
	public ArrayList<Product> selectProductRecords(String product,
			String category, String sort, double price) {
		// TODO Auto-generated method stub
		return userDao.selectProductRecords(product,category,sort,price);
	}

	@Override
	public ArrayList<Product> selectProducts(String product, String category) {
		// TODO Auto-generated method stub
		return userDao.selectProducts(product, category);
	}

	@Override
	public ArrayList<Review> selectReviews(String itemId) {
		// TODO Auto-generated method stub
		return userDao.selectReviews(itemId);
	}

	@Override
	public User selectUser(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectUser(userId);
	}

	@Override
	public boolean updateProfile(InputStream profilePic, String profileName,
			int userId, String fname, String lname, String address,
			String contact) {
		// TODO Auto-generated method stub
		return userDao.updateProfile(profilePic, profileName,
				userId, fname, lname, address, contact);
	}

	
}
