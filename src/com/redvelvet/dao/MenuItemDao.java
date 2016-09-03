package com.redvelvet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.redvelvet.model.MenuItem;
import com.redvelvet.model.User;
import com.redvelvet.util.DbUtil;

public class MenuItemDao {
	private Connection connection;

	public MenuItemDao() {
		connection = DbUtil.getConnection();
	}

	public void addMenuItem(MenuItem menuItem) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into menuitem(cafeid,name,sizeable,parentid,type) value (?, ?, ?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setInt(1, menuItem.getCafeid());
			preparedStatement.setString(2, menuItem.getName());
			preparedStatement.setString(3, menuItem.getSizeable());
			preparedStatement.setInt(4, menuItem.getParentid());
			preparedStatement.setString(5, menuItem.getType());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMenuItem(int itemid) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from menuitem where itemid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, itemid);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateMenuItem(MenuItem menuItem) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"update menuitem set cafeid=?, name=?, sizeable=?, parentid=?, type=? where itemid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, menuItem.getCafeid());
			preparedStatement.setString(2, menuItem.getName());
			preparedStatement.setString(3, menuItem.getSizeable());
			preparedStatement.setInt(4, menuItem.getParentid());
			preparedStatement.setString(5, menuItem.getType());
			preparedStatement.setInt(6, menuItem.getItemid());
			int updates = preparedStatement.executeUpdate();
			//System.out.println("UPDATEs"+updates);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<MenuItem> getAllMenuItems() {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		try {
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
				menuItems.add(menuItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuItems;
	}

	public MenuItem getMenuItemById(int itemid) {
		MenuItem menuItem = new MenuItem();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from menuitem where itemid=?");
			preparedStatement.setInt(1, itemid);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				menuItem.setItemid(rs.getInt("itemid"));
				menuItem.setCafeid(rs.getInt("cafeid"));
				menuItem.setName(rs.getString("name"));
				menuItem.setSizeable(rs.getString("sizeable"));
				menuItem.setParentid(rs.getInt("parentid"));
				menuItem.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuItem;
	}
}
