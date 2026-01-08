package ru.zhidev.lunch_voting_system.vote;

import ru.zhidev.lunch_voting_system.MatcherFactory;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadWinnerTo;

import java.time.LocalDate;

import static ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData.restaurant1;
import static ru.zhidev.lunch_voting_system.restaurant.RestaurantTestData.restaurant2;
import static ru.zhidev.lunch_voting_system.user.UserTestData.admin;
import static ru.zhidev.lunch_voting_system.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteReadTo> VOTE_READ_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteReadTo.class);
    public static final MatcherFactory.Matcher<VoteReadWinnerTo> VOTE_READ_TO_WINNER_MATCHER = MatcherFactory.usingEqualsComparator(VoteReadWinnerTo.class);

    public static final int VOTE_ID = 1;

    public static final int NOT_FOUND = 100;

    public static final Vote vote1 = new Vote(VOTE_ID, restaurant1, user, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE_ID + 1, restaurant2, admin, LocalDate.now());

    public static final VoteReadWinnerTo winner1 = new VoteReadWinnerTo(LocalDate.now(), restaurant1.getName(), 1);
    public static final VoteReadWinnerTo winner2 = new VoteReadWinnerTo(LocalDate.now(), restaurant2.getName(), 1);
}