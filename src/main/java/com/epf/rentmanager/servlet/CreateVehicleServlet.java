package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/create")
public class CreateVehicleServlet extends HttpServlet {
    @Autowired
     VehicleService vehicleService;
    /**
     *
     */

    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {
        Vehicle vehicle = new Vehicle();
        String constructeur = request.getParameter("manufacturer");
        int nb_place = Integer.parseInt(request.getParameter("seats"));
        vehicle.setConstructeur(constructeur);
        vehicle.setNb_places(nb_place);
        boolean nbPlaces = vehicle.isNbPlaces_TwoNine(vehicle);
        try {
            if(nbPlaces == true) {
                vehicleService.create(vehicle);
                this.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp")
                        .forward(request, response);
            }

            if(nbPlaces == false) {
                response.getWriter().write(" error nombre de places");
            }

        }  catch (ServiceException e) {
            e.printStackTrace();
        }

    }

}
