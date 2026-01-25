package ru.zhidev.lunchvotingsystem.restaurant.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.zhidev.lunchvotingsystem.common.to.NamedTo;

import java.math.BigDecimal;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    @NotNull
    @Positive
    BigDecimal price;

    @Size(max = 240)
    String description;

    public DishTo(Integer id, String name, BigDecimal price, String description) {
        super(id, name);
        this.price = price;
        this.description = description;
    }
}