package com.epf.rentmanager.model;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Reservation {
    private long id;
    private int client_id;
    private int vehicule_id;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(int id, int client_id, int vehicule_id, LocalDate debut, LocalDate fin) {
    this.id = id;
    this.client_id = client_id;
    this.vehicule_id = vehicule_id;
    this.debut = debut;
    this.fin = fin;
    }

    public Reservation() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
    /**
     * Permets de vérifier si le véhicule n'est pas réservé plus de 7 jours de suite
     * @param rent
     * @return boolean
     * @throws DaoException
     */
    public static boolean isCarNotRentUnder7days(Reservation rent) {
        Period period = Period.between(rent.getDebut(),rent.getFin());
        return period.getDays() < 7;
    }
    /**
     * Permets de vérifier si un client ne réserve pas les mêmes dates pour un véhicule
     * @param rent
     * @return boolean
     * @throws DaoException
     */
    public static boolean isNotTheSameDay(Reservation rent, ReservationService rentService) throws ServiceException {
        List<Reservation> reservation;
        reservation = rentService.findByResaByVehicleId(rent.vehicule_id);
        for (int i = 0; i < reservation.size(); i++) {
            if (rent.getDebut().isAfter(reservation.get(i).getDebut()) && rent.getDebut().isBefore(reservation.get(i).getFin())) {
                return false;
            }
            if (rent.getFin().isAfter(reservation.get(i).getDebut()) && rent.getFin().isBefore(reservation.get(i).getFin())) {
                return false;
            }
            if(reservation.get(i).getDebut().isAfter(rent.getDebut()) && reservation.get(i).getDebut().isBefore(rent.getFin())) {
                return false;
            }
            if(reservation.get(i).getDebut().isEqual(rent.getDebut()) && reservation.get(i).getFin().isEqual(rent.getFin())) {
                return false;
            }

        }
        return true;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicule_id=" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
