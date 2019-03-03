package com.m3.training.jdbcinventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {
	
	final static Logger logger = LogManager.getLogger(DBConnection.class);

	private static String filePath;
	private String dbDriver;
	private String dbConnection;
	private String user;
	private String pass;
	private Statement statement;
	private Connection connection;
	private static String dbName;
	
	private DBConnection(String db) {
		this.dbDriver = "driver";
		this.dbConnection = "dbconnection";
		this.user = "un";
		this.pass = "pw"; 
		dbName = db;
		
	}
	
	public static void main(String[] args) throws Exception {
		DBConnection.resolveFilePath();
		
		String msg = "Resolve file path as " + filePath;
		logger.info(msg);
		
		DBConnection dbCon = new DBConnection("AJ210");
		

		dbCon.resolveProperties();
		OrderDAO OrderInventoryDB = new OrderDAO();
		OrderInventoryDB.retrieveAllFromDB(dbCon.connectToDB(dbCon.dbDriver), dbName);
		System.out.println(OrderInventoryDB.toString());		
	}
	
	public Connection connectToDB(String driver) throws Exception{
		try {
			Class.forName(driver);
			
			connection = DriverManager.getConnection(dbConnection, user, pass);
			
			String msg = "Connected to database.";
			logger.info(msg);
			
			return connection;
	
		}
		catch(SQLException e){
			String msg  = "SQLException was thrown.";
			logger.error(msg);
			throw new SQLException(msg);
		}
		catch(ClassNotFoundException e) {
			String msg  ="Driver [" + driver + "] or Database [" + dbName + "] values incorrect.";
			logger.error(msg);
			throw new ClassNotFoundException(msg);
		}
	}
	
	
	public static void resolveFilePath() {
		filePath  = System.getProperty("user.dir");
		filePath  +="\\JDBC_SQL\\com\\m3\\training\\JDBC\\config.properties";
	}
	
	public void resolveProperties() throws Exception {
		try {
			File propFile = new File(filePath);
			Properties property = new Properties();
			FileInputStream propFileStream = new FileInputStream(propFile);
			property.load(propFileStream);
			
			dbDriver = property.getProperty(dbDriver);
			dbConnection = property.getProperty(dbConnection);
			user = property.getProperty(user);
			pass = property.getProperty(pass);
			
			String msg = "Obtained values from properties file";
			logger.info(msg);
		}
		
		catch(FileNotFoundException e){
			String msg = "File at " + filePath + " not found";
			logger.error(msg);
			throw new FileNotFoundException(msg);
		} catch (IOException e) {
			String msg = "Problem loading property file into Property object";
			logger.error(msg);
			throw new IOException(msg);
		}
	}

	String getDbName() {
		return dbName;
	}

	static void setDbName(String name) {
		dbName = name;
	}

	String getFilePath() {
		return filePath;
	}

	void setFilePath(String fp) {
		filePath = fp;
	}

	String getDbDriver() {
		return dbDriver;
	}

	void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	String getDbConnection() {
		return dbConnection;
	}

	void setDbConnection(String dbConnection) {
		this.dbConnection = dbConnection;
	}

	String getUser() {
		return user;
	}

	void setUser(String user) {
		this.user = user;
	}

	String getPass() {
		return pass;
	}

	void setPass(String pass) {
		this.pass = pass;
	}

	Connection getConnection() {
		return connection;
	}

	void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public static DBConnection DBConnectionFactory(String dbname) {
		DBConnection dbCon = new DBConnection(dbname);
		return dbCon;
	}
	
}
