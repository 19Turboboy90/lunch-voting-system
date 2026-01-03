package ru.zhidev.lunch_voting_system.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Menu save(MenuTo menuTo, int restaurantId) {
        log.info("save: menu = {}, restaurantId =  {}", menuTo, restaurantId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        Menu menu = MenuUtil.createNewFromTo(menuTo, restaurant);

        return repository.save(menu);
    }

    @Transactional
    public void update(MenuTo menuTo, int restaurantId) {
        log.info("update: menu = {}, restaurantId =  {}", menuTo, restaurantId);
        Menu menu = getMenu(menuTo.getId(), restaurantId);

        repository.save(MenuUtil.updateFromTo(menu, menuTo));
    }

    @Transactional
    public void delete(int menuId, int restaurantId) {
        log.info("delete: menuId = {}", menuId);
        restaurantRepository.getReferenceById(restaurantId);
        repository.deleteExisted(menuId);
    }

    public List<Menu> getAll(int restaurantId) {
        log.info("getAll");
        restaurantRepository.getExisted(restaurantId);
        return repository.getAll(restaurantId);
    }

    public Menu getById(int menuId, int restaurantId) {
        log.info("get: restaurantId = {}, menuId = {}", restaurantId, menuId);
        return getMenu(menuId, restaurantId);
    }

    private Menu getMenu(int menuId, int restaurantId) {
        return repository.getByMenuIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException("Menu with id=" + menuId +
                        " for restaurant with id = " + restaurantId + " not found"));
    }
}