package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT * FROM Reservation WHERE id=?;";
	private static final String EDIT_RESERVATION = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	/**
	 * Permets de creer une réservation
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public long create(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicule_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.execute();
			ResultSet resultset = ps.getGeneratedKeys();
			int id = 0;
			if(resultset.next()) {
				id = resultset.getInt(1);
			}
			return id;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}
	/**
	 * Permets de modifier une réservation
	 * @param reservation
	 * @return
	 * @throws DaoException
	 */
	public void edit(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_RESERVATION)) {

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicule_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5,reservation.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);

		}

	}
	/**
	 * Permets de supprimer une réservation
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_RESERVATION_QUERY);
			ps.setInt(1,id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	/**
	 * Permets d'afficher une liste de réservation par rapport à son client id
	 * @param clientId
	 * @return une liste de réservation
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			statement.setLong(1,clientId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, (int) clientId, vehicle_id, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}
	/**
	 * Permets d'afficher une liste de réservation par rapport à son vehicle id
	 * @param vehicleId
	 * @return une liste de réservation
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
			statement.setLong(1,vehicleId);
			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client_id, (int) vehicleId, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}
	/**
	 * Permets de trouver une réservation par rapport à son id
	 * @param rent_id
	 * @return une réservation
	 * @throws DaoException
	 */
	public Reservation findResaById(long rent_id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_RESERVATION_QUERY)) {

			pstatement.setLong(1,rent_id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			int client_id = rs.getInt("client_id");
			int vehicle_id = rs.getInt("vehicle_id");
			LocalDate debut = rs.getDate("debut").toLocalDate();
			LocalDate fin = rs.getDate("fin").toLocalDate();
			return new Reservation((int) rent_id, client_id, vehicle_id, debut, fin);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	/**
	 * Permets d'afficher une liste de toutes les réservations
	 * @param
	 * @return une liste de réservation
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement()) {

			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client_id, vehicle_id, debut, fin));
			}
			connection.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}


}
