package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/delete")
public class DeleteUserServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        System.out.println(Integer.parseInt(request.getParameter("id").toString()));
        clientService.delete(Integer.parseInt(request.getParameter("id").toString()));

    } catch (NumberFormatException | ServiceException e) {
        e.printStackTrace();
    }
        //response.sendRedirect("/rentmanager/users");
        this.getServletContext()
               .getRequestDispatcher("/WEB-INF/views/users/list.jsp")
               .forward(request, response);

    }
    }




