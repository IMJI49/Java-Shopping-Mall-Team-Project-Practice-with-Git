package com.shoppingmall.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {


	private static final long serialVersionUID = 1L;
	private String itemID;
	private String name;
	private String category;
	private int price;
	private int quantity;
	private String description;
	private static int idNum = 1;
//	private double rating;
	private ArrayList<Double> rating;
	private ArrayList<String> review;
	private int sellCount;


	public Item(String name, String category, int price, int quantity, String description) {
		itemID = "P" + String.format("%04d", idNum);
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		idNum++;
		rating = new ArrayList<Double>();
		review = new ArrayList<String>();
	}
	public void addRating(double rate) {
		rating.add(rate);
	}
	public double averageReviewRating() {
		double sumRate = 0;
		for (Double rate : rating) {
			sumRate += rate;
		}
		return sumRate/rating.size();
	}

	public ArrayList<Double> getRating() {
		return rating;
	}

	public ArrayList<String> getReview() {
		return review;
	}

	public void addReviewing(String review) {
		this.review.add(review);
	}

	public String getName() {
		return name;
	}
	public int getSellCount() {
		return sellCount;
	}


	public String getItemID() {
		return itemID;
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
