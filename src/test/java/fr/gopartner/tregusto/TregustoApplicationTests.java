package fr.gopartner.tregusto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class TregustoApplicationTests {

    @Test
    void validate_architecture() {
        var modules = ApplicationModules.of(TregustoApplication.class).verify();
        System.out.println(modules);
        new Documenter(modules).writeDocumentation();
    }

}
