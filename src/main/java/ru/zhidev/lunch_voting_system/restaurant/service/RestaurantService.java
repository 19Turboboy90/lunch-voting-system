package ru.zhidev.lunch_voting_system.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.repository.RestaurantRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        log.info("save {}", restaurant);
        return repository.save(restaurant);
    }

    @Transactional
    public void update(Restaurant restaurant) {
        log.info("update {}", restaurant);
        repository.getExisted(restaurant.getId());
        repository.save(restaurant);
    }

    @Transactional
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    public Restaurant getById(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    public List<Restaurant> getByName(String name) {
        log.info("getByName {}", name);
        return repository.getByName(name);
    }
}
