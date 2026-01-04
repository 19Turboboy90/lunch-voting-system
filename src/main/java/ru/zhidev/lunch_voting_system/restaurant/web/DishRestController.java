package ru.zhidev.lunch_voting_system.restaurant.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.service.DishService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishRestController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final DishService service;

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("getAll: restaurantId = {}, menuId = {} ", restaurantId, menuId);
        return service.getAll(restaurantId, menuId);
    }

    @GetMapping("/{dishId}")
    public Dish getById(@PathVariable int restaurantId,
                        @PathVariable int menuId,
                        @PathVariable int dishId) {
        log.info("get: restaurantId = {}, menuId = {}, dishId = {}", restaurantId, menuId, dishId);
        return service.getById(dishId, menuId, restaurantId);
    }
}
