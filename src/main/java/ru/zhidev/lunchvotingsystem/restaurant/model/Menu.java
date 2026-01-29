package ru.zhidev.lunchvotingsystem.restaurant.model;

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

@Entity
@Table(name = "menu",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Menu extends NamedEntity {

    @Column(name = "date_of_added", nullable = false)
    @NotNull
    private LocalDate added;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
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
}