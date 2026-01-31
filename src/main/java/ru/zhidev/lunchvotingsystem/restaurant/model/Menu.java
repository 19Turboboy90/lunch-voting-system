package ru.zhidev.lunchvotingsystem.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunchvotingsystem.common.model.NamedEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"id", "name", "added", "dishes", "restaurant"})
public class Menu extends NamedEntity {

    @Column(name = "date_of_added", nullable = false)
    @NotNull
    private LocalDate added;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Dish> dishes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private Restaurant restaurant;

    @PrePersist
    void onCreate() {
        this.added = LocalDate.now();
    }

    public Menu(Integer id, String name, LocalDate added) {
        super(id, name);
        this.added = added;
    }

    public Menu(Integer id, String name, Restaurant restaurant) {
        super(id, name);
        this.restaurant = restaurant;
    }

    public Menu(Integer id, String name, LocalDate added, List<Dish> dishes) {
        super(id, name);
        this.added = added;
        this.dishes = dishes;
    }

    public Menu(LocalDate added, List<Dish> dishes) {
        this.added = added;
        this.dishes = dishes;
    }
}