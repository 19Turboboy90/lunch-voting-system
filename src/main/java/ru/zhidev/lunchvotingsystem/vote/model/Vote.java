package ru.zhidev.lunchvotingsystem.vote.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunchvotingsystem.common.model.BaseEntity;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;
import ru.zhidev.lunchvotingsystem.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "vote",
        schema = "public",
        uniqueConstraints = {@UniqueConstraint(name = "date_of_vote_user_id", columnNames = {"date_of_vote", "user_id"})})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private User user;

    @Column(name = "date_of_vote", nullable = false)
    @NotNull
    private LocalDate dateOfVote;

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate dateOfVote) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.dateOfVote = dateOfVote;
    }
}
