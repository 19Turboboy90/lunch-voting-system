package ru.zhidev.lunch_voting_system.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.repository.RestaurantRepository;

import java.util.List;

import static ru.zhidev.lunch_voting_system.app.config.CacheConfig.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    @CacheEvict(value = {RESTAURANTS, RESTAURANT_BY_ID, RESTAURANT_BY_NAME}, allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        log.info("save {}", restaurant);
        return repository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = {RESTAURANTS, RESTAURANT_BY_ID, RESTAURANT_BY_NAME}, allEntries = true)
    public void update(Restaurant restaurant) {
        log.info("update {}", restaurant);
        repository.getExisted(restaurant.getId());
        repository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = {RESTAURANTS, RESTAURANT_BY_ID, RESTAURANT_BY_NAME}, allEntries = true)
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
