package ru.zhidev.lunchvotingsystem.vote.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.zhidev.lunchvotingsystem.common.to.BaseTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteWriteTo extends BaseTo {

    @NotNull
    int restaurantId;
}