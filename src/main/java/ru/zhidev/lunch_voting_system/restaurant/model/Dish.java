package ru.zhidev.lunch_voting_system.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunch_voting_system.common.model.NamedEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "dish", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Min(value = 1)
    private BigDecimal price;

    @Column(name = "description")
    @Size(min = 0, max = 240)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Dish(BigDecimal price, String description, Menu menu) {
        this.price = price;
        this.description = description;
        this.menu = menu;
    }

    public Dish(Integer id, String name, BigDecimal price, String description, Menu menu) {
        super(id, name);
        this.price = price;
        this.description = description;
        this.menu = menu;
    }
}