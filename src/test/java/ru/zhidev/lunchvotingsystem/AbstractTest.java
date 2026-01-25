package ru.zhidev.lunchvotingsystem;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTest {
    @MockitoBean
    protected Clock clock;

    @Autowired
    EntityManagerFactory emf;

    protected final ZoneId zone = ZoneId.systemDefault();

    @BeforeEach
    void setup() {
        when(clock.getZone()).thenReturn(zone);
    }

    protected void mockTime(LocalDate date, LocalTime time) {
        LocalDateTime fixedTime = LocalDateTime.of(date, time);
        when(clock.instant()).thenReturn(fixedTime.atZone(zone).toInstant());
    }

    @Test
    void checkCacheDisabled() {
        assertFalse(
                Boolean.parseBoolean(
                        String.valueOf(emf.getProperties()
                                .getOrDefault("hibernate.cache.use_second_level_cache", "false"))
                )
        );
    }
}
