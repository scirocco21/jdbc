package com.m3.training.jdbcinventory;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

class DbconTest {
	DBConnection dbConUnderTest;
	String expectedDbConnection = "jdbc:oracle:thin:@10.20.40.53:1521:oradb1";
	String expectedDbDriver = "oracle.jdbc.driver.OracleDriver";
	String expectedUser = "delegate";
	String expectedPass = "pass";
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test_DBConnection_DBConnectionFactory() {
		String dbname = "some data base name";
		dbConUnderTest = DBConnection.DBConnectionFactory(dbname);
		assertNotNull(dbConUnderTest);
	}
	
	@Test
	void test_DBConnection_connectToDb_BadDriverInput() {
		String dbName = "AJ210";
		String testDriver = "NotAnActualDriver";
		String msg = testDriver + " could not be found";
		dbConUnderTest = DBConnection.DBConnectionFactory(dbName);
		
		assertThrows(ClassNotFoundException.class, () -> {dbConUnderTest.connectToDB(testDriver);}, msg);
		
		
	}
	
	@Test
	void test_DBConnection_connectToDb_BadDatabaseInput () {
		String dbName = "not a db";
		String testDriver = "oracle.jdbc.driver.OracleDriver";
		String msg =  " Could not connect to SQL server; Connection credentials may be invalid or SQL server is undreachable.";
		dbConUnderTest = DBConnection.DBConnectionFactory(dbName);
		
		assertThrows(ClassNotFoundException.class, () -> {dbConUnderTest.connectToDB(testDriver);}, msg);
	}
	

	
	

}