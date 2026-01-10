package ru.zhidev.lunch_voting_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class AbstractTest {
    @MockitoBean
    protected Clock clock;

    @Autowired(required = false)
    CacheManager cacheManager;

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
        System.out.println("CacheManager = " + cacheManager);
    }
}
