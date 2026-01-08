package ru.zhidev.lunch_voting_system.vote.to;

import java.time.LocalDate;

public record VoteReadWinnerTo(
        LocalDate date,
        String restaurantName,
        int numberOfVotes) {
}