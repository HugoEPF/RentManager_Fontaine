package com.epf.rentmanager.main;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.AppConfiguration;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class test {
    public static void main(String[] args) {



    Client client = new Client(12, "Hugo","Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.of(2006,02,05));
        System.out.println(client.isLegal(client));


    }
}
