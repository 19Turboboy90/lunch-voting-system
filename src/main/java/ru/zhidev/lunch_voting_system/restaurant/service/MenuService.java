package ru.zhidev.lunch_voting_system.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.common.error.NotFoundException;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.repository.MenuRepository;
import ru.zhidev.lunch_voting_system.restaurant.repository.RestaurantRepository;
import ru.zhidev.lunch_voting_system.restaurant.to.MenuTo;
import ru.zhidev.lunch_voting_system.restaurant.util.MenuUtil;

import java.util.List;

import static ru.zhidev.lunch_voting_system.app.config.CacheConfig.MENUS;
import static ru.zhidev.lunch_voting_system.app.config.CacheConfig.MENU_BY_ID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = {MENUS, MENU_BY_ID}, allEntries = true)
    public Menu save(MenuTo menuTo, int restaurantId) {
        log.info("save: menuTo = {}, restaurantId =  {}", menuTo, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        Menu menu = MenuUtil.createNewFromTo(menuTo, restaurant);

        return repository.save(menu);
    }

    @Transactional
    @CacheEvict(value = {MENUS, MENU_BY_ID}, allEntries = true)
    public void update(MenuTo menuTo, int restaurantId) {
        log.info("update: menuTo = {}, restaurantId =  {}", menuTo, restaurantId);
        Menu menu = getMenu(menuTo.getId(), restaurantId);

        repository.save(MenuUtil.updateFromTo(menu, menuTo));
    }

    @Transactional
    @CacheEvict(value = {MENUS, MENU_BY_ID}, allEntries = true)
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