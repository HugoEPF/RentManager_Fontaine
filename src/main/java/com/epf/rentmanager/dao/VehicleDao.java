package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String FIND_VEHICLES_BY_CLIENT = "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.client_id=?;";
	private static final String FIND_VEHICLE_BY_RENT= "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.id=?;";
	private static final String EDIT_VEHICLE = "UPDATE Vehicle SET constructeur=?, nb_places=? WHERE id=?;";
	/**
	 * Permets de créer un véhicule
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public long create(Vehicle vehicle) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.execute();
			int id = 0;
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
	 * Permets de modifier un véhicule
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public void edit(Vehicle vehicle) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(EDIT_VEHICLE)) {

			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.setLong(3,vehicle.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);

		}

	}
	/**
	 * Permets de supprimer un véhicule
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws DaoException, SQLException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			ps.setInt(1,id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	/**
	 * Permets de trouver un véhicule par rapport à son id
	 * @param id
	 * @return un véhicule
	 * @throws DaoException
	 */
	public Vehicle findById(long id) throws DaoException {

		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {

			pstatement.setLong(1,id);
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			String constructeur = rs.getString("constructeur");
			int nb_places = rs.getInt("nb_places");
			return new Vehicle((int) id, constructeur, nb_places);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	/**
	 * Permets de récupérer tous les véhicules
	 * @param
	 * @return une liste de véhicule
	 * @throws DaoException
	 */
	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement()) {

			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				int nbPlaces = rs.getInt("nb_places");
				vehicles.add(new Vehicle(id, constructeur, nbPlaces));
			}
		}  catch (SQLException e) {
			e.printStackTrace();

		}
		return vehicles;
		
	}
	/**
	 * Permets de récupérer les véhicules par rapport aux clients id dans la réservation
	 * @param
	 * @return une liste de véhicule
	 * @throws DaoException
	 */
	public List<Vehicle> findByClientId(long client_id) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLES_BY_CLIENT);
		) {
			pstatement.setLong(1, client_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, client_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"),rs.getInt("nb_places")));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return vehicles;
	}
	/**
	 * Permets de récupérer les véhicules dans les réservations
	 * @param
	 * @return une liste de véhicule
	 * @throws DaoException
	 */
	public List<Vehicle> findByReservationVehicle(long rent_id) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(FIND_VEHICLE_BY_RENT);
		) {
			pstatement.setLong(1, rent_id);
			ResultSet resultSet = pstatement.executeQuery();
			while(resultSet.next())
				pstatement.setLong(1, rent_id);
			ResultSet rs = pstatement.executeQuery();
			while(rs.next())
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"),rs.getInt("nb_places")));
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();

		}
		return vehicles;
	}



}
