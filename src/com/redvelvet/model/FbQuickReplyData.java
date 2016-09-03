package com.redvelvet.model;

import java.util.Arrays;

public class FbQuickReplyData {
	private String message;
	private String[] options;
	private String msgid;

	public FbQuickReplyData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FbQuickReplyData(String message, String[] options, String msgid) {
		super();
		this.message = message;
		this.options = options;
		this.msgid = msgid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		return "FbQuickReplyData [message=" + message + ", options=" + Arrays.toString(options) + ", msgid=" + msgid
				+ "]";
	}

}
