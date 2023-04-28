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
	
	private VehicleService() {
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

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

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}

	public long delete(int id) throws ServiceException {
		try {
			return vehicleDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public List<Vehicle> findByReservationVehicle(long rent_id) throws ServiceException {
		try {
			return vehicleDao.findByReservationVehicle(rent_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}


	}


	public int count() throws DaoException {


		try {
			return vehicleDao.findAll().size();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
}
