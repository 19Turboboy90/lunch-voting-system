package ru.zhidev.lunchvotingsystem.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.BaseRepository;
import ru.zhidev.lunchvotingsystem.restaurant.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.menu.id = :menuId")
    List<Dish> getAll(int menuId);

    @Query("SELECT d FROM Dish d WHERE d.menu.id = :menuId AND d.id = :dishId")
    Optional<Dish> getByDishIdAndMenuId(int dishId, int menuId);
}