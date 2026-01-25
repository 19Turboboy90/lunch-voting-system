package ru.zhidev.lunchvotingsystem.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.zhidev.lunchvotingsystem.common.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends NamedTo {
    public MenuTo(Integer id, String name) {
        super(id, name);
    }
}