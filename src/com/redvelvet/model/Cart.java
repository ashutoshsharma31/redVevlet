package com.redvelvet.model;

import java.util.Date;
import java.util.List;

public class Cart {
	private List<CartItem> itemsToOrder;
	private float totalOrderPrice;

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(List<CartItem> itemsToOrder, float totalOrderPrice) {
		super();
		this.itemsToOrder = itemsToOrder;
		this.totalOrderPrice = totalOrderPrice;
	}

	public List<CartItem> getItemsToOrder() {
		return itemsToOrder;
	}

	public void setItemsToOrder(List<CartItem> itemsToOrder) {
		this.itemsToOrder = itemsToOrder;
	}

	public float getTotalOrderPrice() {
		return totalOrderPrice;
	}

	public void setTotalOrderPrice(float totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}

	@Override
	public String toString() {
		return "Cart [itemsToOrder=" + itemsToOrder + ", totalOrderPrice=" + totalOrderPrice + "]";
	}

}