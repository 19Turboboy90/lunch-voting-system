package ru.zhidev.lunchvotingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.BaseRepository;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.dateOfVote = :dateOfVote")
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate dateOfVote);

    @Query("""
             SELECT new ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo(
             v.dateOfVote,
             v.restaurant.id,
             v.restaurant.name,
             COUNT (v.restaurant.id))
             FROM Vote v
             WHERE v.dateOfVote =:date
             GROUP BY v.dateOfVote, v.restaurant.name
             ORDER BY COUNT (v.restaurant.id) DESC
            """)
    List<VoteReadWinnerTo> getListRatingByDate(LocalDate date);
}