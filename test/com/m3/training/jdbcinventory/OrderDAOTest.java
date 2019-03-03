package com.m3.training.jdbcinventory;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import org.junit.jupiter.api.Test;

class OrderDAOTest {

	@SuppressWarnings("null")
	@Test
	void test_retrieveAllFromDB_ConnectionNull() {
		String dbName = "test";
		Connection connection = null;
		OrderDAO testDAO = null;
		
		String msg = "Could not connect to database to retrieve items.";
		
		assertThrows(NullPointerException.class, () -> {testDAO.retrieveAllFromDB(connection, dbName);}, msg);
		
	}

}
