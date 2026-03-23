package fr.gopartner.tregusto.authentication.infrastructure.web;


import fr.gopartner.tregusto.authentication.api.generated.ApplicationStatusDTO;
import fr.gopartner.tregusto.authentication.api.generated.AuthenticationApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationApiImpl implements AuthenticationApi, ApiV1Authentication {


    @Override
    public ResponseEntity<ApplicationStatusDTO> getAppStatus() {
        return ResponseEntity.ok(new ApplicationStatusDTO().status("Spring boot is working!"));
    }
}
