package ru.zhidev.lunchvotingsystem.restaurant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;
import ru.zhidev.lunchvotingsystem.restaurant.service.MenuService;
import ru.zhidev.lunchvotingsystem.restaurant.to.MenuTo;

import java.net.URI;

import static ru.zhidev.lunchvotingsystem.common.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminMenuRestController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final MenuService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@PathVariable int restaurantId, @Valid @RequestBody MenuTo menu) {
        log.info("create: restaurantId = {}, menu = {}", restaurantId, menu);
        Menu created = service.create(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{menuId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody MenuTo menu, @PathVariable int menuId) {
        log.info("update: restaurantId =  {}, menu {}, with id={}", restaurantId, menu, menuId);
        assureIdConsistent(menu, menuId);
        service.update(menu, restaurantId);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("delete: restaurantId =  {}, menuId = {}", restaurantId, menuId);
        service.delete(menuId, restaurantId);
    }
}