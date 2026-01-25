package ru.zhidev.lunchvotingsystem.vote.to;

import java.time.LocalDate;

public record VoteReadWinnerTo(
        LocalDate date,
        Integer restaurantId,
        String restaurantName,
        Integer userId,
        Long numberOfVotes) {
}