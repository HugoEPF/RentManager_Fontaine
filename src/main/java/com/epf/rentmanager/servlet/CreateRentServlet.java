package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
    @Autowired
     VehicleService vehicleService;
    @Autowired
     ClientService clientService;
    @Autowired
     ReservationService reservationService;


    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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
        boolean reservationLimit = rent.isCarNotRentUnder7days(rent);

        try {
            if(reservationLimit == true) {
                reservationService.create(rent);
                this.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/rents/create.jsp")
                        .forward(request, response);
            }

            if(reservationLimit == false) {
                response.getWriter().write("error rent");
            }

        }  catch (ServiceException e) {
            e.printStackTrace();
        }

    }

}

