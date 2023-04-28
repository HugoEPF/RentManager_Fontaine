package com.epf.rentmanager.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class VehicleService {
	@Autowired
	private VehicleDao vehicleDao;
	public static VehicleService instance;

	@Autowired
	private	ReservationService reservationService;
	
	private VehicleService() {
		this.vehicleDao = vehicleDao;
	}
	/**
	 * Permets de créer un véhicule
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}
	/**
	 * Permets de trouver un véhicule
	 * @param id
	 * @return un véhicule
	 * @throws DaoException
	 */
	public Vehicle findById(long id) throws ServiceException {
		if(id<0) {
			throw new ServiceException();

		}
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}
	/**
	 * Permets de trouver des véhicules par rapport aux clients
	 * @param client_id
	 * @return une liste de véhicule
	 * @throws DaoException
	 */
	public List<Vehicle> findByClientId(long client_id) throws ServiceException {
		if(client_id<0) {
			throw new ServiceException();

		}
		try {
			return vehicleDao.findByClientId(client_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}
	/**
	 * Permets de trouver tous les véhicules
	 * @param
	 * @return une liste de véhicules
	 * @throws DaoException
	 */
	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}
	/**
	 * Permets de supprimer un véhicule et les réservations intégrées
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws ServiceException {
		try {
			List<Reservation> rentVehicle = reservationService.findByResaByVehicleId(id);
			for(Reservation reservation : rentVehicle) {
				reservationService.delete((int) reservation.getId());
			}
			return vehicleDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}
	/**
	 * Permets de modifier un véhicule
	 * @param vehicle
	 * @return
	 * @throws DaoException
	 */
	public void edit(Vehicle vehicle) throws ServiceException {
		try {
			vehicleDao.edit(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}
	/**
	 * Permets de trouver des véhicules par rapport aux réservations
	 * @param rent_id
	 * @return une liste de véhicules
	 * @throws DaoException
	 */
	public List<Vehicle> findByReservationVehicle(long rent_id) throws ServiceException {
		try {
			return vehicleDao.findByReservationVehicle(rent_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}

	/**
	 * Permets de compter les véhicules
	 * @param
	 * @return un nombre
	 * @throws DaoException
	 */
	public int count() throws DaoException {


		try {
			return vehicleDao.findAll().size();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
}
