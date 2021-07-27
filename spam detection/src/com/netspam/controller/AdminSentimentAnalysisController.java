package com.netspam.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netspam.model.NLP;
import com.netspam.model.Review;

/**
 * Servlet implementation class AdminSentimentAnalysisController
 */
@WebServlet("/AdminSentimentAnalysisController")
public class AdminSentimentAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSentimentAnalysisController() {
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
		HttpSession session = request.getSession();
		ArrayList<Review> reviewList = (ArrayList<Review>)session.getAttribute("ReviewList");
		if(reviewList!=null&&reviewList.size()>0){			
			NLP.init();
			for(int i=0;i<reviewList.size();i++){
				double polarity=0.0;
				String result="";
				System.out.println(reviewList.get(i).getContent() + " : " + NLP.findSentiment(reviewList.get(i).getContent()));
				int mainSentiment=NLP.findSentiment(reviewList.get(i).getContent());
				
				if((mainSentiment==0 || mainSentiment==1)){
					result= "Negative";
					
					polarity=0.0;
				}
			    else if(mainSentiment==2){
			        result= "Neutral";
			        
			        polarity=0.7;
			    }else if((mainSentiment==3 || mainSentiment==4||mainSentiment==5)){
			        result= "Positive";
			        
			        polarity=1.0;
				}			   
				reviewList.get(i).setSentiScore(polarity);
				reviewList.get(i).setSentiResult(result);
				//reviewList.get(i).setQualityScore(mainSentiment);
			}	
			session.setAttribute("ReviewList", reviewList);
			RequestDispatcher rd = request.getRequestDispatcher("admin_senti.jsp");
			rd.forward(request, response);

		}
	}

}
