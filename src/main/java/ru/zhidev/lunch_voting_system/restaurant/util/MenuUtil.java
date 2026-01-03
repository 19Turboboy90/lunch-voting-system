package ru.zhidev.lunch_voting_system.restaurant.util;

import lombok.experimental.UtilityClass;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.to.MenuTo;

@UtilityClass
public class MenuUtil {
    public static Menu updateFromTo(Menu menu, MenuTo menuTo) {
        menu.setName(menuTo.getName());
        return menu;
    }

    public static Menu createNewFromTo(MenuTo menuTo, Restaurant restaurant) {
        return new Menu(null, menuTo.getName(), restaurant);
    }
}