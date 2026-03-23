package fr.gopartner.tregusto.authentication.integration;

import fr.gopartner.tregusto.authentication.api.generated.ApplicationStatusDTO;
import fr.gopartner.tregusto.authentication.infrastructure.web.AuthenticationApiImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.modulith.test.ApplicationModuleTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@ApplicationModuleTest
class AuthenticationApiIntegrationTest {

    @Autowired
    private AuthenticationApiImpl authenticationApi;

    @Test
    @DisplayName("GIVEN Authentication API WHEN getAppStatus() is called THEN should return expected message")
    void givenAuthenticationApi_whenGetAppStatusCalled_thenReturnExpectedMessage() {
        ResponseEntity<ApplicationStatusDTO> response = authenticationApi.getAppStatus();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be 200");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Spring boot is working!", response.getBody().getStatus(), "Response status should match expected message");
    }
}