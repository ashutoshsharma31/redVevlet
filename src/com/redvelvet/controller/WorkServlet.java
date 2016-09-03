package com.redvelvet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.redvelvet.session.Storage;
import com.redvelvet.util.BotUtils;
import com.redvelvet.util.ParseUtils;
import com.redvelvet.util.ValidateUserMessageUtils;
import com.redvelvet.session.SessionData;
import com.redvelvet.conf.MenuItems;
import com.redvelvet.conf.Quantity;
import com.redvelvet.conf.StatusMessages;
import com.redvelvet.dao.SizeDao;
import com.redvelvet.model.CartItem;
import com.redvelvet.model.GupshupObject;
import com.redvelvet.model.MenuItem;
import com.redvelvet.model.Size;

@WebServlet("/WorkServlet")
public class WorkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WorkServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Write Object for response writing
		PrintWriter writer = response.getWriter();
		String serverPath = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getServletPath().length());
		GupshupObject go = ParseUtils.parseRequestToGupsupObject(request);
		String orderStatus = StatusMessages.START;
		String itemStatus = StatusMessages.START;

		SessionData sessionData = Storage.getObject(go.getChannelId());

		// Set Session Object or Update
		if (sessionData != null) {
			orderStatus = sessionData.getOrderStatus();
			itemStatus = sessionData.getItemStatus();
		} else {
			sessionData = new SessionData();
			sessionData.setOrderStatus(orderStatus);
			sessionData.setItemStatus(itemStatus);
			Storage.addElementToMap(go.getChannelId(), sessionData);
		}

		String usermessage = go.getUserMessage();
		System.out.println("---------USER MESSAGE ----- " + usermessage);
		ArrayList<String> options = new ArrayList<String>();
		// Parse Messages
		// For the start of conversation
		if (ValidateUserMessageUtils.getEvent(usermessage).equals("START")) {
			System.out.println("************************ 1 ************************");
			String message = "What would you like to order ?";
			options = MenuItems.getStartOptions();
			String msgid = "StartMessage";
			sessionData.setOrderStatus(StatusMessages.ORDER_START);
			sessionData.setItemStatus(StatusMessages.ITEM_START);
			writer.println(BotUtils.quickReplyTest(message, options, msgid));

		}
		
		// REVIEW ORDER
		
		else if (usermessage.trim().contains("Review Order")) {
			List<CartItem> orderList = sessionData.getOrderList();
			String message = "";
			for (CartItem order : orderList) {
				System.out.println(order);
				message += order;
			}
			//String[] options = MenuItems.getMenuItems("Review Order");
			JSONObject coralObject = BotUtils.coralView(orderList, serverPath);
			System.out.println("Coral Object" + coralObject);
			writer.println(coralObject);

		}
		
		
		
		// For quantity of the product
		else if ((sessionData.getItemStatus().equals(StatusMessages.ITEM_QUANTITY)
				|| sessionData.getItemStatus().equals(StatusMessages.ITEM_SIZE)
				|| sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_SIZE)
				|| sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_SIZE_COUNTER))
				&& ParseUtils.isInteger(usermessage.trim())) {
			System.out.println("************************ 2 ************************");
			CartItem item = sessionData.getCartItem();
			System.out.println(" CART ITEM TO PROCESS is " + item);
			if (MenuItems.isSizeable(item.getMenuItem())) {
				// If the product is sizable
				System.out.println("************************ 3 ************************");
				SizeDao sizeDao = new SizeDao();
				if (sessionData.getItemStatus().equals(StatusMessages.ITEM_QUANTITY)) {
					sessionData.setRemQuantity(Integer.parseInt(usermessage.trim()));
				} else {
					sessionData.setRemQuantity(sessionData.getRemQuantity() - Integer.parseInt(usermessage.trim()));
				}

				if (Integer.parseInt(usermessage.toLowerCase().trim()) == 1
						&& !sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_QUANTITY)
						&& !sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_SIZE_COUNTER)
						&& !sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_SIZE)) {
					// if quantity choose is 1
					System.out.println("************************ 4 ************************");
					String msgid = "SIZE_SELECT_ONE_QTY";
					String message = "What size do you want that? ";
					options = sizeDao.getAllSizesForItem(item.getMenuItem());
					sessionData.setItemStatus(StatusMessages.ITEM_SINGLE_SIZE);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				} else {
					// Sizable Item quantity more than 1
					System.out.println("************************ 5 ************************");
					ArrayList<Size> sizeList = sessionData.getSizesList();
					int sizeCounter = sessionData.getSizeCounter();

					int remQty = sessionData.getRemQuantity();
					if (sizeList.isEmpty()) {
						// Fill the size list
						System.out.println("************************ 6 ************************");
						sizeList = sizeDao.getAllSizes(item.getMenuItem());
						sessionData.setSizesList(sizeList);
						remQty = Integer.parseInt(usermessage);
					}
					if (remQty == 0 || sizeCounter == sizeList.size() - 1) {
						// for last item
						System.out.println("************************ 7 ************************");
						String message = "Item added ..  Whats else ?";
						if (Integer.parseInt(usermessage) > 0) {
							CartItem finalitem = new CartItem(item.getMenuItem(), Integer.parseInt(usermessage),
									sizeList.get(sizeCounter).getDesc());
							sessionData.addItemToCart(finalitem);
						}
						String msgid = "ITEM_COMPLETED";
						sessionData.initialzeAfterItemAddition();
						options = MenuItems.getReviewOrder();
						sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
						sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
						writer.println(BotUtils.quickReplyTest(message, options, msgid));
					} else {
						// all the items except last
						System.out.println(
								"************************ 9 ************************ size counter" + sizeCounter + " ");
						options = Quantity.getQuantityFromZero(sessionData.getRemQuantity());
						System.out.println(" STATUS " + sizeCounter + " " + sessionData.getItemStatus());
						if (sizeCounter == 0
								&& !sessionData.getItemStatus().equals(StatusMessages.ITEM_MULTIPLE_SIZE_COUNTER)) {
							// FIRST COUNTER .. No need to add the item
							System.out.println("************************ 123 ************************");
							Size size = sizeList.get(sizeCounter);
							item.setSize(size.getDesc());
							// item.setQuantity(Integer.parseInt(usermessage));
							String message = "How many " + size.getDesc() + " ? ";
							String msgid = "ITEM_MULTIPLE_SIZE_COUNTER";
							// sessionData.setSizeCounter(sizeCounter+1);
							sessionData.setItemStatus(StatusMessages.ITEM_MULTIPLE_SIZE_COUNTER);
							writer.println(BotUtils.quickReplyTest(message, options, msgid));
						} else {
							System.out.println("************************ 1000 ************************");
							System.out.println(item.getMenuItem() + " " + Integer.parseInt(usermessage) + " "
									+ sizeList.get(sizeCounter).getDesc());
							if (Integer.parseInt(usermessage) > 0) {
								CartItem finalitem = new CartItem(item.getMenuItem(), Integer.parseInt(usermessage),
										sizeList.get(sizeCounter).getDesc());
								sessionData.addItemToCart(finalitem);
							}
							sessionData.setSizeCounter(sizeCounter + 1);
							String msgid = "ITEM_MULTIPLE_SIZE_COUNTER";
							String message = "How many " + sizeList.get(sizeCounter + 1).getDesc() + " ? ";
							writer.println(BotUtils.quickReplyTest(message, options, msgid));
						}

						sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);

					}
				}
			} else {
				// Product is not sizeable
				System.out.println("************************ 13 ************************");
				item.setQuantity(Integer.parseInt(usermessage.trim()));
				String message = "Item added " + item.getMenuItem().getName() + " with quantity " + item.getQuantity()
						+ "/n Whats else ?";
				String msgid = "itemaddednosize";
				options = MenuItems.getReviewOrder();
				sessionData.addItemToCart(item);
				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}

		}

		else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_SINGLE_SIZE)) {
			System.out.println("************************ 14 ************************");
			// Item added to cartList with size .. this is only for 1 item
			CartItem item = sessionData.getCartItem();
			SizeDao sizeDao = new SizeDao();
			ArrayList<String> sizeList = sizeDao.getAllSizesForItem(item.getMenuItem());
			System.out.println(sizeList);
			if (sizeList.contains(usermessage)) {
				System.out.println("************************ 15 ************************");
				System.out.println("Size Found ");
				item.setSize(usermessage);
				item.setQuantity(1);
				sessionData.addItemToCart(item);
				String message = "Item added ..  Whats else ?";
				String msgid = "ITEM_COMPLETED";
				options = MenuItems.getReviewOrder();
				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else {
				System.out.println("************************ 16 ************************");
				System.out.println("SIZE NOT IDENTIFIED ... HANDLE IT");
			}
		}

		// If the message received is a product or menu item
		else if (MenuItems.hasItem(usermessage)) {
			System.out.println("************************ 17 ************************");
			MenuItem menuItem = new MenuItem();
			menuItem = MenuItems.getMenuItems(usermessage);
			sessionData.setItemStatus(StatusMessages.ITEM_IN_PROGRESS);
			sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
			String msgid = "SetQuantity";
			if (MenuItems.checkMenuItemToOrder(menuItem)) {
				System.out.println("************************ 18 ************************");
				options = Quantity.getQuantity();
				String message = "Please specify quantity of " + usermessage;
				sessionData.setItemStatus(StatusMessages.ITEM_QUANTITY);
				sessionData.setCartItem(new CartItem(menuItem));
				writer.println(BotUtils.quickReplyTest(message, options, msgid));

			} else {
				System.out.println("************************ 19 ************************");
				// This is a parent menu
				// Will send the child product as response
				options = MenuItems.getNextOptions(menuItem);
				String message = "Which " + usermessage + " you like to order ?";
				msgid = "ParentProduct";
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}

		}
		// System.out.println(sessionData);

		System.out.println(sessionData.getSizesList());
		System.out.println(sessionData.getOrderList());

		writer.flush();
		writer.close();

	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
