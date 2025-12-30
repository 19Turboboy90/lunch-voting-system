package ru.zhidev.lunch_voting_system.restaurant.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.common.BaseRepository;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
}