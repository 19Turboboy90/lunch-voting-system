package ru.zhidev.lunchvotingsystem.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zhidev.lunchvotingsystem.AbstractControllerTest;
import ru.zhidev.lunchvotingsystem.restaurant.MenuTestData;
import ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zhidev.lunchvotingsystem.restaurant.DishTestData.*;
import static ru.zhidev.lunchvotingsystem.user.UserTestData.USER_MAIL;

class DishRestControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndMenuId() throws Exception {
        perform(MockMvcRequestBuilders.get(getUrl(RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesForFirstMenu));
        DISH_MATCHER.assertMatch(dishesForFirstMenu, dish1, dish2);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getById() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID) + '/' + DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID) + '/' + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByIdForNotExistRestaurant() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.NOT_FOUND, MenuTestData.MENU_ID) + '/' + DISH_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByIdForNotExistMenu() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.RESTAURANT_ID, MenuTestData.NOT_FOUND) + '/' + DISH_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private static String getUrl(int restaurantId, int menuId) {
        return String.format("/api/restaurants/%d/menus/%d/dishes", restaurantId, menuId);
    }
}
