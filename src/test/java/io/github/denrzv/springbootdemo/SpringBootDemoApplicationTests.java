package io.github.denrzv.springbootdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {
    private static final int DEV_PORT = 8080;
    private static final int PROD_PORT = 8081;
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(DEV_PORT);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(PROD_PORT);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> devEntity = restTemplate.
                getForEntity("http://localhost:" + devApp.getMappedPort(DEV_PORT) + "/profile", String.class);
        System.out.println(devEntity.getBody());

        ResponseEntity<String> prodEntity = restTemplate.
                getForEntity("http://localhost:" + prodApp.getMappedPort(PROD_PORT) + "/profile", String.class);
        System.out.println(prodEntity.getBody());

        Assertions.assertTrue(Objects.requireNonNull(devEntity.getBody()).contains("dev"));
        Assertions.assertTrue(Objects.requireNonNull(prodEntity.getBody()).contains("prod"));
    }
}
