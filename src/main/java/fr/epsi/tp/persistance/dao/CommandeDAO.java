package fr.epsi.tp.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.epsi.tp.persistance.ConnectionFactory;
import fr.epsi.tp.persistance.bean.Commande;
import fr.epsi.tp.persistance.bean.CommandeLigne;
import fr.epsi.tp.persistance.bean.Produit;

public class CommandeDAO implements IJdbcCrud<Commande, Long> {
	ProduitDAO produitDao = new ProduitDAO();

	public Commande findById(Long identifier) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT id, date_creation, comm_produit.quantite FROM (commande, comm_produit) WHERE commande.id = ? AND comm_produit.commande_id = ?");
		ArrayList<CommandeLigne> ret = new ArrayList<>();
		Commande commande = new Commande();
		CommandeLigne commandeLigne = new CommandeLigne();
		Produit produit = new Produit();

		try {
			ps.setLong(1, identifier);
			ps.setLong(2, identifier);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				commande.setIdentifier(rs.getLong("id"));
				commande.setDateCreation(rs.getDate("date_creation").toLocalDate());
				produit = produitDao.findById(rs.getLong("quantite"));
				commandeLigne.setProduit(produit);
				commandeLigne.setQuantite(rs.getInt("quantite"));
				ret.add(commandeLigne);
				commande.setLignes(ret);
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
		return commande;
	}

	public Collection<Commande> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Commande create(Commande entity) throws SQLException {
		return null;
	}
}
