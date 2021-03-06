package com.redvelvet.session;

import java.util.ArrayList;
import java.util.List;

import com.redvelvet.model.CartItem;
import com.redvelvet.model.GupshupObject;
import com.redvelvet.model.MenuItem;
import com.redvelvet.model.Size;

public class SessionData {

	private CartItem cartItem;
	private int itemQuantity;
	private int remQuantity;
	private String orderStatus;
	private String itemStatus;
	private GupshupObject gupshupObject;
	List<CartItem> orderList = new ArrayList<CartItem>();
	private ArrayList<Size> sizesList = new ArrayList<Size>();
	private int sizeCounter;

	public SessionData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SessionData(CartItem cartItem, int itemQuantity, int remQuantity, String orderStatus, String itemStatus,
			GupshupObject gupshupObject) {
		super();
		this.cartItem = cartItem;
		this.itemQuantity = itemQuantity;
		this.remQuantity = remQuantity;
		this.orderStatus = orderStatus;
		this.itemStatus = itemStatus;
		this.gupshupObject = gupshupObject;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public int getRemQuantity() {
		return remQuantity;
	}

	public void setRemQuantity(int remQuantity) {
		this.remQuantity = remQuantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public GupshupObject getGupshupObject() {
		return gupshupObject;
	}

	public void setGupshupObject(GupshupObject gupshupObject) {
		this.gupshupObject = gupshupObject;
	}

	public List<CartItem> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CartItem> orderList) {
		this.orderList = orderList;
	}

	public ArrayList<Size> getSizesList() {
		return sizesList;
	}

	public void setSizesList(ArrayList<Size> sizesList) {
		this.sizesList = sizesList;
	}

	public int getSizeCounter() {
		return sizeCounter;
	}

	public void setSizeCounter(int sizeCounter) {
		this.sizeCounter = sizeCounter;
	}

	public void addItemToCart(CartItem cartItem) {
		orderList.add(cartItem);
	}

	public CartItem getCartItem(MenuItem item) {
		CartItem result = null;
		for (CartItem order : orderList) {
			if (order.getMenuItem().getName().equals(item.getName())) {
				result = order;
				break;
			}
		}
		return result;
	}

	public void resetSessionData() {
		this.cartItem = new CartItem();
		this.itemQuantity = 0;
		this.remQuantity = 0;
		this.orderStatus = "";
		this.itemStatus = "";
		this.gupshupObject = new GupshupObject();
	}

	public void initialzeAfterItemAddition() {

		
		this.sizeCounter = 0 ;
		this.cartItem = new CartItem();
		this.itemQuantity = 0;
		this.remQuantity = 0;
		this.sizesList.clear();
		
	}

	public void removeOrderFromList(String itemName) {
		int index = 0;
		for (CartItem order : orderList) {
			if (order.getMenuItem().getName().equals(itemName)) {
				orderList.remove(index);
				break;
			}
			index++;
		}
	}

	public void removeOrderFromListUsingIndex(int index) {
		orderList.remove(index);
	}

	public void clearOrderList() {
		orderList.clear();
	}

	@Override
	public String toString() {
		return "SessionData [cartItem=" + cartItem + ", itemQuantity=" + itemQuantity + ", remQuantity=" + remQuantity
				+ ", orderStatus=" + orderStatus + ", itemStatus=" + itemStatus + ", gupshupObject=" + gupshupObject
				+ ", orderList=" + orderList + ", sizesList=" + sizesList + ", sizeCounter=" + sizeCounter + "]";
	}

}
