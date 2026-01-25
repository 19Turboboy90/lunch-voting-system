package ru.zhidev.lunchvotingsystem.vote.to;

import java.time.LocalDate;

public record VoteReadWinnerTo(
        LocalDate date,
        String restaurantName,
        int numberOfVotes) {
}