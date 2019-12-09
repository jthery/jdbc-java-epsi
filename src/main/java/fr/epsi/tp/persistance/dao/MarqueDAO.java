package fr.epsi.tp.persistance.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import fr.epsi.tp.persistance.bean.Marque;
import fr.epsi.tp.persistance.ConnectionFactory;

public class MarqueDAO implements IJdbcCrud<Marque, Long> {
	
	
	public Marque findById(Long identifier) throws SQLException {
		  Connection conn = ConnectionFactory.getInstance().getConnection();
		  Statement st = conn.createStatement();
		  ResultSet rs = st.executeQuery("select id, libelle from marque where id = 1");
		  
		  Marque marque = new Marque();
		  
		  while(rs.next()) {
			marque.setIdentifier(rs.getLong("id"));
			marque.setLibelle(rs.getString("libelle"));
		  }
		  return marque;
  }

	public Collection<Marque> findAll() throws SQLException {
		
//		  Connection conn = ConnectionFactory.getInstance().getConnection();
//		  Statement st = conn.createStatement();
//		  ResultSet rs = st.executeQuery("select id, libelle from marque");
//		  
//		  Marque marque = new Marque();
//		  while(rs.next()) {
//			marque.setIdentifier(rs.getLong("id"));
//			marque.setLibelle(rs.getString("libelle"));
//		  }
//		  
//	    return (Collection<Marque>) marque;
		return null;
	  }

  public Marque create(Marque entity) throws SQLException {	  
    return null;
  }

}
