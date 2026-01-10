package ru.zhidev.lunch_voting_system.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

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
                .maximumSize(500);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(
                new CaffeineCache(USERS, caffeine.build()),
                new CaffeineCache(RESTAURANTS, caffeine.build()),
                new CaffeineCache(RESTAURANT_BY_ID, caffeine.build()),
                new CaffeineCache(RESTAURANT_BY_NAME, caffeine.build()),
                new CaffeineCache(MENUS, caffeine.build()),
                new CaffeineCache(MENU_BY_ID, caffeine.build()),
                new CaffeineCache(DISHES, caffeine.build()),
                new CaffeineCache(DISH_BY_ID, caffeine.build())
        ));
        return cacheManager;
    }
}