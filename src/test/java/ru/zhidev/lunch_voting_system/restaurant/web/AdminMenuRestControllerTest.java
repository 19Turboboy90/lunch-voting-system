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
import ru.zhidev.lunch_voting_system.restaurant.MenuTestData;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;
import ru.zhidev.lunch_voting_system.restaurant.model.Menu;
import ru.zhidev.lunch_voting_system.restaurant.service.MenuService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zhidev.lunch_voting_system.restaurant.MenuTestData.MENU_ID;
import static ru.zhidev.lunch_voting_system.restaurant.MenuTestData.MENU_MATCHER;
import static ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.zhidev.lunch_voting_system.user.UserTestData.ADMIN_MAIL;
import static ru.zhidev.lunch_voting_system.user.UserTestData.USER_MAIL;

class AdminMenuRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void save() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(getUrl(RESTAURANT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.getById(newId, RESTAURANT_ID), newMenu);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void saveWithoutAccessRights() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        perform(MockMvcRequestBuilders.post(getUrl(RESTAURANT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RESTAURANT_ID) + '/' + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(service.getById(MENU_ID, RESTAURANT_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithoutAccessRights() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RESTAURANT_ID) + '/' + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateForNotExistRestaurant() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(getUrl(RestaurantTestData.NOT_FOUND) + '/' + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RESTAURANT_ID) + '/' + MENU_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(MENU_ID, RESTAURANT_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteWithoutAccessRights() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RESTAURANT_ID) + '/' + MENU_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteForNotExistRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(getUrl(RestaurantTestData.NOT_FOUND) + '/' + MENU_ID))
                .andExpect(status().isNotFound());
    }

    private static String getUrl(int restaurantId) {
        return String.format("/api/admin/restaurants/%d/menus", restaurantId);
    }
}