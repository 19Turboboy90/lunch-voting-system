package ru.zhidev.lunchvotingsystem.restaurant.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.zhidev.lunchvotingsystem.common.model.NamedEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "menus"})
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Menu> menus = new ArrayList<>();

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Menu> menus) {
        super(id, name);
        this.menus = menus;
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
        menu.setRestaurant(this);
    }
}