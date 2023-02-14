package me.vlob.shulkers.databases;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class Database {
	
	protected Connection connection;
	
	protected Database() {
		this.connection = null;
	}

	public abstract Connection openConnection() throws SQLException,
			ClassNotFoundException;

	public boolean checkConnection() throws SQLException {
		return connection != null && !connection.isClosed();
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean closeConnection() throws SQLException {
		if (connection == null) {
			return false;
		}
		connection.close();
		return true;
	}

}
