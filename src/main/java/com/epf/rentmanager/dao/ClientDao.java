package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository

public class ClientDao {

	//private static ClientDao instance = null;

	public ClientDao() {
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String FIND_CLIENTS_MAIL = "SELECT id, nom, prenom, email, naissance FROM Client WHERE email=?;";
	private static final String FIND_CLIENT_BY_VEHICLES= "SELECT * FROM Client INNER JOIN Reservation ON Reservation.client_id=Client.id WHERE Reservation.vehicle_id=?;";
	private static final String FIND_CLIENT_BY_RENT= "SELECT * FROM Client INNER JOIN Reservation ON Reservation.client_id=Client.id WHERE Reservation.id=?;";
	private static final String EDIT_CLIENT = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	/**
	 * Permets de creer un client
	 * @param client
	 * @return
	 * @throws DaoException
	 */
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.execute();
			int id =0;
			ResultSet resultset = ps.getGeneratedKeys();
			if (resultset.next()) {
				id = resultset.getInt(1);
			}
			return id;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}
	/**
	 * Permets de modifier les informations du client
	 * @param client
	 * @return
	 * @throws DaoException
	 */
	public void edit(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_CLIENT)) {


			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setLong(5,client.getId());
			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);

		}

	}



	/**
	 * Permets de supprimer un client
	 * @param id
	 * @return
	 * @throws DaoException
	 */public long delete(int id) throws DaoException, SQLException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			ps.setInt(1,id);
			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	/**
	 * Permets de trouver un client par son id
	 * @param id
	 * @return le client recherché
	 * @throws DaoException
	 */
	public Client findById(long id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {
			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate date = rs.getDate("naissance").toLocalDate();
			return new Client((int) id, nom, prenom, email, date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	/**
	 * Permets de trouver un client par son mail
	 * @param email
	 * @return le client avec l'email associé
	 * @throws DaoException
	 */
	public Client findByEmail(String email) throws DaoException {
		// Connexion à la base de données
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_MAIL)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				LocalDate date = rs.getDate("naissance").toLocalDate();
				return new Client(id, nom, prenom, email, date);
			}
		else{
			ps.close();
			connection.close();
			return null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
		throw new DaoException();

	}

	}
	/**
	 * Permets de trouver tous les clients
	 * @param
	 * @return tous les clients de base
	 * @throws DaoException
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate date = rs.getDate("naissance").toLocalDate();
				clients.add(new Client(id, nom, prenom, email, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clients;
	}
	/**
	 * Permets de trouver un client par le vehicule associé dans la réservation recherché
	 * @param vehicle_id
	 * @return un client
	 * @throws DaoException
	 */
	public List<Client> findByVehicleId(long vehicle_id) throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_BY_VEHICLES))
		 {
			pstatement.setLong(1, vehicle_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, vehicle_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getDate("naissance").toLocalDate()));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return clients;
	}
	/**
	 * Permets de trouver un client dans la réservation recherché
	 * @param rent_id
	 * @return un client
	 * @throws DaoException
	 */
	public List<Client> findByReservationClient(long rent_id) throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_CLIENT_BY_RENT)
		) {
			pstatement.setLong(1, rent_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, rent_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getDate("naissance").toLocalDate()));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return clients;
	}

}
