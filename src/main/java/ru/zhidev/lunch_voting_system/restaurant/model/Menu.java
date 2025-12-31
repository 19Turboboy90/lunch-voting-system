package ru.zhidev.lunch_voting_system.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunch_voting_system.common.model.NamedEntity;

import java.util.List;

@Entity
@Table(name = "menu", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "restaurant")
public class Menu extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Dish> dishList;

    public Menu(Restaurant restaurant, List<Dish> dishList) {
        this.restaurant = restaurant;
        this.dishList = dishList;
    }

    public Menu(Integer id, String name, Restaurant restaurant, List<Dish> dishList) {
        super(id, name);
        this.restaurant = restaurant;
        this.dishList = dishList;
    }
}