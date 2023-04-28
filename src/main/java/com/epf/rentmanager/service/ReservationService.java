package com.epf.rentmanager.service;


import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
@Service
public class ReservationService {
    @Autowired
    private ReservationDao reservationDao;

    private ReservationService() {
        this.reservationDao = reservationDao;
    }
    /**
     * Permets de créer une réservation
     * @param reservation
     * @return
     * @throws DaoException
     */
    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
    /**
     * Permets de trouver des réservations par rapport aux clients
     * @param id
     * @return liste de réservation
     * @throws DaoException
     */
    public List<Reservation> findByResaByClientId(long id) throws ServiceException {
        if(id<0) {
            throw new ServiceException();

        }
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }


    }
    /**
     * Permets de trouver des réservations par rapport aux véhicules
     * @param id
     * @return liste de réservation
     * @throws DaoException
     */
    public List<Reservation> findByResaByVehicleId(long id) throws ServiceException {
        if(id<0) {
            throw new ServiceException();

        }
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }


    }

    /**
     * Permets de trouver toutes les réservations
     * @param
     * @return liste de réservation
     * @throws DaoException
     */
    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();

        }  catch(DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

    }

    /**
     * Permets de supprimer une réservation
     * @param id
     * @return
     * @throws DaoException
     */
    public long delete(int id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch (DaoException | SQLException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

    }
    /**
     * Permets de modifier une réservation
     * @param reservation
     * @return liste de réservation
     * @throws DaoException
     */
    public void edit(Reservation reservation) throws ServiceException {
        try {
            reservationDao.edit(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

    }
    /**
     * Permets de trouver une réservation
     * @param rent_id
     * @return une réservation
     * @throws DaoException
     */
    public Reservation findResaById(int rent_id) throws ServiceException {
        try {
            return reservationDao.findResaById(rent_id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

    }
    /**
     * Permets de compter le nombre de réservations
     * @param
     * @return un nombre
     * @throws DaoException
     */
    public int count() throws DaoException {


        try {
            return reservationDao.findAll().size();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }
}
