package ru.zhidev.lunch_voting_system.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhidev.lunch_voting_system.AbstractTest;
import ru.zhidev.lunch_voting_system.common.error.NotFoundException;
import ru.zhidev.lunch_voting_system.restaurant.MenuTestData;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.to.DishTo;
import ru.zhidev.lunch_voting_system.restaurant.util.DishUtil;

import java.util.List;

import static ru.zhidev.lunch_voting_system.restaurant.DishTestData.*;

class DishServiceTest extends AbstractTest {

    @Autowired
    private DishService service;

    @Test
    void save() {
        DishTo dishTo = getNewTo();
        Dish save = service.save(dishTo, MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID);
        DISH_MATCHER.assertMatch(save, service.getById(save.getId(), MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void update() {
        DishTo updatedTo = getUpdatedTo();

        service.update(updatedTo, MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID);
        Dish actual = service.getById(updatedTo.getId(), MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID);
        Dish expected = DishUtil.updateFromTo(getUpdated(), updatedTo);

        DISH_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        Dish delete = dish1;
        service.delete(delete.getId(), MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID);
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(delete.getId(), MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.delete(NOT_FOUND, MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void getAll() {
        List<Dish> all = service.getAll(RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID);
        DISH_MATCHER.assertMatch(all, dish1, dish2);
    }

    @Test
    void getById() {
        Dish byId = service.getById(DISH_ID, MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID);
        DISH_MATCHER.assertMatch(byId, dish1);
    }

    @Test
    void getByIdNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(NOT_FOUND, MenuTestData.MENU_ID, RestaurantTestData.RESTAURANT_ID));
    }
}