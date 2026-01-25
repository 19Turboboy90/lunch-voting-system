package ru.zhidev.lunchvotingsystem.vote.util;

import lombok.experimental.UtilityClass;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadTo;

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