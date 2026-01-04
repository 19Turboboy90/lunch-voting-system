package ru.zhidev.lunch_voting_system.restaurant.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantRestController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable int id) {
        log.info("get {}", id);
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<Restaurant> getByName(@RequestParam String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }
}
