package ru.zhidev.lunchvotingsystem.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunchvotingsystem.common.model.NamedEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "dish", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"id", "name", "price", "description", "menu"})
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Positive
    private BigDecimal price;

    @Column(name = "description")
    @Size(max = 240)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private Menu menu;

    public Dish(Integer id, String name, BigDecimal price, String description) {
        super(id, name);
        this.price = price;
        this.description = description;
    }

    public Dish(Integer id, String name, BigDecimal price, String description, Menu menu) {
        super(id, name);
        this.price = price;
        this.description = description;
        this.menu = menu;
    }
}