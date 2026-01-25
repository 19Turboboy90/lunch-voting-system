package ru.zhidev.lunchvotingsystem.vote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhidev.lunchvotingsystem.AbstractServiceTest;
import ru.zhidev.lunchvotingsystem.vote.error.VoteTimeExpiredException;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadTo;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo;
import ru.zhidev.lunchvotingsystem.vote.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData.*;
import static ru.zhidev.lunchvotingsystem.user.UserTestData.*;
import static ru.zhidev.lunchvotingsystem.vote.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void voteFirstTime() {
        mockTime(LocalDate.now(), LocalTime.of(19, 30));

        service.saveOrUpdate(RESTAURANT_ID, GUEST_ID);

        VOTE_READ_TO_MATCHER.assertMatch(service.getByDateAndUserId(LocalDate.now(), GUEST_ID), getVoteReadTo());
    }

    @Test
    void voteBeforeDeadline() {
        mockTime(LocalDate.now(), LocalTime.of(10, 30));

        Vote firstVote = service.saveOrUpdate(restaurant1.getId(), GUEST_ID);
        Vote secondVote = service.saveOrUpdate(restaurant2.getId(), GUEST_ID);
        assertEquals(firstVote.getId(), secondVote.getId());
        assertEquals(restaurant2, secondVote.getRestaurant());
    }

    @Test
    void voteAfterDeadline() {
        LocalDateTime fixedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));
        when(clock.instant()).thenReturn(fixedTime.atZone(zone).toInstant());

        assertThrows(VoteTimeExpiredException.class,
                () -> service.saveOrUpdate(restaurant2.getId(), USER_ID));
    }

    @Test
    void calculateResult() {
        VoteReadWinnerTo winner1 =
                new VoteReadWinnerTo(LocalDate.now(), restaurant1.getId(), restaurant1.getName(), user.getId(), 1L);
        VoteReadWinnerTo winner2 =
                new VoteReadWinnerTo(LocalDate.now(), restaurant2.getId(), restaurant2.getName(), admin.getId(), 1L);
        List<VoteReadWinnerTo> voteReadWinnerTos = service.calculateResult(LocalDate.now());
        VOTE_READ_TO_WINNER_MATCHER.assertMatch(voteReadWinnerTos, List.of(winner1, winner2));
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