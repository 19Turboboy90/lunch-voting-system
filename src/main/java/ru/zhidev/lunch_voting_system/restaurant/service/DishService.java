package ru.zhidev.lunch_voting_system.restaurant.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.common.error.NotFoundException;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;
import ru.zhidev.lunch_voting_system.restaurant.repository.DishRepository;
import ru.zhidev.lunch_voting_system.restaurant.to.DishTo;
import ru.zhidev.lunch_voting_system.restaurant.util.DishUtil;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class DishService {

    private final DishRepository repository;
    private final MenuService menuService;

    @Transactional
    public Dish save(@Valid DishTo dishTo, int menuId, int restaurantId) {
        log.info("save: dishTo = {}, menuId = {}, restaurantId =  {}", dishTo, menuId, restaurantId);
        Menu menu = menuService.getById(menuId, restaurantId);

        Dish dish = DishUtil.createNewFromTo(dishTo, menu);

        return repository.save(dish);
    }


    @Transactional
    public void update(@Valid DishTo dishTo, int menuId, int restaurantId) {
        log.info("update: dishTo = {}, menuId = {}, restaurantId =  {}", dishTo, menuId, restaurantId);
        Menu menu = menuService.getById(menuId, restaurantId);
        Dish dish = getDish(dishTo.getId(), menu.getId());

        repository.save(DishUtil.updateFromTo(dish, dishTo));
    }


    @Transactional
    public void delete(int dishId, int menuId, int restaurantId) {
        log.info("delete: dishTo = {}, menuId = {}, restaurantId =  {}", dishId, menuId, restaurantId);
        Menu menu = menuService.getById(menuId, restaurantId);
        Dish dish = getDish(dishId, menu.getId());
        repository.delete(dish.getId());
    }

    public List<Dish> getAll(int restaurantId, int menuId) {
        log.info("getAll: restaurantId =  {}, menuId = {}, ", restaurantId, menuId);
        Menu menu = menuService.getById(menuId, restaurantId);
        return repository.getAll(menu.getId());
    }

    public Dish getById(int dishId, int menuId, int restaurantId) {
        log.info("getById: dishTo = {}, menuId = {}, restaurantId =  {}", dishId, menuId, restaurantId);
        Menu menu = menuService.getById(menuId, restaurantId);
        return getDish(dishId, menu.getId());
    }

    private Dish getDish(int dishId, int menuId) {
        return repository.getByDishIdAndMenuId(dishId, menuId)
                .orElseThrow(() -> new NotFoundException("Dish with id=" + dishId +
                        " for menu with id = " + menuId + " not found"));
    }
}
