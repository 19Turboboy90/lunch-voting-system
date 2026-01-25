package ru.zhidev.lunchvotingsystem.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.error.NotFoundException;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;
import ru.zhidev.lunchvotingsystem.restaurant.repository.MenuRepository;
import ru.zhidev.lunchvotingsystem.restaurant.repository.RestaurantRepository;
import ru.zhidev.lunchvotingsystem.restaurant.to.MenuTo;
import ru.zhidev.lunchvotingsystem.restaurant.util.MenuUtil;

import java.util.List;

import static ru.zhidev.lunchvotingsystem.app.config.CacheConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = MENUS, key = "#restaurantId"),
            @CacheEvict(value = MENU_BY_ID, allEntries = true),
            @CacheEvict(value = DISHES, allEntries = true)
    })
    public Menu create(MenuTo menuTo, int restaurantId) {
        log.info("create: menuTo = {}, restaurantId =  {}", menuTo, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        Menu menu = MenuUtil.createNewFromTo(menuTo, restaurant);

        return repository.save(menu);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = MENUS, key = "#restaurantId"),
            @CacheEvict(value = MENU_BY_ID, key = "#menuTo.id"),
            @CacheEvict(value = DISHES, key = "#menuTo.id")
    })
    public void update(MenuTo menuTo, int restaurantId) {
        log.info("update: menuTo = {}, restaurantId =  {}", menuTo, restaurantId);
        Menu menu = getMenu(menuTo.getId(), restaurantId);

        repository.save(MenuUtil.updateFromTo(menu, menuTo));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = MENUS, key = "#restaurantId"),
            @CacheEvict(value = MENU_BY_ID, key = "#menuId"),
            @CacheEvict(value = DISHES, key = "#menuId"),
            @CacheEvict(value = DISH_BY_ID, allEntries = true)
    })
    public void delete(int menuId, int restaurantId) {
        log.info("delete: menuId = {}, restaurantId = {}", menuId, restaurantId);
        restaurantRepository.getExisted(restaurantId);
        repository.deleteExisted(menuId);
    }

    @Cacheable(value = MENUS, key = "#restaurantId")
    public List<Menu> getAll(int restaurantId) {
        log.info("getAll: restaurantId = {}", restaurantId);
        restaurantRepository.getExisted(restaurantId);
        return repository.getAll(restaurantId);
    }

    @Cacheable(value = MENU_BY_ID, key = "#menuId")
    public Menu getById(int menuId, int restaurantId) {
        log.info("get: menuId = {}, restaurantId = {}", menuId, restaurantId);
        return getMenu(menuId, restaurantId);
    }

    private Menu getMenu(int menuId, int restaurantId) {
        return repository.getByMenuIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException("Menu with id=" + menuId +
                        " for restaurant with id = " + restaurantId + " not found"));
    }
}