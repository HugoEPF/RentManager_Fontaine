package epf.rentmanager.util;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientTest {
    ClientService clientService;
    @Test
    void isLegal_return_true() {
        // Given
        Client client = new Client(12, "Hugo","Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.of(2006,02,05));

    //Then
        assertTrue(client.isLegal(client));


    }

    @Test
    void isLongName_true() {
        // Given
        Client client = new Client(12, "J", "Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.of(2006, 02, 05));

        //Then
        assertTrue(client.isNameNotLong(client));
    }
    @Test
    void isLongName_false() {
        // Given
        Client client = new Client(12, "Hugo", "Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.of(2006, 02, 05));

        //Then
        assertFalse(client.isNameNotLong(client));
    }

    @Test
    void isMailTheSame_True() throws DaoException {
        Client client = new Client(12, "Hugo", "Fontaine", "hugo.fontaine@epfedu.fr", LocalDate.of(2006, 02, 05));
        assertTrue(client.isMailTheSame(client, clientService));
    }
}
