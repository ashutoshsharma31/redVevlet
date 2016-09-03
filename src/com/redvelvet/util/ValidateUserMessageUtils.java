package com.redvelvet.util;

import com.redvelvet.conf.UserMessages;

public class ValidateUserMessageUtils {
	public static String getEvent(String usermessage) {
		String eventName = "NOT_START";
		if (UserMessages.isStartEvent(usermessage)) {
			eventName = "START";
		}

		return eventName;
	}

}
