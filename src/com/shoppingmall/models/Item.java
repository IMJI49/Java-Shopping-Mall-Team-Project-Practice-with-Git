package com.shoppingmall.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {



	private static final long serialVersionUID = 1L;
	private String itemID;
	private String name;
	private String category;
	private int price;
	private int quantity;
	private String prodDesc;
	private static int idNum = 1;
	
	private List<String> review;
	private List<String> reviewerIds;
	private List<Integer> ratings;		// null값 들어갈 수 있음




	public String getItemID() {
		return itemID;
	}

	public Item(String name, String category, int price, int quantity, String prodDesc) {
		itemID = "P" + String.format("%40d", idNum);
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.prodDesc = prodDesc;
		idNum++;
		
	    this.review = new ArrayList<>();
	    this.reviewerIds = new ArrayList<>();
	    this.ratings = new ArrayList<>();
	}

	
	public List<String> getReview() {
		if (review == null) review = new ArrayList<>();
		return review;
	}

	public List<String> getReviewerIds() {
	    if (reviewerIds == null) reviewerIds = new ArrayList<>();
		return reviewerIds;
	}

	public List<Integer> getRatings() {
	    if (ratings == null) ratings = new ArrayList<>();
		return ratings;
	}
	
	
	
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getCategory() {
		return category;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setReview(List<String> review) {
		this.review = review;
	}

	public void setReviewerIds(List<String> reviewerIds) {
		this.reviewerIds = reviewerIds;
	}

	public void setRatings(List<Integer> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {

		return "Item [itemID=" + itemID + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", quantity=" + quantity + ", prodDesc=" + prodDesc + ", "
						+ "리뷰수=" + (review == null? 0: review.size()) + "]";
	}

	// 테스트용 
	public String setItemID(String itemID) {
		
		return this.itemID = itemID;
	}
}