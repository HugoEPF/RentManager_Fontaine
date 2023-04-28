package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/rents/edit")
public class EditRentServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {
            int id = Integer.parseInt(request.getParameter("id").toString());
            final Reservation rents = reservationService.findResaById(id);
            request.setAttribute("rents", rents);
            request.setAttribute("clientObj", clientService.findAll());
            request.setAttribute("carObj", vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        //request.setAttribute("clients", clients);

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/rents/edit.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Reservation rent = new Reservation();
        int vehicle_id = Integer.parseInt(request.getParameter("car"));
        int client_id = Integer.parseInt(request.getParameter("client"));
        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));
        rent.setId(id);
        rent.setClient_id(client_id);
        rent.setVehicule_id(vehicle_id);
        rent.setDebut(debut);
        rent.setFin(fin);
        boolean reservationLimit = rent.isCarNotRentUnder7days(rent);
        boolean reservationDate = false;
        try {
            reservationDate = rent.isNotTheSameDay(rent, reservationService);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            if(reservationLimit == true && reservationDate == true) {
                reservationService.edit(rent);
                response.sendRedirect("../rents");
            }
            if(reservationLimit == false) {
                response.getWriter().write("error rent");
            }
            if(reservationDate == false) {
                response.getWriter().write("error Voiture déja réservé ce jour");
            }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
