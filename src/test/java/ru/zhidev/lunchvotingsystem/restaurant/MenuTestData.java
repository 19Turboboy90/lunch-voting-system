package ru.zhidev.lunchvotingsystem.restaurant;

import ru.zhidev.lunchvotingsystem.MatcherFactory;
import ru.zhidev.lunchvotingsystem.restaurant.model.Menu;
import ru.zhidev.lunchvotingsystem.restaurant.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static ru.zhidev.lunchvotingsystem.restaurant.DishTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");

    public static final int MENU_ID = 1;

    public static final int NOT_FOUND = 100;

    public static final Menu menu1 = new Menu(MENU_ID, "lunch", LocalDate.now());

    public static final Menu menuWithDishes1 = new Menu(MENU_ID, "lunch", LocalDate.now(), List.of(dish1, dish2));
    public static final Menu menuWithDishes2 = new Menu(MENU_ID + 1, "lunch", LocalDate.now(), List.of(dish3, dish4));

    public static final List<Menu> menus = List.of(menu1);

    public static Menu getNew() {
        return new Menu(null, "new menu", LocalDate.now());
    }

    public static MenuTo getNewTo() {
        return new MenuTo(null, "new menuTo");
    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID, "update menu", LocalDate.now());
    }

    public static MenuTo getUpdatedTo() {
        return new MenuTo(MENU_ID, "update menuTo");
    }
}