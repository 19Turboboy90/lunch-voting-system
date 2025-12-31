package ru.zhidev.lunch_voting_system.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.common.BaseRepository;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT DISTINCT r FROM Restaurant r WHERE r.name LIKE concat('%', :name, '%')")
    List<Restaurant> getByName(@Param("name") String name);
}