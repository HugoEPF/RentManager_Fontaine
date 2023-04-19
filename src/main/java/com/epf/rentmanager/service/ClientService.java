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
	
	//public static ClientService getInstance() {
		//if (instance == null) {
			//instance = new ClientService();
		//}
		
		//return instance;
	//}
	
	
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long delete(int id) throws ServiceException {
		try {
			return clientDao.delete(id);
		} catch (DaoException | SQLException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

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

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();

		}  catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public int count() throws DaoException {


		try {
			return clientDao.findAll().size();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
