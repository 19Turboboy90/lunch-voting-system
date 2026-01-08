package ru.zhidev.lunch_voting_system.vote.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.zhidev.lunch_voting_system.AbstractServiceTest;
import ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData;
import ru.zhidev.lunch_voting_system.vote.error.VoteTimeExpiredException;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadWinnerTo;
import ru.zhidev.lunch_voting_system.vote.util.VoteUtil;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static ru.zhidev.lunch_voting_system.user.UserTestData.GUEST_ID;
import static ru.zhidev.lunch_voting_system.user.UserTestData.USER_ID;
import static ru.zhidev.lunch_voting_system.vote.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @MockitoBean
    private Clock clock;

    private final ZoneId zone = ZoneId.systemDefault();

    @BeforeEach
    void setup() {
        when(clock.getZone()).thenReturn(zone);
    }

    @Test
    void voteFirstTime() {
        LocalDateTime fixedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 30));
        when(clock.instant()).thenReturn(fixedTime.atZone(zone).toInstant());

        Vote vote = service.saveOrUpdate(RestaurantTestData.RESTAURANT_ID, GUEST_ID);

        VOTE_READ_TO_MATCHER.assertMatch(VoteUtil.mapperTo(vote), service.getByDateAndUserId(LocalDate.now(), GUEST_ID));
    }

    @Test
    void voteBeforeDeadline() {
        LocalDateTime fixedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30));
        when(clock.instant()).thenReturn(fixedTime.atZone(zone).toInstant());

        Vote firstVote = service.saveOrUpdate(RestaurantTestData.restaurant1.getId(), GUEST_ID);
        Vote secondVote = service.saveOrUpdate(RestaurantTestData.restaurant2.getId(), GUEST_ID);
        assertEquals(firstVote.getId(), secondVote.getId());
        assertEquals(RestaurantTestData.restaurant2, secondVote.getRestaurant());
    }

    @Test
    void voteAfterDeadline() {
        LocalDateTime fixedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));
        when(clock.instant()).thenReturn(fixedTime.atZone(zone).toInstant());

        assertThrows(VoteTimeExpiredException.class,
                () -> service.saveOrUpdate(RestaurantTestData.restaurant2.getId(), USER_ID));
    }

    @Test
    void calculateResult() {
        VoteReadWinnerTo winner1 = new VoteReadWinnerTo(LocalDate.now(), RestaurantTestData.restaurant1.getName(), 1);
        VoteReadWinnerTo winner2 = new VoteReadWinnerTo(LocalDate.now(), RestaurantTestData.restaurant2.getName(), 1);
        List<VoteReadWinnerTo> voteReadWinnerTos = service.calculateResult(LocalDate.now());
        VOTE_READ_TO_WINNER_MATCHER.assertMatch(voteReadWinnerTos, List.of(winner2, winner1));
    }

    @Test
    void calculateResultNoVotes() {
        List<VoteReadWinnerTo> voteReadWinnerTos = service.calculateResult(LocalDate.now().minusDays(1));
        VOTE_READ_TO_WINNER_MATCHER.assertMatch(voteReadWinnerTos, List.of());
    }

    @Test
    void getByDateAndUserId() {
        VoteReadTo byDateAndUserId = service.getByDateAndUserId(LocalDate.now(), USER_ID);
        VOTE_READ_TO_MATCHER.assertMatch(byDateAndUserId, VoteUtil.mapperTo(vote1));
    }
}