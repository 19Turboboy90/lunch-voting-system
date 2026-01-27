package ru.zhidev.lunchvotingsystem.vote;

import ru.zhidev.lunchvotingsystem.MatcherFactory;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadTo;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo;

import java.time.LocalDate;
import java.util.List;

import static ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData.restaurant1;
import static ru.zhidev.lunchvotingsystem.restaurant.RestaurantTestData.restaurant2;
import static ru.zhidev.lunchvotingsystem.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteReadTo> VOTE_READ_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteReadTo.class);
    public static final MatcherFactory.Matcher<VoteReadWinnerTo> VOTE_READ_TO_WINNER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteReadWinnerTo.class);

    public static final int VOTE_ID = 1;

    public static final int NOT_FOUND = 100;

    public static final Vote vote1 = new Vote(VOTE_ID, restaurant1, user, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID + 1, restaurant2, admin, LocalDate.now());

    public static final VoteReadWinnerTo winner1 =
            new VoteReadWinnerTo(LocalDate.now(), restaurant1.getId(), restaurant1.getName(), 1L);
    public static final VoteReadWinnerTo winner2 =
            new VoteReadWinnerTo(LocalDate.now(), restaurant2.getId(), restaurant2.getName(), 1L);

    public static final List<VoteReadWinnerTo> winners = List.of(winner1, winner2);

    public static VoteReadTo getVoteReadTo() {
        return VoteReadTo.builder()
                .date(LocalDate.now())
                .restaurantId(restaurant1.getId())
                .restaurantName(restaurant1.getName())
                .userId(guest.getId())
                .userName(guest.getName())
                .email(guest.getEmail())
                .build();
    }
}