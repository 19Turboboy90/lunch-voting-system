package ru.zhidev.lunchvotingsystem.vote.to;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record VoteReadTo(
        LocalDate date,
        Integer restaurantId,
        String restaurantName,
        Integer userId,
        String userName,
        String email
) {
}
