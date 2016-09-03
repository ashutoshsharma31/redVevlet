package com.redvelvet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.redvelvet.dao.MenuItemDao;
import com.redvelvet.model.MenuItem;

@WebServlet("/MenuItemServlet")
public class MenuItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MenuItemServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Request Example
		
		MenuItemDao menuItemDao = new MenuItemDao();
		String action = request.getParameter("action");
		PrintWriter writer = response.getWriter();
		if (action.equals("insert")) {
			/// MenuItemServlet?action=insert&menuitem={cafeid:1,name:Samosa,parentid:2,sizeable:N,type:P}
			String menuitem = request.getParameter("menuitem");
			JSONObject menuItemJsonObj = new JSONObject(menuitem);
			MenuItem menuItemObj = new MenuItem();
			menuItemObj.setCafeid(menuItemJsonObj.getInt("cafeid"));
			menuItemObj.setName(menuItemJsonObj.getString("name"));
			menuItemObj.setParentid(menuItemJsonObj.getInt("parentid"));
			menuItemObj.setSizeable(menuItemJsonObj.getString("sizeable"));
			menuItemObj.setType(menuItemJsonObj.getString("type"));
			menuItemDao.addMenuItem(menuItemObj);
			writer.println(menuItemObj);
		} else if (action.equals("update")) {
			/// MenuItemServlet?action=update&menuitem={itemid:15,cafeid:1,name:Samosa,parentid:2,sizeable:N,type:P}
			String menuitem = request.getParameter("menuitem");
			JSONObject menuItemJsonObj = new JSONObject(menuitem);
			MenuItem menuItemObj = new MenuItem();
			menuItemObj.setItemid(menuItemJsonObj.getInt("itemid"));
			menuItemObj.setCafeid(menuItemJsonObj.getInt("cafeid"));
			menuItemObj.setName(menuItemJsonObj.getString("name"));
			menuItemObj.setParentid(menuItemJsonObj.getInt("parentid"));
			menuItemObj.setSizeable(menuItemJsonObj.getString("sizeable"));
			menuItemObj.setType(menuItemJsonObj.getString("type"));
			menuItemDao.updateMenuItem(menuItemObj);
			writer.println(menuItemObj);
		} else {
			writer.println("No Action Defined");
		}

		writer.flush();
		writer.close();

	}

}
