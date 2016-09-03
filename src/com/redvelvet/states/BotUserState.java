package com.redvelvet.states;

public class BotUserState {

	private String currentBotState;
	private String previousBotState;
	private String currentUserMessage;
	private String previousUserMesage;
	private String orderStatus;
	private String itemStatus;

	public String getCurrentBotState() {
		return currentBotState;
	}

	public BotUserState setCurrentBotState(String currentBotState) {
		this.currentBotState = currentBotState;
		return this;
	}

	public String getPreviousBotState() {
		return previousBotState;
	}

	public BotUserState setPreviousBotState(String previousBotState) {
		this.previousBotState = previousBotState;
		return this;
	}

	public String getCurrentUserMessage() {
		return currentUserMessage;
	}

	public BotUserState setCurrentUserMessage(String currentUserMessage) {
		this.currentUserMessage = currentUserMessage;
		return this;
	}

	public String getPreviousUserMesage() {
		return previousUserMesage;
	}

	public BotUserState setPreviousUserMesage(String previousUserMesage) {
		this.previousUserMesage = previousUserMesage;
		return this;
	}

	public void refreshBotState(String state) {
		this.previousBotState = this.currentBotState;
		this.currentBotState = state;
	}

	public void refreshUserMessage(String msg) {
		this.previousUserMesage = this.currentUserMessage;
		this.currentUserMessage = msg;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the itemStatus
	 */
	public String getItemStatus() {
		return itemStatus;
	}

	/**
	 * @param itemStatus
	 *            the itemStatus to set
	 */
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

}
