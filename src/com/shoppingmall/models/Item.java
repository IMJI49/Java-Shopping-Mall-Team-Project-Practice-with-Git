package com.shoppingmall.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemID;
	private String name;
	private String category;
	private int price;
	private int quantity;
	private String description;
	private static int idNum = 1;
	private double rating;
	private ArrayList<String> reviewing;
	private int reviewCount = 0;
//	"P"+String.format("%4d", idNum);

	public int getReviewCount() {
		return reviewCount;
	}

	public String getItemID() {
		return itemID;
	}

	public Item(String name, String category, int price, int quantity, String description) {
		itemID = "P" + String.format("%04d", idNum);
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		idNum++;
		rating = 0.0;
		reviewing = new ArrayList<String>();
	}
	
	public void addReview(double newRating) {
		double nowRating = getRating()*getReviewCount();
		reviewCount++;
		rating = (nowRating+newRating)/(double)reviewCount;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rate) {
		this.rating = rate;
	}

	public ArrayList<String> getReviewing() {
		return reviewing;
	}

	public void setReviewing(ArrayList<String> reviewing) {
		this.reviewing = reviewing;
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

	public String getDescription() {
		return description;
	}

	public boolean productStock(int quantity) {
		this.quantity += quantity;
		return true;
	}

	public int discount(double offRate) {
		this.price *= (1-offRate);
		return this.price;
	}

	@Override
	public String toString() {
		return String.format("%s", name);
	}

}
