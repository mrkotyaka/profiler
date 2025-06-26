package mrkotyaka.profiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfilerApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final GenericContainer<?> myAppDev = new  GenericContainer<>("devapp:latest").withExposedPorts(8080);
    private static final GenericContainer<?> myAppProd = new  GenericContainer<>("prodapp:latest").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myAppDev.start();
        myAppProd.start();
    }

    @Test
    void contextLoadsDev() {
        String expected = "Current profile is dev";
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + myAppDev.getMappedPort(8080) + "/profile", String.class);
        System.out.println(forEntityDev.getBody());

        Assertions.assertEquals(expected, forEntityDev.getBody());
    }

    @Test
    void contextLoadsProd() {
        String expected = "Current profile is production";
        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + myAppProd.getMappedPort(8081) + "/profile", String.class);
        System.out.println(forEntityProd.getBody());

        Assertions.assertEquals(expected, forEntityProd.getBody());
    }
}
