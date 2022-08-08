package com;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class CryptoPricesTrackerApplicationTest {

    @Test
    void test() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            MockedStatic.Verification verification = () -> SpringApplication.run(CryptoPricesTrackerApplication.class);
            mocked.when(verification).thenReturn(Mockito.mock(ConfigurableApplicationContext.class));
            CryptoPricesTrackerApplication.main(new String[]{});
            mocked.verify(verification);
        }
    }
}
