package ru.zhidev.lunchvotingsystem.vote.to;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record VoteReadTo(
        LocalDate date,
        String restaurantName,

        String userName,

        String email
) {
}
