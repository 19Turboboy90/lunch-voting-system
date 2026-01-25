package ru.zhidev.lunchvotingsystem.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhidev.lunchvotingsystem.AbstractServiceTest;
import ru.zhidev.lunchvotingsystem.common.error.NotFoundException;
import ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;

import java.util.Collections;
import java.util.List;

import static ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void save() {
        Restaurant restaurant = getNew();
        Restaurant save = service.save(restaurant);
        RESTAURANT_MATCHER.assertMatch(save, service.getById(save.getId()));
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();

        service.update(updated);
        Restaurant actual = service.getById(updated.getId());

        RESTAURANT_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        Restaurant delete = restaurant1;
        service.delete(delete.getId());
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(delete.getId()));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.delete(RestaurantTestData.NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHER.assertMatch(all, restaurant1, restaurant2);
    }

    @Test
    void getById() {
        Restaurant byId = service.getById(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(byId, restaurant1);
    }

    @Test
    void getByIdNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(RestaurantTestData.NOT_FOUND));
    }

    @Test
    void getByName() {
        List<Restaurant> restaurants = service.getByName("resta");
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurant1, restaurant2);
    }

    @Test
    void getByNameNotFound() {
        RESTAURANT_MATCHER.assertMatch(service.getByName("empty"), Collections.emptyList());
    }
}