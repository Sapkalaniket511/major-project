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

import com.netspam.model.Porter;
import com.netspam.model.Review;
import com.netspam.model.lsa.SimilarityException;
import com.netspam.model.lsa.TextSimilarityMeasure;
import com.netspam.model.lsa.WordNGramJaccardMeasure;

/**
 * Servlet implementation class ReviewSimilarityController
 */
@WebServlet("/ReviewSimilarityController")
public class ReviewSimilarityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewSimilarityController() {
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
		String words="";
		String word[]=null;		
		String sentence="";
		if(reviewList!=null&&reviewList.size()>0){
			for(int i=0;i<reviewList.size();i++){
				sentence="";
				words=reviewList.get(i).getContent();
				words=words.toLowerCase();
				words=words.replace(".","");
				words=words.replace(",","");
				words=words.replace("!","");
				//words=words.replace(":d","");
				words=words.replace(":)","");
				words=words.replace(":","");
				words=words.replace(";","");
				words=words.replace("?","");
				words=words.replace("_","");
				words=words.replace("*","");
				words=words.replace("^","");
				words=words.replace("<3","");
				word=words.split("\\s+");
				for(int j=0;j<word.length;j++){
					word[j]=removeDup(word[j]);
				}
				for(int j=0;j<word.length;j++){
					Porter porter = new Porter();
					if(!word[j].equals("")){
					word[j]=porter.stripSuffixes(word[j]);
					}
					sentence = sentence + word[j]+" ";
				}
				reviewList.get(i).setStemming(sentence);
			}
		}
		if(reviewList!=null&&reviewList.size()>0){
			for(int i=0;i<reviewList.size()-1;i++){
				String content1=reviewList.get(i).getStemming();
				String[] tokens1 = content1.split(" ");
				double score=0;
				for(int j=0;j<reviewList.size();j++){
					if(i!=j){				
					String content2=reviewList.get(j).getStemming();
					TextSimilarityMeasure measure = new WordNGramJaccardMeasure(3);    // Use word trigrams
					
					String[] tokens2 = content2.split(" ");					
					try {
						score = score+measure.getSimilarity(tokens1, tokens2);
						System.out.println("Similarity: " + score);
					} catch (SimilarityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
				reviewList.get(i).setSemanticScore(score);
			}
		}
		session.setAttribute("ReviewList", reviewList);
		RequestDispatcher rd = request.getRequestDispatcher("admin_similarity.jsp");
		rd.forward(request, response);
	}
	public  String removeDup(String str){
		str = str + " "; // Adding a space at the end of the word
        int l=str.length(); // Finding the length of the word
		String ans="";
		char ch1,ch2;
				 
        for(int i=0; i<l-1; i++)
        {
            ch1=str.charAt(i); // Extracting the first character
            ch2=str.charAt(i+1); // Extracting the next character
 
// Adding the first extracted character to the result if the current and the next characters are different
            int count= countRun(str, ch1);
            if(count==2){
            	ans = ans + ch1;
            }else if(ch1!=ch2){            
            	ans = ans + ch1;
            }            
        }
	    return ans;
    }
	public static int countRun( String s, char c )
	{
	    int counter = 0;
	    for( int i = 0; i < s.length(); i++)
	    {
	      if( s.charAt(i) == c )  counter++;
	      else if(counter>0) break;
	    }
	    return counter;
	}
}
