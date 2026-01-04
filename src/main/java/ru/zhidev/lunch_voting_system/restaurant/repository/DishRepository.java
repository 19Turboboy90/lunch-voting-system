package ru.zhidev.lunch_voting_system.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.common.BaseRepository;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d JOIN FETCH d.menu WHERE d.menu.id = :menuId")
    List<Dish> getAll(int menuId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.menu m WHERE m.id = :menuId AND d.id = :dishId")
    Optional<Dish> getByDishIdAndMenuId(int dishId, int menuId);
}