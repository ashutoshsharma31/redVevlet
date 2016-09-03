package com.redvelvet.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.redvelvet.model.CartItem;


public class BotUtils {

	public static JSONObject quickReplyTest(String message, ArrayList<String> options, String msgid) {
		// String[] str = { "Red", "Green", "Yellow", "Blue" };
		JSONObject startMessage = new JSONObject();
		startMessage.put("type", "quick_reply")
				.put("content", new JSONObject().put("type", "text").put("text", message)).put("options", options)
				.put("msgid", msgid);

		return startMessage;

	}

	public static JSONObject coralView(List<CartItem> orderList, String serverPath) {

		/*
		 * { "type": "catalogue", "msgid": "cat_212", "items": [{ "title":
		 * "White T Shirt", "subtitle":
		 * "Soft cotton t-shirt \nXs, S, M, L \n$10", "imgurl":
		 * "http://petersapparel.parseapp.com/img/item100-thumb.png", "options":
		 * [ { "type": "url", "title": "View Details", "url":
		 * "http://petersapparel.parseapp.com/img/item100-thumb.png" }, {
		 * "type": "text", "title": "Buy" }
		 * 
		 * ] }, { "title": "Grey T Shirt", "subtitle":
		 * "Soft cotton t-shirt \nXs, S, M, L \n$12", "imgurl":
		 * "http://petersapparel.parseapp.com/img/item101-thumb.png", "options":
		 * [ { "type": "url", "title": "View Details", "url":
		 * "http://petersapparel.parseapp.com/img/item101-thumb.png" }, {
		 * "type": "text", "title": "Buy" }] }] }
		 */

		// List arrOptions = Arrays.asList(options);
		JSONObject my = new JSONObject();
		my.put("type", "catalogue").put("msgid", "coralview");
		// .put("content", new JSONObject().put("type", "text").put("text",
		// message))
		// .put("options", arrOptions).put("msgid", msgid);
		ArrayList<JSONObject> itemObject = new ArrayList<JSONObject>();
		for (CartItem order : orderList) {
			ArrayList<JSONObject> options = new ArrayList<JSONObject>();
			options.add(new JSONObject().put("type", "text").put("title", "Update " + order.getMenuItem().getName()));
			options.add(new JSONObject().put("type", "text").put("title", "Delete " + order.getMenuItem().getName()));
			options.add(new JSONObject().put("type", "text").put("title", "Confirm Order"));
			JSONObject catItem = null;
			if (order.getSize() != null) {
				catItem = new JSONObject()
						.put("title", order.getMenuItem().getName()).put("subtitle", "Size: " + order.getSize()
								+ " Quantity:" + order.getQuantity() + " Price:" + order.getTotalPrice())
						.put("options", options);
			} else {
				catItem = new JSONObject().put("title", order.getMenuItem().getName())
						.put("subtitle", " Quantity:" + order.getQuantity() + " Price:" + order.getTotalPrice())
						.put("options", options);
			}
			catItem.put("imgurl", serverPath + "http://dummyimage.com/200x300&text=" + order.getMenuItem().getName());
			itemObject.add(catItem);

		}

		my.put("items", itemObject);

		return my;
	}

}
