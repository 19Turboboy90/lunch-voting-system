package ru.zhidev.lunch_voting_system.vote.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.zhidev.lunch_voting_system.common.model.BaseEntity;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "vote",
        schema = "public",
        uniqueConstraints = {@UniqueConstraint(name = "user_id_date_of_vote", columnNames = {"user_id", "date_of_vote"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date_of_vote", nullable = false)
    @NotNull
    private LocalDate dateOfVote;
}
