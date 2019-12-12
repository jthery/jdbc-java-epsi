package fr.epsi.tp.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.epsi.tp.persistance.ConnectionFactory;
import fr.epsi.tp.persistance.bean.Marque;
import fr.epsi.tp.persistance.bean.Produit;

public class ProduitDAO implements IJdbcCrud<Produit, Long> {
	MarqueDAO marqueDao = new MarqueDAO();
			
	public Produit findById(Long identifier) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT id, libelle, description, prix, marque_id FROM produit WHERE id =" + identifier);
		Produit produit = new Produit();
		Marque marque = new Marque();

		try {
			while (rs.next()) {
				produit.setIdentifier(rs.getLong("id"));
				produit.setLibelle(rs.getString("libelle"));
				produit.setDescription(rs.getString("description"));
				produit.setPrix(rs.getBigDecimal("prix"));
				marque = marqueDao.findById(rs.getLong("marque_id"));
				produit.setMarque(marque);
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
		return produit;
	}

	public Collection<Produit> findAll() throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT id, libelle, description, prix, marque_id, image FROM produit");
		Collection<Produit> ret = new ArrayList<>();
		Marque marque = new Marque();
		
		try {
			while (rs.next()) {
				Produit produit = new Produit();
				produit.setIdentifier(rs.getLong("id"));
				produit.setLibelle(rs.getString("libelle"));
				produit.setDescription(rs.getString("description"));
				produit.setPrix(rs.getBigDecimal("prix"));
				marque = marqueDao.findById(rs.getLong("marque_id"));
				produit.setMarque(marque);
				ret.add(produit);
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

	public Produit create(Produit entity) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO produit(libelle, description, prix, marque_id) VALUES(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, entity.getLibelle());
			ps.setString(2, entity.getDescription());
			ps.setBigDecimal(3, entity.getPrix());
			ps.setLong(4, entity.getMarque().getIdentifier());
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

	public Collection<Produit> findByLibelle(String libelle) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT id, description, prix, marque_id FROM produit WHERE libelle = ?");
		Collection<Produit> ret = new ArrayList<>();
		Produit produit = new Produit();
		Marque marque = new Marque();

		try {
			ps.setString(1, libelle);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				produit.setIdentifier(rs.getLong("id"));
				produit.setDescription(rs.getString("description"));
				produit.setPrix(rs.getBigDecimal("prix"));
				marque = marqueDao.findById(rs.getLong("marque_id"));
				produit.setMarque(marque);
				ret.add(produit);
			}
			System.out.println("Database findbyLibelle successfully ");
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

	public Produit update(Produit entity) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE produit SET libelle=?, description=?, prix=?, marque_id=? WHERE id=?");

			ps.setString(1, entity.getLibelle());
			ps.setString(2, entity.getDescription());
			ps.setBigDecimal(3, entity.getPrix());
			ps.setLong(4, entity.getMarque().getIdentifier());
			ps.setLong(5, entity.getIdentifier());
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
