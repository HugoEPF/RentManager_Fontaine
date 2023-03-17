package epf.rentmanager.service;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientServiceTest {
    @Test
     void isCreate() {
        // Given
        Client createClient = new Client(1,"John", "Doe", "john.doe@ensta.fr", LocalDate.now());
        ClientService clientService = new ClientService;
        // Then
        //assertThrows(clientService.create(createClient));
        assertThrows(clientService.create(createClient));
    }


}


