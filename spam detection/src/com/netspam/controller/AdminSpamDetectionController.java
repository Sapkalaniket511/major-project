package com.netspam.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.bean.Features;
import com.netspam.model.Review;
import com.netspam.services.AdminService;
import com.netspam.services.AdminServiceImpl;
import com.netspam.services.UserService;
import com.netspam.services.UserServiceImpl;

/**
 * Servlet implementation class AdminSpamDetectionController
 */
@WebServlet("/AdminSpamDetectionController")
public class AdminSpamDetectionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSpamDetectionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession();
		ArrayList<Review> reviewList = (ArrayList<Review>) session.getAttribute("ReviewList");
		ArrayList<Review> negRatio = new ArrayList<Review>();
		ArrayList<Review> bstList = new ArrayList<Review>();
		ArrayList<Review> avgSim = new ArrayList<Review>();
		ArrayList<Review> maxSim = new ArrayList<Review>();
		ArrayList<Review> devList = new ArrayList<Review>();
		ArrayList<Review> pp1List = new ArrayList<Review>();
		ArrayList<Review> resList = new ArrayList<Review>();
		ArrayList<Review> simList = new ArrayList<Review>();
		Set<Integer> spamList = new HashSet<Integer>();
		int cntBst=0,cntNeg=0,cntASim=0,cntMSim=0,cntDev=0,cntPP1=0,cntRes=0,cntEtf=0;
		HashMap<String, ArrayList<Review>> dupDateList=new HashMap<String, ArrayList<Review>>();
		
		String pronoun[]={"you","she","he","it","him","her","they","them"};
		double rating[]=new double[reviewList.size()];
		double sum=0,avg=0;
		if(reviewList!=null&&reviewList.size()>0){
			AdminService adminService = new AdminServiceImpl();
			ArrayList<String> dupUserList= adminService.selectDupUser(reviewList.get(0).getItemID());
			int count=0;
			if(dupUserList!=null&&dupUserList.size()>0){
				for(int i=0;i<dupUserList.size();i++){
					String cust_name=dupUserList.get(i);
					ArrayList<Review> dupUser=adminService.selectDupUser1(cust_name,reviewList.get(0).getItemID());
					for(int j=0;j<dupUser.size()-1;j++){
						String rdate=dupUser.get(j).getReviewDate();
						//rdate=rdate.substring(9);
						String d[]=rdate.split(" ");
						rdate=d[1]+" "+d[0]+", "+d[2];
						String rdate1=dupUser.get(j+1).getReviewDate();
						//rdate1=rdate1.substring(9);
						String d1[]=rdate1.split(" ");
						rdate1=d1[1]+" "+d1[0]+", "+d1[2];
						 try {
							 Date date = DateFormat.getDateInstance().parse(rdate);
					         System.out.println(date); 
					         Date date1 = DateFormat.getDateInstance().parse(rdate1);
					         System.out.println(date1);
					         long duration  = date.getTime() - date1.getTime();
					         long days=TimeUnit.MILLISECONDS.toDays(duration);
					         System.out.println(Math.abs(days));
					         double bst=((double)days/28);					         
					         if(bst>=0.5){
					        	 bst=1.0;	
					        	 bstList.add(dupUser.get(j));
					        	 bstList.add(dupUser.get(j+1));
					         }else{
					        	 bst=0.0;
					         }
					         System.out.println(bst);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					
				}
			}
			ArrayList<String> dateList=adminService.selectDupDate(reviewList.get(0).getItemID());
			for(int i=0;i<dateList.size();i++){
				ArrayList<Review> reviews = adminService.selectReviews(dateList.get(i),reviewList.get(0).getItemID());
				dupDateList.put(dateList.get(i), reviews);
			}
			for(int i=0;i<reviewList.size();i++){
				sum=sum+reviewList.get(i).getRating();
				rating[i]=reviewList.get(i).getRating();
			}
			avg=sum/reviewList.size();
			double min = rating[0];
	        for (double i : rating){
	            min = min < i ? min : i;
	        }
	        System.out.println("Min="+min);
			for(int i=0;i<reviewList.size();i++){
				//System.out.println(reviewList.get(i).getId());
				String result = reviewList.get(i).getSentiResult();
				if(result.equals("Negative")){
					negRatio.add(reviewList.get(i));
				}
				double score=reviewList.get(i).getSemanticScore();
				if(score>=0.7){
					maxSim.add(reviewList.get(i));
				}else if(score>=0.5){
					avgSim.add(reviewList.get(i));
				}
				double rate=(1-(reviewList.get(i).getRating()-avg)/4);
				System.out.println("Rate="+rate);
				if(rate>min){
					devList.add(reviewList.get(i));
				}
				
				String content= reviewList.get(i).getStemming();
				String words[]=content.split(" ");
				int cnt=0;
				for(int j=0;j<words.length;j++){
					for(int k=0;k<pronoun.length;k++){
						if(words[j].equalsIgnoreCase(pronoun[k])){
							cnt++;
						}
					}
				}
				if(cnt>=3){
					pp1List.add(reviewList.get(i));
				}
				String str=reviewList.get(i).getContent();
				int cnt2=0;
				if(str.contains("!")){
					for(int k=0;k<str.length();k++){
						if(str.charAt(k)=='!'){
							cnt2++;
						}
					}
					if(cnt2>=3){
						resList.add(reviewList.get(i));
					}
				}
			}
			System.out.println("bstList.size() ="+bstList.size());
			for(int i=0;i<bstList.size();i++){
				
				System.out.println(bstList.get(i).getId());
			}
			Features feature = new Features();
			feature.setBurstiness((double)bstList.size()/reviewList.size());
			System.out.println("negRatio.size() ="+negRatio.size());
			for(int i=0;i<negRatio.size();i++){
				
				System.out.println(negRatio.get(i).getId());
			}
			feature.setNegRatio((double)negRatio.size()/reviewList.size());
			System.out.println("avgSim.size() ="+avgSim.size());
			for(int i=0;i<avgSim.size();i++){
				System.out.println(avgSim.get(i).getId());
			}
			feature.setAvgSim((double)avgSim.size()/reviewList.size());
			feature.setMaxSim((double)maxSim.size()/reviewList.size());
			simList.addAll(avgSim);
			simList.addAll(maxSim);
			System.out.println("maxSim.size() ="+maxSim.size());
			for(int i=0;i<maxSim.size();i++){
				System.out.println(maxSim.get(i).getId());
			}
			System.out.println("devList.size() ="+devList.size());
			for(int i=0;i<devList.size();i++){
				System.out.println(devList.get(i).getId());
			}
			feature.setDeviation((double)devList.size()/reviewList.size());
			System.out.println("pp1List.size() ="+pp1List.size());
			for(int i=0;i<pp1List.size();i++){
				System.out.println(pp1List.get(i).getId());
			}
			feature.setPp1((double)pp1List.size()/reviewList.size());
			System.out.println("resList.size() ="+resList.size());
			for(int i=0;i<resList.size();i++){
				System.out.println(resList.get(i).getId());
			}
			feature.setRes((double)resList.size()/reviewList.size());
			for(int i=0;i<negRatio.size();i++){
				for(int j=0;j<simList.size();j++){
					for(Entry<String, ArrayList<Review>> entry: dupDateList.entrySet()){
						String key = entry.getKey();
						ArrayList<Review> reviews=dupDateList.get(key);
						for(int d=0;d<reviews.size();d++){
							if(negRatio.get(i).getId()==reviews.get(d).getId()
									&& simList.get(j).getId()==reviews.get(d).getId()){
								count++;
								spamList.add(reviews.get(d).getId());
							}
						}						
					}
				}
			}
			for(int i=0;i<negRatio.size();i++){
				for(int j=0;j<simList.size();j++){
					for(int k=0;k<devList.size();k++){
						if(negRatio.get(i).getId()==simList.get(j).getId()
								&& negRatio.get(i).getId()==devList.get(k).getId()){
							count++;
							spamList.add(negRatio.get(i).getId());
						}
					}
				}
			}
			for(int i=0;i<negRatio.size();i++){
				for(int j=0;j<simList.size();j++){
					for(int k=0;k<bstList.size();k++){
						if(negRatio.get(i).getId()==simList.get(j).getId()
								&& negRatio.get(i).getId()==bstList.get(k).getId()){
							count++;
							spamList.add(negRatio.get(i).getId());
						}
					}
				}
			}
			for(int i=0;i<devList.size();i++){
				for(int j=0;j<pp1List.size();j++){
					for(int k=0;k<bstList.size();k++){
						if(devList.get(i).getId()==pp1List.get(j).getId()
								&& devList.get(i).getId()==bstList.get(k).getId()){
							count++;
							spamList.add(devList.get(i).getId());
						}
					}
				}
			}
			for(int i=0;i<negRatio.size();i++){
				for(int j=0;j<pp1List.size();j++){
					for(int k=0;k<resList.size();k++){
						if(negRatio.get(i).getId()==pp1List.get(j).getId()								
								|| negRatio.get(i).getId()==resList.get(k).getId()){
							count++;
							spamList.add(negRatio.get(i).getId());
						}
					}
				}
			}
			ArrayList<Integer> spamReviews = new ArrayList<Integer>();
			spamReviews.addAll(spamList);
			for(int i=0;i<reviewList.size();i++){
				reviewList.get(i).setClassify("Non-spam");
				for(int j=0;j<spamReviews.size();j++){
					if(reviewList.get(i).getId()==spamReviews.get(j)){
						reviewList.get(i).setClassify("Spam");
						break;
					}
				}
			}
			boolean flag=adminService.updateReviews(reviewList);
			session.setAttribute("ReviewList", reviewList);
			session.setAttribute("Features", feature);
			session.setAttribute("SpamReviews", spamReviews.size());
			session.setAttribute("NonSpamReviews", (reviewList.size()-spamReviews.size()));
			RequestDispatcher rd=request.getRequestDispatcher("admin_result.jsp");
			rd.forward(request, response);
			
			
		}
	}
	
}
