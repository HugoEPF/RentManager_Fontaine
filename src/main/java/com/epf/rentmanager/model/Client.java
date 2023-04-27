package com.epf.rentmanager.model;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;

import java.time.LocalDate;
import java.time.Period;

public class Client {
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(int id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public static boolean isLegal(Client client) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(client.getNaissance(), currentDate);
        int age = period.getYears();
        System.out.println(age);
        return age >= 18;
    }

    public static boolean isNameNotLong(Client client) {
        return client.getNom().length() >= 3 && client.getPrenom().length() >= 3;
    }

    public static boolean isMailTheSame(Client client, ClientService clientService) throws DaoException {
        boolean mailIdentique = true;
        Client clientMail = clientService.findByEmail(client.getEmail());
        if (clientMail != null && client.getEmail() == clientMail.getEmail()) { // Si identique
            mailIdentique = false;
        }
        return mailIdentique;
   }



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
