package com;

import java.sql.*;


public class Item {
	
	//create DB connection 

	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test",
	 "root", "");
	 //For testing
	 System.out.print("Successfully connected");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}
	
	
	//Insert items into the database
	public String insertItem(String code, String name, String price, String desc)
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database";
	 }
	 // create a prepared statement
	 String query = " insert into item (`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
	 + " values (?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, code);
	 preparedStmt.setString(3, name);
	 preparedStmt.setDouble(4, Double.parseDouble(price));
	 preparedStmt.setString(5, desc); 
	
	//execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	catch (Exception e)
	 {
	 output = "Error while inserting";
	 System.err.println(e.getMessage());
	 }
	return output;
	}
	
	
	//Read all items from the database
	public String readItems()
    {
     String output = "";
    try
     {
     Connection con = connect();
     if (con == null)
     {
     return "Error while connecting to the database for reading.";
     }
     // Prepare the html table to be displayed
     output = "<table border='1'><tr><th>Item Code</th>"
     +"<th>Item Name</th><th>Item Price</th>"
     + "<th>Item Description</th>"
     + "<th>Update</th><th>Remove</th></tr>";
     String query = "select * from item";
     Statement stmt = con.createStatement();
     ResultSet rs = stmt.executeQuery(query);
     // iterate through the rows in the result set
     while (rs.next())
     {
     String itemID = Integer.toString(rs.getInt("itemID"));
     String itemCode = rs.getString("itemCode");
     String itemName = rs.getString("itemName");
     String itemPrice = Double.toString(rs.getDouble("itemPrice"));
     String itemDesc = rs.getString("itemDesc");
     // Add a row into the html table
     output += "<tr><td>" + itemCode + "</td>";
     output += "<td>" + itemName + "</td>";
     output += "<td>" + itemPrice + "</td>";
     output += "<td>" + itemDesc + "</td>";
     // buttons
     output += "<td><form method='post' action='itemps.jsp'>"
    		 +"<input name='itemID' type='hidden' value='" + itemID + "'>"
    		 + "<input name='action' value='select' type='hidden'>"
    		 + "<input name='btnUpdate' type='submit' value='Update'class='btn btn-secondary' >"
    		 + "</form></td>"
    		 + "<td><form method='post' action='itemps.jsp'>"
    		 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
    		 + "<input name='action' value='delete' type='hidden'>"
    		 + "<input name='itemID' type='hidden' value='" + itemID + "'>"
    		 + "</form></td></tr>";
     }
     con.close();
     // Complete the html table
     output += "</table>";
     }
    catch (Exception e)
     {
     output = "Error while reading the items.";
     System.err.println(e.getMessage());
     }
    return output;
    }
	
	

	//Update an item
	public String updateItem(int ID,String code, String name, String price, String desc)
	{
	// for testing System.out.println(ID);
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database";
		}
		// create a prepared statement
		String query = "update item set itemCode=?,itemName =?,itemPrice=?,itemDesc=? where itemID = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, code);
		preparedStmt.setString(2, name);
		preparedStmt.setDouble(3,Double.parseDouble(price));
		preparedStmt.setString(4, desc);
		preparedStmt.setInt(5, ID);
		//execute the statement
		preparedStmt.executeUpdate();
		con.close();
		output = "Updated successfully";
	}
	catch (Exception e)
	{
		output = "Error while updating";
		System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	
	//Retrieve selected item  to the form for update process
	public String readSelectedItem(int id)
	{
		//For testing System.out.print(id);
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for selected item reading.";
			}
			String query = "select * from item where itemID = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			// iterate through the rows in the result set
			while (rs.next())
			{
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				output += "<form method=post action=itemps.jsp>"
						+ " <input name='action' value='update' type='hidden'>"
						+ " Item ID: '"+itemID+ "'<br>"
						+ " Item code: <input name=itemCode type=text class= form-control value='"+itemCode+ "'><br>"
						+ " Item name: <input name=itemName type=text class= form-control value='"+itemName+ "'><br>"
						+ " Item price: <input name=itemPrice type=text class= form-control value='"+itemPrice+ "'><br>"
						+ " Item description: <input name=itemDesc type=text class= form-control value='"+itemDesc+ "'><br>"
						+ " <input name='itemID' type='hidden' value='" + itemID + "'>"
						+ " <input name=btnSubmit type=submit value=Update class='btn btn-secondary' >"
						+ " </form>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
	}
	catch (Exception e)
	{
		output = "Error while reading the one item.";
		System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	//Delete an item
	public String deleteItem(int ID) {
		String output = "";
		//For testing System.out.print(ID);
		try {
			Connection con = this.connect();
			if (con == null) {
				return "Error connecting to database";
		}
			//creating prepared statement
			String query = "delete from item where itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			//binding values to prepared statement
			preparedStmt.setInt(1,ID);
			preparedStmt.execute();
			con.close();
			output = "deleted successfully";
		} 
		catch (Exception e) {
			output = "Error while deleting";
			System.err.println(e.getMessage());
		}
		return output;
		}
		

}
