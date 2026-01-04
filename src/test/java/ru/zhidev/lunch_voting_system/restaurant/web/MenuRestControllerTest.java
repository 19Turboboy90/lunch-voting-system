package ru.zhidev.lunch_voting_system.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zhidev.lunch_voting_system.AbstractControllerTest;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zhidev.lunch_voting_system.restaurant.MenuTestData.*;
import static ru.zhidev.lunch_voting_system.user.UserTestData.USER_MAIL;

class MenuRestControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(getUrl(RestaurantTestData.RESTAURANT_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menus));
        MENU_MATCHER.assertMatch(menus, menu1);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getById() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.RESTAURANT_ID) + '/' + MENU_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.RESTAURANT_ID) + '/' + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByIdForNotExistRestaurant() throws Exception {
        perform(MockMvcRequestBuilders
                .get(getUrl(RestaurantTestData.NOT_FOUND) + '/' + MENU_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private static String getUrl(int restaurantId) {
        return String.format("/api/restaurants/%d/menus", restaurantId);
    }
}