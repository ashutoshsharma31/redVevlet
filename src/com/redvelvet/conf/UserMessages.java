package com.redvelvet.conf;

import java.util.Arrays;

public class UserMessages {
	private static String[] startEvent = { "hi", "hello", "wassup", "how are you", "good morning", "good evening",
			"good afternoon" };

	public static boolean  isStartEvent(String usermessage) {
		return Arrays.asList(startEvent).contains(usermessage.toLowerCase());
	}
}
