package com.epf.rentmanager.service;

import java.sql.*;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	@Autowired
	private ClientDao clientDao;
	public static ClientService instance;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	/**
	 * Permets de créer un client
	 * @param client
	 * @return
	 * @throws DaoException
	 */
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	/**
	 * Permets de supprimer un client
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public long delete(int id) throws ServiceException {
		try {
			return clientDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}
	/**
	 * Permets de trouver un client
	 * @param id
	 * @return un client
	 * @throws DaoException
	 */
	public Client findById(long id) throws ServiceException {
		if(id<0) {
			throw new ServiceException();
		}
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();

		}


	}
	/**
	 * Permets de trouver tous les clients
	 * @param
	 * @return une liste de clients
	 * @throws DaoException
	 */
	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();

		}  catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}
	/**
	 * Permets de trouver un client grâce à son email
	 * @param mail
	 * @return un client
	 * @throws DaoException
	 */
	public Client findByEmail(String mail) throws DaoException {
		try {
			return clientDao.findByEmail(mail);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	/**
	 * Permets de modifier un client
	 * @param client
	 * @return
	 * @throws DaoException
	 */
	public void edit(Client client) throws DaoException {
		try {
			clientDao.edit(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	/**
	 * Permets de trouver des clients grâce à leur véhicule
	 * @param vehicle_id
	 * @return une liste de clients
	 * @throws DaoException
	 */
	public List<Client> findByVehicleId(long vehicle_id) {
		try {
			return clientDao.findByVehicleId(vehicle_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Permets de trouver des clients grâce à leur réservation
	 * @param rent_id
	 * @return une liste de clients
	 * @throws DaoException
	 */
	public List<Client> findByReservationClient(long rent_id) {
		try {
			return clientDao.findByReservationClient(rent_id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Permets de compter le nombre de clients
	 * @param
	 * @return un nombre
	 * @throws DaoException
	 */
	public int count() throws DaoException {


		try {
			return clientDao.findAll().size();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
