package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rents/create")
public class CreateRentServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService =  VehicleService.getInstance();
    private ClientService clientService =  ClientService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("carObj", vehicleService.findAll());
            request.setAttribute("clientObj", clientService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/rents/create.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {
        Reservation rent = new Reservation();
        int vehicle_id = Integer.parseInt(request.getParameter("car"));
        int client_id = Integer.parseInt(request.getParameter("client"));
        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));
        rent.setClient_id(client_id);
        rent.setVehicule_id(vehicle_id);
        rent.setDebut(debut);
        rent.setFin(fin);

        try {
            ReservationService.getInstance().create(rent);
        }  catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/rents/create.jsp")
                .forward(request, response);
    }

}

