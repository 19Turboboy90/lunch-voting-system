package ru.zhidev.lunch_voting_system.vote.to;

import java.time.LocalDate;

public record VoteReadTo(
        LocalDate date,
        String restaurantName,
        int numberOfVotes) {
}