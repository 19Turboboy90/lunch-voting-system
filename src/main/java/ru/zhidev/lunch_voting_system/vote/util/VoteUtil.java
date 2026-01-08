package ru.zhidev.lunch_voting_system.vote.util;

import lombok.experimental.UtilityClass;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;

@UtilityClass
public class VoteUtil {
    public static VoteReadTo mapperTo(Vote vote) {
        return VoteReadTo.builder()
                .date(vote.getDateOfVote())
                .restaurantName(vote.getRestaurant().getName())
                .userName(vote.getUser().getName())
                .email(vote.getUser().getEmail())
                .build();
    }

}