package fr.epsi.tp.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.epsi.tp.persistance.bean.Marque;
import fr.epsi.tp.persistance.ConnectionFactory;

public class MarqueDAO implements IJdbcCrud<Marque, Long> {

	public Marque findById(Long identifier) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id, libelle from marque where id =" + identifier);
		Marque marque = new Marque();
		
		try {
			while (rs.next()) {
				marque.setIdentifier(rs.getLong("id"));
				marque.setLibelle(rs.getString("libelle"));
			}
			System.out.println("Database findbyID successfully ");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return marque;
	}

	public Collection<Marque> findAll() throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id, libelle from marque");
		Collection<Marque> ret = new ArrayList<>();
		
		try {
			while (rs.next()) {
				Marque marque = new Marque();
				marque.setIdentifier(rs.getLong("id"));
				marque.setLibelle(rs.getString("libelle"));
				ret.add(marque);
			}
			System.out.println("Database findAll successfully ");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public Marque create(Marque entity) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO marque(libelle) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, entity.getLibelle());
			ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	entity.setIdentifier(rs.getLong(1));
	        	}
	        
	        System.out.println("Database created successfully ");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return entity;
	}
	
	public Marque update(Marque entity) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		try {

			PreparedStatement ps = conn.prepareStatement("UPDATE marque SET libelle=? where id=?");
			
			ps.setString(1, entity.getLibelle());
			ps.setFloat(2, entity.getIdentifier());
			ps.executeUpdate();
			System.out.println("Database updated successfully ");
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return entity;
	}

}
