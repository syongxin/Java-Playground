/**
 * 
 */
package com.miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author yongxinsun
 *
 */
public class MiniProject {

	Connection con;
	Statement st;
	PreparedStatement pt;
	ResultSet rs;

	MiniProject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject", "root", "Haobb1210!");
			if (con != null) {
				System.out.println("Database Connected!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*
	 * 0. User input validation, valid inputs are number only.
	 */

	public int validInput() {
		Scanner reader = new Scanner(System.in);
		int option = 0;
		String welcome = "----------------- Please enter a valid number to perform an action, -1 to exit: -----------------\n"
				+ "1. Add new product category.\n" 
				+ "2. Add new product under a category.\n"
				+ "3. View specific product's description and other details.\n" 
				+ "4. Listing of categories.\n"
				+ "5. Listing of all the products of a category\n"
				+ "6. Display Average number of products among all categories\n"
				+ "7. Display the product which has largest description\n" 
				+ "8. Delete a category\n"
				+ "9. Delete a product\n"
				+ "10. Remove Product from a category\n"
				+ "11. Display most recent 5 products.\n";

		while (true) {
			System.out.println(welcome);
			while (!reader.hasNextInt()) {
				System.out.println("That's not a number");
				reader.next();
			}

			option = reader.nextInt();
			if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5 || option == 6 || option == 7 || option == 8
					|| option == 9 || option == 10 || option == 11 || option == -1) {
				break;
			}
		}

		return option;
	}

	/*
	 * 1. Ask valid user input for category name
	 */
	public String askNewCategory() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Please enter the name of category:");
		String new_category = reader.nextLine();
		while (new_category.length() > 50) {
			System.out.println("Category name is too long! 50 max");
			new_category = reader.nextLine();
		}

		return new_category;
	}

	/*
	 * 1. Insert new product category into category table
	 */
	public void insertNewCategory(String cat) throws SQLException {
		pt = con.prepareStatement("insert into category values (default,?);");
		pt.setString(1, cat);
		int rowAffected = pt.executeUpdate();

		System.out.println("New category added successfully!");

	}

	/*
	 * 2. Ask valid user inputs for product detail and target category
	 */
	public ArrayList<Object> askNewProductUnderCategory() throws SQLException {
		Scanner reader = new Scanner(System.in);
		ArrayList<Object> input_set = new ArrayList<>();

		String target_cat = askNewCategory();
		//HashMap<Integer, String> cat_list = listCategories();
		ArrayList<String> cat_list = new ArrayList<>();
		st = con.createStatement();
		rs = st.executeQuery("select * from category;");
		
		while(rs.next()){
			cat_list.add(rs.getString(2));
		}
		
		if (!cat_list.contains(target_cat)) {
			insertNewCategory(target_cat);
		}

		System.out.println("Please enter the product name:");
		String pro_name = reader.nextLine();
		while (pro_name.length() > 30) {
			System.out.println("Product name is too long! max 30. Please enter again:");
			reader.nextLine();
		}

		System.out.println("Please enter the product description:");
		String pro_description = reader.nextLine();
		while (pro_description.length() > 100) {
			System.out.println("Product description is too long! max 100. Please enter again:");
			reader.nextLine();
		}

		Double pro_price = 0.0;
		System.out.println("Please enter the product price:");
		while (!reader.hasNextDouble()) {
			System.out.println("That's not a number");
			reader.nextDouble();
		}
		pro_price = reader.nextDouble();

		input_set.add(pro_name);
		input_set.add(pro_description);
		input_set.add(pro_price);
		input_set.add(target_cat);

		return input_set;
	}

	/*
	 * 2. Add new product under a category
	 */
	public void insertNewProductUnderCategory(ArrayList<Object> input_set) throws SQLException {
		String pro_name = (String) input_set.get(0);
		String pro_description = (String) input_set.get(1);
		double pro_price = (double) input_set.get(2);
		String cat = (String) input_set.get(3);

		String insert_product = "insert into product values (default, ?, ?, ?)";
		String select_cat_id = "select id from category where cat_name = ?";
		String select_new_product_id = "select max(id) as id from product";

		// Insert new product to product table
		pt = con.prepareStatement(insert_product);
		pt.setString(1, pro_name);
		pt.setString(2, pro_description);
		pt.setDouble(3, pro_price);
		pt.executeUpdate();
		// Find the category id by category name
		pt = con.prepareStatement(select_cat_id);
		pt.setString(1, cat);
		rs = pt.executeQuery();
		int cat_id = 0;
		if (rs.next()) {
			cat_id = rs.getInt(1);
		}
		// Find newly inserted product id
		pt = con.prepareStatement(select_new_product_id);
		rs = pt.executeQuery();
		int pro_id = 0;
		if (rs.next()) {
			pro_id = rs.getInt(1);
		}
		// Insert (pid, cid) pair into pro_cat table
		String insert_procat = "insert into pro_cat values (?, ?)";
		pt = con.prepareStatement(insert_procat);
		pt.setInt(1, pro_id);
		pt.setInt(2, cat_id);
		pt.executeUpdate();
		System.out.println("New product added successfully!");

		System.out.println("New product under category added successfully!");
	}

