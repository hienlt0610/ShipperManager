package edu.hutech.shippermanager.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FMessage{

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("userid")
	@Expose
	private String userid;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}