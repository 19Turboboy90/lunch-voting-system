package ru.zhidev.lunchvotingsystem.restaurant;

import ru.zhidev.lunchvotingsystem.MatcherFactory;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;

import java.util.List;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");

    public static final int RESTAURANT_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "restaurant_1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "restaurant_2");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }
}