	/*
	 * 3. Show product list
	 */
	public HashSet<Integer> listProducts() throws SQLException {
		HashSet<Integer> result = new HashSet<>();

		st = con.createStatement();
		rs = st.executeQuery("select id, pro_name from product;");

		while (rs.next()) {
			result.add(rs.getInt(1));
			System.out.println("" + rs.getInt(1) + ". " + rs.getString(2));
		}

		return result;
	}

	/*
	 * 3. View specific product's description and other details
	 */
	public void showProductDetail() throws SQLException {
		System.out.println("Please enter product id to view details:");
		HashSet<Integer> product_list = listProducts();
		Scanner reader = new Scanner(System.in);
		int option = 0;

		while (!reader.hasNextInt()) {
			System.out.println("That's not a valid number");
			reader.next();
		}

		option = reader.nextInt();
		if (!product_list.contains(option)) {
			System.out.println("Product does not exist!");
		} else {

			pt = con.prepareStatement("select * from product where id = ?");
			pt.setInt(1, option);
			rs = pt.executeQuery();

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt(1) + "\nName: " + rs.getString(2) + "\nDescription: "
						+ rs.getString(3) + "\nPrice: " + rs.getDouble(4));
			}

		}
	}

	/*
	 * 4. Listing of categories
	 */
	public HashMap<Integer, String> listCategories() throws SQLException {
		HashMap<Integer, String> cat_list = new HashMap<>();

		st = con.createStatement();
		rs = st.executeQuery("select * from category;");

		while (rs.next()) {
			cat_list.put(rs.getInt(1), rs.getString(2));
			System.out.println("" + rs.getInt(1) + " " + rs.getString(2));
		}

		return cat_list;
	}

	/*
	 * 5. Listing of all the products of a category
	 */
	public void allProductsOfCategory() throws SQLException {
		System.out.println("Please select a category:");
		Scanner reader = new Scanner(System.in);
		int option = 0;
		HashMap<Integer, String> cat_list = listCategories();

		while (!reader.hasNextInt()) {
			System.out.println("That's not a number");
			reader.next();
		}

		option = reader.nextInt();

		if (!cat_list.keySet().contains(option)) {
			System.out.println("Category does not exist!");
		} else {

			pt = con.prepareStatement(
					"select id, pro_name from product p, pro_cat pc where p.id = pc.pid and pc.cid = ?");
			pt.setInt(1, option);
			rs = pt.executeQuery();

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt(1) + " Name: " + rs.getString(2));
			}

		}

	}

	/*
	 * 6. Display Average number of products among all categories
	 */
	public void avgNumOfProducts() throws SQLException{
		
		
		double total_count = 0;
		int total_cat = 0;
		
		HashMap<Integer, String> cat_list = new HashMap<>();;
		
		st = con.createStatement();
		rs = st.executeQuery("select * from category;");

		while (rs.next()) {
			cat_list.put(rs.getInt(1), rs.getString(2));
			total_cat++;
		}
		
		Iterator it = cat_list.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        pt = con.prepareStatement("SELECT COUNT(pid) FROM pro_cat where cid = ?");
			pt.setInt(1, (int) pair.getKey());
			rs = pt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1) + " in " +pair.getValue());
				total_count += Integer.parseInt(rs.getString(1));
			}
			
	    }
	    System.out.println("Average number of products among all categories: " +total_count/total_cat);

	}
	

	/*
	 * 7. Display the product which has largest description
	 */
	public void productLongestDscrp() throws SQLException {
		System.out.println("The product that has longest description is:");

		pt = con.prepareStatement("select * from product order by length(pro_description) desc limit 1;");

		rs = pt.executeQuery();

		while (rs.next()) {
			System.out.println("ID: " + rs.getInt(1) + "\nName: " + rs.getString(2) + "\nDescription: "
					+ rs.getString(3) + "\nPrice: " + rs.getDouble(4));
		}

	}

	/*
	 * 8. Delete a category
	 */

	public void deleteCategory() throws SQLException {
		System.out.println("Please enter the id of category that you want to delete:");
		HashMap<Integer, String> cat_list = listCategories();
		Scanner reader = new Scanner(System.in);
		int option = 0;

		while (!reader.hasNextInt()) {
			System.out.println("That's not a valid number");
			reader.next();
		}

		option = reader.nextInt();

		if (!cat_list.keySet().contains(option)) {
			System.out.println("Category does not exist!");
		} else {

			// Delete category from pro_cat table
			pt = con.prepareStatement("delete from pro_cat where cid = ?");
			pt.setInt(1, option);
			pt.executeUpdate();
			// Delete category from category table
			pt = con.prepareStatement("delete from category where id = ?");
			pt.setInt(1, option);
			pt.executeUpdate();

			System.out.println("Category deleted successfully!");

		}

	}

	/*
	 * 9. Delete a product
	 */
	public void deleteProduct() throws SQLException {
		System.out.println("Please enter the id of product that you want to delete:");
		HashSet<Integer> pro_list = listProducts();
		Scanner reader = new Scanner(System.in);
		int option = 0;

		while (!reader.hasNextInt()) {
			System.out.println("That's not a valid number");
			reader.next();
		}

		option = reader.nextInt();

		if (!pro_list.contains(option)) {
			System.out.println("Product does not exist!");
		} else {

			// Delete category from pro_cat table
			pt = con.prepareStatement("delete from pro_cat where pid = ?");
			pt.setInt(1, option);
			pt.executeUpdate();
			// Delete category from category table
			pt = con.prepareStatement("delete from product where id = ?");
			pt.setInt(1, option);
			pt.executeUpdate();

			System.out.println("Product deleted successfully!");

		}
	}

	/*
	 * 10. Remove Product from a category
	 */
	public void removeProFromCat() throws SQLException {
		System.out.println("Please select the product you want to update:");
		HashSet<Integer> product_list = listProducts();
		Scanner reader = new Scanner(System.in);
		int pid = 0;
		//Get Product id
		while (!reader.hasNextInt()) {
			System.out.println("That's not a valid number");
			reader.next();
		}

		pid = reader.nextInt();
		
		//Use product id to find the categories that it belongs to.
		if (!product_list.contains(pid)) {
			System.out.println("Product does not exist!");
		} else {
			//Reset input option
			
			pt = con.prepareStatement(
					"select cat.id, cat.cat_name from pro_cat pc, category cat where pc.cid = cat.id and pc.pid = ?");
			pt.setInt(1, pid);
			rs = pt.executeQuery();

			int count = 0;
			if (rs.last()) {
				count = rs.getRow();
				rs.beforeFirst();
			}

			if (count == 0) {
				System.out.println("This product does not belong to any categories!");
			} else {
				ArrayList<Integer> cat_list = new ArrayList<>();
				System.out.println("Please enter the id of category:");
				while (rs.next()) {
					System.out.println("" + rs.getInt(1) + ". " + rs.getString(2));
					cat_list.add(rs.getInt(1));
				}
				//Get category id
				int cid = 0;
				while (!reader.hasNextInt()) {
					System.out.println("That's not a valid number");
					reader.next();
				}

				cid = reader.nextInt();
				// Write a function that takes pid and cid, remove record from pro_cat.
				if(!cat_list.contains(cid)){
					System.out.println("Category does not exits!");
				}else{
					removeProCatPair(pid, cid);
				}
				
			}
		}

	}
	
	/*
	 * 10. Remove Product from a category
	 */
	public void removeProCatPair(int pid, int cid) throws SQLException{
		
		pt = con.prepareStatement("delete from pro_cat where pid = ? and cid = ?");
		pt.setInt(1, pid);
		pt.setInt(2, cid);
		pt.executeUpdate();
		
		System.out.println("Product has been removed from selected category!");
		
	}
	
	
	/*
	 * 11. Display most recent 5 products
	 */
	public void showRecentProduct() throws SQLException{
		st = con.createStatement();
		rs = st.executeQuery("select * from product order by id desc limit 5;");

		while (rs.next()) {
			//System.out.println("" + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4));
			System.out.println("ID: "+rs.getInt(1));
			System.out.println("Name: "+rs.getString(2));
			System.out.println("Description: "+rs.getString(3));
			System.out.println("Price: "+ rs.getDouble(4));
			System.out.println("===========================================================");
		}

	}
	

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		MiniProject app = new MiniProject();

		while (true) {
			int option = app.validInput();

			switch (option) {
			case 1:
				String new_category = app.askNewCategory();
				app.insertNewCategory(new_category);
				break;
			case 2:
				ArrayList<Object> input_set = app.askNewProductUnderCategory();
				app.insertNewProductUnderCategory(input_set);
				break;
			case 3:
				app.showProductDetail();
				break;
			case 4:
				System.out.println("All categories are listed here:");
				app.listCategories();
				break;
			case 5:
				app.allProductsOfCategory();
				break;
			case 6:
				app.avgNumOfProducts();
				break;
			case 7:
				app.productLongestDscrp();
				break;
			case 8:
				app.deleteCategory();
				break;
			case 9:
				app.deleteProduct();
				break;
			case 10:
				app.removeProFromCat();
				break;
			case 11:
				app.showRecentProduct();
				break;
			case -1:
				return;

			}

		}
	}

}
