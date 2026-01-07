package ru.zhidev.lunch_voting_system.vote.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zhidev.lunch_voting_system.AbstractControllerTest;
import ru.zhidev.lunch_voting_system.MatcherFactory;
import ru.zhidev.lunch_voting_system.common.util.JsonUtil;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;
import ru.zhidev.lunch_voting_system.user.UserTestData;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;
import ru.zhidev.lunch_voting_system.vote.to.VoteWriteTo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zhidev.lunch_voting_system.user.UserTestData.GUEST_MAIL;
import static ru.zhidev.lunch_voting_system.user.UserTestData.USER_MAIL;
import static ru.zhidev.lunch_voting_system.vote.web.VoteRestController.REST_URL;

class VoteRestControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void saveFirstVoteForUser() throws Exception {
        VoteWriteTo newVote = new VoteWriteTo(RestaurantTestData.RESTAURANT_ID);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = MatcherFactory.usingEqualsComparator(Vote.class).readFromJson(action);

        assertEquals(RestaurantTestData.RESTAURANT_ID, created.getRestaurant().getId());

        assertEquals(UserTestData.GUEST_ID, created.getUser().getId());

        assertEquals(LocalDate.now(), created.getDateOfVote());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void votingAfterElevenClockProhibited() throws Exception {
        VoteWriteTo newVote = new VoteWriteTo(RestaurantTestData.RESTAURANT_ID);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void calculateResult() throws Exception {
        VoteReadTo winner1 = new VoteReadTo(LocalDate.now(), RestaurantTestData.restaurant1.getName(), 1);
        VoteReadTo winner2 = new VoteReadTo(LocalDate.now(), RestaurantTestData.restaurant2.getName(), 1);
        perform(MockMvcRequestBuilders.get(REST_URL + "/winners")
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MatcherFactory.usingEqualsComparator(VoteReadTo.class).contentJson(winner2, winner1));
    }
}