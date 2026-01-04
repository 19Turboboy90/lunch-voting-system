package ru.zhidev.lunch_voting_system.restaurant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.service.DishService;
import ru.zhidev.lunch_voting_system.restaurant.to.DishTo;

import java.net.URI;

import static ru.zhidev.lunch_voting_system.common.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminDishRestController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final DishService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> save(@PathVariable int restaurantId,
                                     @PathVariable int menuId,
                                     @Valid @RequestBody DishTo dish) {
        log.info("save: restaurantId = {}, menuId ={}, dish = {}", restaurantId, menuId, dish);
        Dish created = service.save(dish, menuId, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{dishId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId,
                       @PathVariable int menuId,
                       @Valid @RequestBody DishTo dish,
                       @PathVariable int dishId) {
        log.info("update: restaurantId =  {}, menuId = {}, dishTo {}, with id={}", restaurantId, menuId, dish, dishId);
        assureIdConsistent(dish, dishId);
        service.update(dish, menuId, restaurantId);
    }


    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId,
                       @PathVariable int menuId,
                       @PathVariable int dishId) {
        log.info("delete: restaurantId =  {}, menuId = {}, dishId = {}", restaurantId, menuId, dishId);
        service.delete(dishId, menuId, restaurantId);
    }
}