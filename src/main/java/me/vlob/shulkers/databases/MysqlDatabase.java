package me.vlob.shulkers.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDatabase extends Database {
	
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	public MysqlDatabase(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
	}

	@Override
	public Connection openConnection() throws SQLException,
			ClassNotFoundException {
		if (checkConnection()) {
			return connection;
		}
		
		String connectionURL = "jdbc:mysql://"
				+ this.hostname + ":" + this.port;
		if (database != null) {
			connectionURL = connectionURL + "/" + this.database;
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(connectionURL,
				this.user, this.password);
		return connection;
	}

}
