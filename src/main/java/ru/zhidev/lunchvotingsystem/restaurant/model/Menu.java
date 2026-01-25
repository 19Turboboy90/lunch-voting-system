package ru.zhidev.lunchvotingsystem.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunchvotingsystem.common.model.NamedEntity;

@Entity
@Table(name = "menu",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Menu extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    public Menu(Integer id, String name) {
        super(id, name);
    }

    public Menu(Integer id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }
}