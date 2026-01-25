package ru.zhidev.lunchvotingsystem.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhidev.lunchvotingsystem.AbstractServiceTest;
import ru.zhidev.lunchvotingsystem.common.error.NotFoundException;
import ru.zhidev.lunchvotingsystem.restaurant.MenuTestData;
import ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;
import ru.zhidev.lunchvotingsystem.restaurant.to.MenuTo;
import ru.zhidev.lunchvotingsystem.restaurant.util.MenuUtil;

import java.util.List;

import static ru.zhidev.lunchvotingsystem.restaurant.DishTestData.NOT_FOUND;
import static ru.zhidev.lunchvotingsystem.restaurant.MenuTestData.*;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void save() {
        MenuTo menuTo = getNewTo();
        Menu save = service.save(menuTo, RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(save, service.getById(save.getId(), RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void update() {
        MenuTo updatedTo = getUpdatedTo();

        service.update(updatedTo, RestaurantTestData.RESTAURANT_ID);
        Menu actual = service.getById(updatedTo.getId(), RestaurantTestData.RESTAURANT_ID);
        Menu expected = MenuUtil.updateFromTo(getUpdated(), updatedTo);

        MENU_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        Menu delete = menu1;
        service.delete(delete.getId(), RestaurantTestData.RESTAURANT_ID);
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(delete.getId(), RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.delete(MenuTestData.NOT_FOUND, RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void getAll() {
        List<Menu> all = service.getAll(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(all, menu1);
    }

    @Test
    void getById() {
        Menu byId = service.getById(MENU_ID, RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(byId, menu1);
    }

    @Test
    void getByIdNotFound() {
        Assertions.assertThrows(NotFoundException.class, () ->
                service.getById(NOT_FOUND, RestaurantTestData.RESTAURANT_ID));
    }
}