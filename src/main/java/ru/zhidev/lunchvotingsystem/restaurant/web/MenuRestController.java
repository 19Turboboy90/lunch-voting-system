package ru.zhidev.lunchvotingsystem.restaurant.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;
import ru.zhidev.lunchvotingsystem.restaurant.service.MenuService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuRestController {

    static final String REST_URL = "/api/restaurants/{restaurantId}/menus";

    private final MenuService service;

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll: restaurantId  = {}", restaurantId);
        return service.getAll(restaurantId);
    }

    @GetMapping("/{menuId}")
    public Menu getById(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get: restaurantId = {}, menuId = {}", restaurantId, menuId);
        return service.getById(menuId, restaurantId);
    }
}