package ru.zhidev.lunch_voting_system.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Profile("!test")
public class CacheConfig {

    public static final String USERS = "users";
    public static final String RESTAURANTS = "restaurants";
    public static final String RESTAURANT_BY_ID = "restaurantById";
    public static final String RESTAURANT_BY_NAME = "restaurantByName";
    public static final String MENUS = "menus";
    public static final String MENU_BY_ID = "menuById";
    public static final String DISHES = "dishes";
    public static final String DISH_BY_ID = "dishById";

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(500);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                USERS, RESTAURANTS, RESTAURANT_BY_ID, RESTAURANT_BY_NAME, MENUS, MENU_BY_ID, DISHES, DISH_BY_ID
        );
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}