package fr.epsi.tp.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
  private static ConnectionFactory INSTANCE = new ConnectionFactory();

  private ConnectionFactory() { }

  public Connection getConnection(String url, String login, String password) throws SQLException {
	  return DriverManager.getConnection(url, login, password);
  }

  public Connection getConnection() throws SQLException {
	  return getConnection("jdbc:mysql://localhost:3306/boutique?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");
  }

  public static ConnectionFactory getInstance() {
    return INSTANCE;
  }
}
