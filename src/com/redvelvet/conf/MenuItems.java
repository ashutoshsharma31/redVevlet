package com.redvelvet.conf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.redvelvet.model.MenuItem;
import com.redvelvet.util.DbUtil;

public class MenuItems {
	private static HashMap<String, MenuItem> menuMap = new HashMap<String, MenuItem>();
	private static List<MenuItem> menuItems = new ArrayList<MenuItem>();
	private static Connection connection;

	static {

		try {
			connection = DbUtil.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from menuitem");
			while (rs.next()) {
				MenuItem menuItem = new MenuItem();
				menuItem.setItemid(rs.getInt("itemid"));
				menuItem.setCafeid(rs.getInt("cafeid"));
				menuItem.setName(rs.getString("name"));
				menuItem.setSizeable(rs.getString("sizeable"));
				menuItem.setParentid(rs.getInt("parentid"));
				menuItem.setType(rs.getString("type"));
				menuMap.put(menuItem.getName(), menuItem);
				menuItems.add(menuItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public MenuItems() {
		
	}

	public static boolean hasItem(String key) {
		if (menuMap.get(key) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	

	public static MenuItem getMenuItems(String key) {

		return menuMap.get(key);

	}

	public static void printAllMenuItems() {
		for (String menuName : menuMap.keySet()) {
			MenuItem menuItem = menuMap.get(menuName);
			System.out.println(menuItem);

		}
	}

	public static boolean checkMenuItemToOrder(MenuItem menuItem) {
		return menuItem.getType().equals("P");
	}

	public static boolean isSizeable(MenuItem menuItem) {
		return menuItem.getSizeable().equals("Y");
	}

	public static ArrayList<String> getStartOptions() {
		ArrayList<String> startArr = new ArrayList<String>();
		for (MenuItem menuObj : menuItems) {
			if (menuObj.getParentid() == 0) {
				startArr.add(menuObj.getName());
			}
		}
		return startArr;
	}
	public static ArrayList<String> getNextOptions(MenuItem menuItem) {
		ArrayList<String> nextArr = new ArrayList<String>();
		for (MenuItem menuObj : menuItems) {
			if (menuObj.getParentid() == menuItem.getItemid()) {
				nextArr.add(menuObj.getName());
			}
		}
		return nextArr;
	}

	public static ArrayList<String> getReviewOrder() {
		ArrayList<String> confirm = new ArrayList<String>();
		confirm.addAll(getStartOptions());
		confirm.add("Confirm Order");
		confirm.add("Review Order");
		return confirm;
	}
}
