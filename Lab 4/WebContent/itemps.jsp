<%@ page import = "com.Item" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%
		if (request.getParameter("action") != null){
			
			Item itemObj = new Item();
			//itemObj.connect();//For testing the connect method
			if ( request.getParameter("action").equalsIgnoreCase("insert")) // redirect to insert method
			{
				String stsMsg = itemObj.insertItem(request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("itemPrice"),request.getParameter("itemDesc"));
				session.setAttribute("statusMsg", stsMsg);
			}
			else if (request.getParameter("action").equalsIgnoreCase("update")) // redirect to the update method
					{
						System.out.println("newUpdate");
						String stsMsg = itemObj.updateItem(Integer.parseInt(request.getParameter("itemID")),request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("itemPrice"),request.getParameter("itemDesc"));
						session.setAttribute("statusMsg", stsMsg);
					}
			else if(request.getParameter("action").equalsIgnoreCase("delete")) //redirect to the delete method
					{
						System.out.println("newDelete");
						String stsMsg = itemObj.deleteItem(Integer.parseInt(request.getParameter("itemID")));
						session.setAttribute("statusMsg", stsMsg);
					}

		}
	
	/*if (request.getParameter("itemID") != null)
	 {
	 Item itemObj = new Item();
	 String stsMsg = itemObj.deleteItem(Integer.parseInt(request.getParameter("itemID")));
	 session.setAttribute("statusMsg", stsMsg);
	 }
	*/
	
	
	
%>
  

    
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>Items Management</title>
</head>
<body>
	
<div class="container">
 <div class="row">
 <div class="col">
	 <h1>Items Management</h1>
	
	
	<%
	if (request.getParameter("action") != null) {
		if (request.getParameter("action").toString().equalsIgnoreCase("select")) {
			Item itemObj = new Item();
			out.print(itemObj.readSelectedItem(Integer.parseInt(request.getParameter("itemID"))));
		}
		else {
				out.print("<form method='post' action='itemps.jsp'> " + "<input name='action' value='insert' type='hidden'> "
				+ "Item code: <input name='itemCode' type='text' class='form-control'><br>"
				+ "Item name: <input name='itemName' type='text' class='form-control'><br> "
				+ "Item price: <input name='itemPrice' type='text' class='form-control'><br> "
				+ "Item description: <input name='itemDesc' type='text' class='form-control'><br> "
				+ "<input name='btnSubmit' type='submit' value='Save' class='btn btn-primary'> " + "</form>");
			}
	}	
	else {
			out.print("<form method='post' action='itemps.jsp'> " + "<input name='action' value='insert' type='hidden'> "
			+ "Item code: <input name='itemCode' type='text' class='form-control'><br>"
			+ "Item name: <input name='itemName' type='text' class='form-control'><br> "
			+ "Item price: <input name='itemPrice' type='text' class='form-control'><br> "
			+ "Item description: <input name='itemDesc' type='text' class='form-control'><br> "
			+ "<input name='btnSubmit' type='submit' value='Save' class='btn btn-primary'> " + "</form>");
		}
%>

	
	
	<div class="alert alert-success">
 <%
 	out.print(session.getAttribute("statusMsg"));
	%> 
</div>
	

	<br>
	<%
	Item itemObj = new Item();
 	out.print(itemObj.readItems());
 	%> 
 </div>
 </div>
</div>


</body>
</html>
