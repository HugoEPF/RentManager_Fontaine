package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Autowired
     private ClientService clientService;


    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/users/create.jsp")
                .forward(request, response);

    }

    protected void doPost(HttpServletRequest   request,   HttpServletResponse response)
            throws ServletException,  IOException {
        Client client = new Client();
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNaissance(naissance);
        boolean ageLegal = client.isLegal(client);
        boolean nameCharacter = client.isNameNotLong(client);


        boolean mailUser = false;
        try {
            mailUser = client.isMailTheSame(client, clientService);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }


        try {

            if(ageLegal == true  && nameCharacter == true && mailUser == true) { //&& mail == true) {

                clientService.create(client);
                response.sendRedirect("../users");

            } if(ageLegal == false){
               response.getWriter().write("Erreur : vous êtes mineur\n");

            } if(nameCharacter == false){
                response.getWriter().write("Erreur : Nom/Prénom faisant moins de 3 caractères\n");

            } if(mailUser == false) {
                response.getWriter().write("Erreur : Nom/Prénom faisant moins de 3 caractères\n");
            }

        }  catch (ServiceException e) {
            e.printStackTrace();
        }

    }



}
