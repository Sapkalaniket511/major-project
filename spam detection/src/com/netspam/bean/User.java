package com.netspam.bean;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

public class User {
	private int userId;
	private String fname;
	private String lname;
	private String email;
	private String password;
	private Date dob;
	private String gender;
	private String address;
	private String contact;
	private String secQuestion;
	private String secAnswer;
	private InputStream profilePic;
	private String profileName;
	private String status;
	private String ipAddress;
	private Timestamp regTime;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public InputStream getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(InputStream profilePic) {
		this.profilePic = profilePic;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getSecQuestion() {
		return secQuestion;
	}
	public void setSecQuestion(String secQuestion) {
		this.secQuestion = secQuestion;
	}
	public String getSecAnswer() {
		return secAnswer;
	}
	public void setSecAnswer(String secAnswer) {
		this.secAnswer = secAnswer;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Timestamp getRegTime() {
		return regTime;
	}
	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}
	
}
