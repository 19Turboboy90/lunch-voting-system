package ru.zhidev.lunch_voting_system.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zhidev.lunch_voting_system.AbstractControllerTest;
import ru.zhidev.lunch_voting_system.common.error.NotFoundException;
import ru.zhidev.lunch_voting_system.common.util.JsonUtil;
import ru.zhidev.lunch_voting_system.restaurant.DishTestData;
import ru.zhidev.lunch_voting_system.restaurant.MenuTestData;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;
import ru.zhidev.lunch_voting_system.restaurant.model.Dish;
import ru.zhidev.lunch_voting_system.restaurant.service.DishService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zhidev.lunch_voting_system.restaurant.DishTestData.DISH_ID;
import static ru.zhidev.lunch_voting_system.restaurant.DishTestData.DISH_MATCHER;
import static ru.zhidev.lunch_voting_system.restaurant.MenuTestData.MENU_ID;
import static ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.zhidev.lunch_voting_system.user.UserTestData.ADMIN_MAIL;
import static ru.zhidev.lunch_voting_system.user.UserTestData.USER_MAIL;

class AdminDishRestControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void save() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(getUrl(RESTAURANT_ID, MENU_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.getById(newId, MENU_ID, RESTAURANT_ID), newDish);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void saveWithoutAccessRights() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(getUrl(RESTAURANT_ID, MENU_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RESTAURANT_ID, MENU_ID) + '/' + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(service.getById(DISH_ID, MENU_ID, RESTAURANT_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithoutAccessRights() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RESTAURANT_ID, MENU_ID) + '/' + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateForNotExistRestaurant() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RestaurantTestData.NOT_FOUND, MENU_ID) + '/' + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateForNotExistMenu() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RESTAURANT_ID, MenuTestData.NOT_FOUND) + '/' + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RESTAURANT_ID, MENU_ID) + '/' + DISH_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(DISH_ID, MENU_ID, RESTAURANT_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteWithoutAccessRights() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RESTAURANT_ID, MENU_ID) + '/' + DISH_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteForNotExistRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RestaurantTestData.NOT_FOUND, MENU_ID) + '/' + DISH_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteForNotExistMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RESTAURANT_ID, MenuTestData.NOT_FOUND) + '/' + DISH_ID))
                .andExpect(status().isNotFound());
    }

    private static String getUrl(int restaurantId, int menuId) {
        return String.format("/api/admin/restaurants/%d/menus/%d/dishes", restaurantId, menuId);
    }
}