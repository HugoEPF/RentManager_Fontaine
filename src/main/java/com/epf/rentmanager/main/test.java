package com.epf.rentmanager.main;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        //System.out.println(new Client(12, "Hugo","Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.now()));

        try {
            System.out.println(ClientService.getInstance().findById(2));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
