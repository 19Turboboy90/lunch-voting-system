package ru.zhidev.lunchvotingsystem.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.BaseRepository;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAll(int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAllWithDishes(int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes")
    List<Menu> getAllWithDishes();

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.id = :menuId")
    Optional<Menu> getByMenuIdAndRestaurantId(int menuId, int restaurantId);
}