package com.netspam.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.bean.Product;
import com.netspam.services.AdminService;
import com.netspam.services.AdminServiceImpl;
import com.oreilly.servlet.MultipartRequest;

import au.com.bytecode.opencsv.CSVReader;



/**
 * Servlet implementation class AdminUploadController
 */
@WebServlet("/AdminUploadController")
@MultipartConfig(maxFileSize=1024*1024*50)
public class AdminUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUploadController() {
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
		MultipartRequest m=new MultipartRequest(request,"E:/Shilpa/ProjectGuru/Spam/netspamproduct/WebContent/upload",1024*1024*1024);
        String csvFile = "E:/Shilpa/ProjectGuru/Spam/netspamproduct/WebContent/upload/product.csv";
        ArrayList<String> dataArray =  new ArrayList<String>(); 
        ArrayList<Product> productList = new ArrayList<Product>();
        //openCSV reader to parse the CSV file
        CSVReader reader = new CSVReader(new FileReader(csvFile));
       
        //nextLine array contains a an entire row of data,
        //Each element represents a cell in the spreadsheet.
        String[] nextLine;
        int count=0;
        //Iterate though the CSV while there are more lines to read
        while((nextLine = reader.readNext()) != null)
        {
     	   if(nextLine[0].startsWith("id")&&nextLine[1].startsWith("product")){
     		   nextLine[0]=null;
     		   nextLine[1]=null;
     		   nextLine[2]=null;
     		   nextLine[3]=null;
     		   nextLine[4]=null;
     		   nextLine[5]=null;
     		   nextLine[6]=null;
     		   nextLine[7]=null;     
     		   nextLine[8]=null;
    		   nextLine[9]=null;
    		   nextLine[10]=null;   
     	   }else{
     		   dataArray.clear();
     		      //Iterate through the elements on this line
         	   Product product = new Product();
                for(String s : nextLine)
                {	s=s.trim();
                    dataArray.add(s);
                    //System.out.println(s);
                }
                System.out.println("\t"+dataArray.size());
                if(dataArray.size()==11){
                	product.setId(Integer.parseInt(dataArray.get(0)));
                	product.setProduct(dataArray.get(1));
                	product.setItem_id(dataArray.get(2));
                	product.setItem_name(dataArray.get(3));
                	product.setPrice(Double.parseDouble(dataArray.get(4)));
                	product.setOffer(Double.parseDouble(dataArray.get(5)));
                	product.setDiscount(Integer.parseInt(dataArray.get(6).replace("%", "")));
                	product.setRating(Double.parseDouble(dataArray.get(7)));
                	product.setReviews(Integer.parseInt(dataArray.get(8)));
                	product.setCategory(dataArray.get(9));
                	product.setImg_url(dataArray.get(10));  
                	productList.add(product);
                }
     	   }
                  
        }
        reader.close();
        AdminService adminService = new AdminServiceImpl();
        if(productList!=null && productList.size()>0){
        	if(adminService.addProductList(productList)){
        		
        		System.out.print("Successfully added");
        	}
        	HttpSession session = request.getSession();
           	session.setAttribute("ProductList",productList);
           	RequestDispatcher rd = request.getRequestDispatcher("admin_listproducts.jsp");
           	rd.forward(request, response);
        }else{
        	request.setAttribute("ErrMsg","Please choose correct file");
           	RequestDispatcher rd = request.getRequestDispatcher("admin_upload.jsp");
           	rd.include(request, response);
        }
	}

}
