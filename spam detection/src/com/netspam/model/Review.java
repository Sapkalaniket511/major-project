package com.netspam.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Review {
	/**
	 * All the pieces in a review on Amazon.com
	 * 
	 * @param aitemID
	 *            the unique item ID of a product
	 * @param areviewID
	 *            the unique review ID
	 * @param acustomerName
	 *            displayed name of a customer
	 * @param acustomerID
	 *            unique customer ID
	 * @param atitle
	 *            title of the review
	 * @param arating
	 *            star rating out of 5 given by the customer
	 * @param afullRating
	 *            max rating can be given (5 for now)
	 * @param ahelpfulVotes
	 *            number of readers who rated the review as helpful
	 * @param atotalVotes
	 *            total number of readers who voted usefulness of the review
	 * @param verifiedornot
	 *            whether the review is from a verified purchase
	 * @param realnameornot
	 *            whether the customer is using his real name when writing the
	 *            review (obsolete, amazon no longer displays the badge)
	 * @param aReviewDate
	 *            date of the review
	 * @param acontent
	 *            textual content of the review
	 */
	public Review(String aitemID, String areviewID, String acustomerName,
			String acustomerID, String atitle, int arating, int afullRating,
			int ahelpfulVotes, int atotalVotes, String verifiedornot,
			String realnameornot, String aReviewDate, String acontent) {
		itemID = aitemID;
		reviewID = areviewID;
		customerName = acustomerName;
		customerID = acustomerID;
		title = atitle;
		rating = arating;
		fullRating = afullRating;
		helpfulVotes = ahelpfulVotes;
		totalVotes = atotalVotes;
		verifiedPurchase = verifiedornot;
		realName = realnameornot;
		reviewDate = aReviewDate;
		content = acontent;
	}
	public Review(){
		
	}
	/**
	 * Write single review into a Sqlite database Relatively inefficient, should
	 * use {@link Item#writeReviewsToDatabase(String)} method to write all reviews for an
	 * item at the same time instead
	 * 
	 * @param database
	 *            path of database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	/*public void writeDatabase(String database) throws ClassNotFoundException,
			SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager
				.getConnection("jdbc:sqlite:" + database);
		PreparedStatement insertreview = conn
				.prepareStatement("insert into review (reviewid, title, content) values (?1, ?2, ?3);");
		PreparedStatement insertreviewinfo = conn
				.prepareStatement("insert into reviewinfo (addedDate, reviewDate, realName, verifiedPurchase, totalVotes, "
						+ "helpfulVotes, fullRating, rating, title, customerID, customerName, reviewID, itemID) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13);");
		insertreview.setString(1, reviewID);
		insertreview.setString(2, title);
		insertreview.setString(3, content);
		insertreview.addBatch();
		insertreview.executeBatch();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String nowtime = dateFormat.format(date);
		insertreviewinfo.setString(1, nowtime);
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
		String reviewdatestring = dateFormat2.format(reviewDate);
		insertreviewinfo.setString(2, reviewdatestring);
		insertreviewinfo.setString(3, String.valueOf(realName));
		insertreviewinfo.setString(4, String.valueOf(verifiedPurchase));
		insertreviewinfo.setInt(5, totalVotes);
		insertreviewinfo.setInt(6, helpfulVotes);
		insertreviewinfo.setInt(7, (int) fullRating);
		insertreviewinfo.setInt(8, (int) rating);
		insertreviewinfo.setString(9, title);
		insertreviewinfo.setString(10, customerID);
		insertreviewinfo.setString(11, customerName);
		insertreviewinfo.setString(12, reviewID);
		insertreviewinfo.setString(13, itemID);
		insertreviewinfo.addBatch();
		insertreviewinfo.executeBatch();
		conn.close();

	}*/

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/*public void updateReview(String aitemid, String areviewid,
			String acustomername, String acustomerID, String atitle,
			double arating, double afullRating, int ahelpfulVotes,
			int atotalVotes, boolean verified, String realname,
			Date areviewDate, String acontent) {
		this.itemID = aitemid;
		this.reviewID = areviewid;
		this.customerName = acustomername;
		this.customerID = acustomerID;
		this.title = atitle;
		this.rating = arating;
		this.fullRating = afullRating;
		this.helpfulVotes = ahelpfulVotes;
		this.totalVotes = atotalVotes;
		this.verifiedPurchase = verified;
		this.realName = realname;
		this.reviewDate = areviewDate;
		this.content = acontent;
	}
*/
	int id;
	String itemID;
	String reviewID;
	String customerName;
	String customerID;
	String title;
	double rating;
	double fullRating;
	int helpfulVotes;
	int totalVotes;
	String verifiedPurchase;
	String realName;
	String reviewDate;
	String content;
	double sentiScore;
	String sentiResult;
	String stemming;
	double semanticScore;
	String semResult;
	String classify;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemID() {
		return itemID;
	}
	
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getReviewID() {
		return reviewID;
	}

	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getFullRating() {
		return fullRating;
	}

	public void setFullRating(double fullRating) {
		this.fullRating = fullRating;
	}

	public int getHelpfulVotes() {
		return helpfulVotes;
	}

	public void setHelpfulVotes(int helpfulVotes) {
		this.helpfulVotes = helpfulVotes;
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	public void setVerifiedPurchase(String verifiedPurchase) {
		this.verifiedPurchase = verifiedPurchase;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public double getSentiScore() {
		return sentiScore;
	}
	public void setSentiScore(double sentiScore) {
		this.sentiScore = sentiScore;
	}
	public String getSentiResult() {
		return sentiResult;
	}
	public void setSentiResult(String sentiResult) {
		this.sentiResult = sentiResult;
	}
	public String getStemming() {
		return stemming;
	}
	public void setStemming(String stemming) {
		this.stemming = stemming;
	}
	public double getSemanticScore() {
		return semanticScore;
	}
	public void setSemanticScore(double semanticScore) {
		this.semanticScore = semanticScore;
	}
	public String getSemResult() {
		return semResult;
	}
	public void setSemResult(String semResult) {
		this.semResult = semResult;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getVerifiedPurchase() {
		return verifiedPurchase;
	}
	
}
