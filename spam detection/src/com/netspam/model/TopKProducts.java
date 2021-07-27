package com.netspam.model;

import java.util.Comparator;

import com.netspam.bean.Product;


public class TopKProducts implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		 if((double)o1.getRating() < (double)o2.getRating()){
	            return 1;
	        } else if((double)o1.getRating() > (double)o2.getRating()) {
	            return -1;
	        }	else
	        	return 0;
	}
	
} 