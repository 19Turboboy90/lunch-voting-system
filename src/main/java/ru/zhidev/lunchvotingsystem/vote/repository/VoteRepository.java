package ru.zhidev.lunchvotingsystem.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.BaseRepository;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.dateOfVote = :dateOfVote")
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate dateOfVote);

    @Query("SELECT v FROM Vote v WHERE v.dateOfVote =:date")
    List<Vote> getByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.dateOfVote =:date AND v.user.id = :userId")
    Optional<Vote> getByDateAndUserId(LocalDate date, int userId);
}