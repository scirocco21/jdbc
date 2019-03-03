package com.m3.training.jdbcinventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderDAO {
	
	final static Logger logger = LogManager.getLogger(OrderDAO.class);

	private List<Item> inventoryItems;

	public OrderDAO() {
		super();
		inventoryItems = new ArrayList<Item>();
	}

	List<Item> retrieveAllFromDB(Connection connection, String dbName) {

		Statement stmt;
		try {
			stmt = createSQLStmt(connection, dbName);
			String selectAll = "SELECT * FROM " + dbName;

			if (stmt.execute(selectAll)) {
				ResultSet res = stmt.getResultSet();

				while (res.next()) {

					Item item = Item.itemFactory(res.getInt(1), res.getNString("ITEM_NAME"), res.getInt(3),
							res.getNString("DESCRIPTION"));

					inventoryItems.add(item);
				}
			}
		} catch (SQLException e) {
			String msg = "Could not retrieve items from table " + dbName;
			logger.error(msg);
		}
		catch (NullPointerException e) {
			String msg = "Connection is null";
			logger.error(msg);
		}
		
		return inventoryItems;
	}

	Integer getMaxId(Connection connection, String dbName) throws SQLException {

		Integer maxId = null;
		Statement stmt;
		try {
			stmt = createSQLStmt(connection, dbName);
			String selectMaxId = "SELECT MAX item_id FROM " + dbName;
		
			if(stmt.execute(selectMaxId)) {
				ResultSet rs = stmt.getResultSet();
				maxId = rs.getInt("ITEM_ID");
			}
		}
		
		catch(SQLException e) {
			String msg = "Could not get max item_id from table " + dbName;
			logger.error(msg);
		}
		catch (NullPointerException e) {
			String msg = "Connection is null";
			logger.error(msg);
		}
		
		return maxId;
		
	}

	Boolean commitToDb(List<Item> itemList, Connection connection, String dbName) {

		Boolean ret = false;
		Statement stmt;
		try {
			stmt = createSQLStmt(connection, dbName);
			String insertIntoDb = "INSERT INTO " + dbName + " VALUES ";
			
			for(Item item: itemList) {
				
				stmt.executeQuery(insertIntoDb + "( " + item.getidNum() +", '" + item.getItemName()
				+"', " + item.getQuantity() + ", '" + item.getDescription() +"')" );
			}
			ret = true;
			
			String msg = "Commited items to table " + dbName;
			logger.error(msg);
		}
		
		catch(SQLException e) {
			String msg = "Could not commit items to table " + dbName;
			logger.error(msg);
		}
		return ret;
	}

	Statement createSQLStmt(Connection conn, String dbName) throws SQLException {
		Connection copyConnection = conn;
		Statement stmt = copyConnection.createStatement();

		if (stmt == null) {
			String msg = "Could not successfully connect to DB";
			logger.error(msg);
			throw new SQLException(msg);
		}

		return stmt;
	}

	public String toString() {
		StringBuilder toPrint = new StringBuilder("");
		String empty = "No items in inventory";

		if (this.inventoryItems.size() > 0) {
			for (Item item : inventoryItems) {
				toPrint.append(item.toString());

			}
			return toPrint.toString();
		}
		return empty;

	}

	// public static void main(String[] args) {
	//
	//	
	// Connection copyConnection = connection;
	// Statement stmt = copyConnection.createStatement();
	//
	// if (stmt == null) {
	// String msg = "Could not successfully connect to DB";
	// throw new SQLException(msg);
	// }
	//
	//
	// }
}
