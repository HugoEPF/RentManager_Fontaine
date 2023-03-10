package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);


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
			ps.close();
			connection.close();
			return id;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}
	
	public long delete(Reservation reservation) throws DaoException {
		return 0;
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				int vehicule_id = rs.getInt("vehicule_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, (int) clientId, vehicule_id, debut, fin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
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

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
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
