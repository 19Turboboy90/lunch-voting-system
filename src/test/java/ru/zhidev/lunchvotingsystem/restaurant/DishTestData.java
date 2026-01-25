package ru.zhidev.lunchvotingsystem.restaurant;

import ru.zhidev.lunchvotingsystem.MatcherFactory;
import ru.zhidev.lunchvotingsystem.restaurant.model.Dish;
import ru.zhidev.lunchvotingsystem.restaurant.to.DishTo;

import java.math.BigDecimal;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final int DISH_ID = 1;

    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish(DISH_ID, "egg", new BigDecimal("12.12"), "egg");
    public static final Dish dish2 = new Dish(DISH_ID + 1, "milk", new BigDecimal("4.50"), "milk");
    public static final Dish dish3 = new Dish(DISH_ID + 2, "cookie", new BigDecimal("15.20"), "cookie");
    public static final Dish dish4 = new Dish(DISH_ID + 3, "soda", new BigDecimal("10.00"), "soda");

    public static final DishTo dishTo = new DishTo(null, "test", new BigDecimal("12.00"), "test description");
    public static final List<Dish> dishesForFirstMenu = List.of(dish1, dish2);

    public static Dish getNew() {
        return new Dish(null, "newName", new BigDecimal("12.12"), "new description");
    }

    public static DishTo getNewTo() {
        return new DishTo(null, "newNameTo", new BigDecimal("12.00"), "test descriptionTo");
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "updateName", new BigDecimal("10.00"), "update description");
    }

    public static DishTo getUpdatedTo() {
        return new DishTo(DISH_ID, "updateNameTo", new BigDecimal("10.11"), "update descriptionTo");
    }
}