package ru.zhidev.lunch_voting_system.restaurant.util;

import lombok.experimental.UtilityClass;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;
import ru.zhidev.lunch_voting_system.restaurant.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setDescription(dishTo.getDescription());
        return dish;
    }

    public static Dish createNewFromTo(DishTo dishTo, Menu menu) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice(), dishTo.getDescription(), menu);
    }
}