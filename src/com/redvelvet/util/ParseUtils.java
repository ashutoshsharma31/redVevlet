package com.redvelvet.util;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.redvelvet.model.GupshupObject;

public class ParseUtils {
	public static GupshupObject parseRequestToGupsupObject(HttpServletRequest request) throws JSONException {
		String messageObj = request.getParameter("messageobj");
		String senderObj = request.getParameter("senderobj");
		JSONObject messageObjeObject = new JSONObject(messageObj);
		JSONObject senderObjeObject = new JSONObject(senderObj);
		String messageType = messageObjeObject.getString("type");
		String userMessage = messageObjeObject.getString("text");
		String channelType = senderObjeObject.getString("channeltype");
		String channelid = null;
		if (channelType.equalsIgnoreCase("telegram")) {
			long id = senderObjeObject.getLong("channelid");
			channelid = String.valueOf(id);
		} else {
			channelid = senderObjeObject.getString("channelid");
		}

		GupshupObject gs = new GupshupObject();
		try {
			gs.setChannelId(channelid);
			gs.setChannelType(channelType);
			gs.setMessageType(messageType);
			gs.setUserMessage(userMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gs;
	}
	public static boolean isInteger(String str) {
		return str.matches("^[0-9]+$");
	}
}
