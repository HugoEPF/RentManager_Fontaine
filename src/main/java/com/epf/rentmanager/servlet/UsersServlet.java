package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ClientService clientService = ClientService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("users", clientService.findAll());
        } catch ( ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/users/list.jsp")
                .forward(request, response);

    }

}
