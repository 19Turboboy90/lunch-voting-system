package ru.zhidev.lunchvotingsystem.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;
import ru.zhidev.lunchvotingsystem.restaurant.repository.RestaurantRepository;

import java.util.List;

import static ru.zhidev.lunchvotingsystem.app.config.CacheConfig.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = RESTAURANTS, allEntries = true),
            @CacheEvict(value = RESTAURANT_BY_NAME, allEntries = true)
    })
    public Restaurant save(Restaurant restaurant) {
        log.info("save {}", restaurant);
        return repository.save(restaurant);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = RESTAURANTS, allEntries = true),
            @CacheEvict(value = RESTAURANT_BY_NAME, allEntries = true),
            @CacheEvict(value = RESTAURANT_BY_ID, key = "#restaurant.id")
    })
    public void update(Restaurant restaurant) {
        log.info("update {}", restaurant);
        repository.getExisted(restaurant.getId());
        repository.save(restaurant);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = RESTAURANTS, allEntries = true),
            @CacheEvict(value = RESTAURANT_BY_ID, key = "#id"),
            @CacheEvict(value = RESTAURANT_BY_NAME, allEntries = true),
            @CacheEvict(value = MENUS, allEntries = true),
            @CacheEvict(value = MENU_BY_ID, allEntries = true),
            @CacheEvict(value = DISHES, allEntries = true),
            @CacheEvict(value = DISH_BY_ID, allEntries = true)
    })
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @Cacheable(RESTAURANTS)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @Cacheable(value = RESTAURANT_BY_ID, key = "#id")
    public Restaurant getById(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @Cacheable(value = RESTAURANT_BY_NAME, key = "#name")
    public List<Restaurant> getByName(String name) {
        log.info("getByName {}", name);
        return repository.getByName(name);
    }
}
