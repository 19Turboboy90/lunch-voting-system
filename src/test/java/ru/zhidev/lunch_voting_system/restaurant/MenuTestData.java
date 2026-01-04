package ru.zhidev.lunch_voting_system.restaurant;

import ru.zhidev.lunch_voting_system.MatcherFactory;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;

import java.util.List;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final int MENU_ID = 1;

    public static final int NOT_FOUND = 100;

    public static final Menu menu1 = new Menu(MENU_ID, "lunch");
    public static final Menu menu2 = new Menu(MENU_ID + 1, "lunch");

    public static final Menu menuWithRestaurant = new Menu(MENU_ID, "lunch", RestaurantTestData.restaurant1);


    public static final List<Menu> menus = List.of(menu1);

    public static Menu getNew() {
        return new Menu(null, "new menu");
    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID, "update menu");
    }
}