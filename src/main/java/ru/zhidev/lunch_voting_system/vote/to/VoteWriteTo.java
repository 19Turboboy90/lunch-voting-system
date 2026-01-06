package ru.zhidev.lunch_voting_system.vote.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.zhidev.lunch_voting_system.common.to.BaseTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteWriteTo extends BaseTo {

    @NotNull
    int restaurantId;
}