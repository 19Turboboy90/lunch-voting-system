package ru.zhidev.lunchvotingsystem.vote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.common.error.NotFoundException;
import ru.zhidev.lunchvotingsystem.restaurant.model.Restaurant;
import ru.zhidev.lunchvotingsystem.restaurant.repository.RestaurantRepository;
import ru.zhidev.lunchvotingsystem.user.model.User;
import ru.zhidev.lunchvotingsystem.user.repository.UserRepository;
import ru.zhidev.lunchvotingsystem.vote.error.VoteTimeExpiredException;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.repository.VoteRepository;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadTo;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo;
import ru.zhidev.lunchvotingsystem.vote.util.VoteUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {
    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final Clock clock;
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote saveOrUpdate(int restaurantId, int userId) {
        log.info("saveOrUpdate:  restaurantId =  {}, userId = {}", restaurantId, userId);

        final LocalDateTime localDateTime = LocalDateTime.now(clock);
        final LocalDate dateOfVote = localDateTime.toLocalDate();
        final LocalTime timeOfVote = localDateTime.toLocalTime();

        User user = userRepository.getReferenceById(userId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        return repository.getByUserIdAndDate(user.getId(), dateOfVote)
                .map(vote -> updateVote(timeOfVote, vote, restaurant))
                .orElseGet(() -> repository.save(new Vote(null, restaurant, user, dateOfVote)));
    }

    private Vote updateVote(LocalTime timeOfVote, Vote vote, Restaurant restaurant) {
        if (timeOfVote.isAfter(DEADLINE)) {
            throw new VoteTimeExpiredException("Voting time is over");
        }

        vote.setRestaurant(restaurant);
        return repository.save(vote);
    }

    public List<VoteReadWinnerTo> calculateResult(LocalDate date) {
        log.info("calculateResult:  date =  {}", date);
        List<VoteReadWinnerTo> listRatingByDate = repository.getListRatingByDate(date);
        if (listRatingByDate.isEmpty()) {
            return List.of();
        }

        long maxVotes = listRatingByDate.getFirst().numberOfVotes();

        return listRatingByDate.stream()
                .takeWhile(v -> v.numberOfVotes() == maxVotes)
                .toList();
    }

    public List<VoteReadWinnerTo> showRatingByDate(LocalDate date) {
        log.info("showRatingForDate:  date =  {}", date);
        return repository.getListRatingByDate(date);
    }

    public VoteReadTo getByDateAndUserId(LocalDate date, int userId) {
        User user = userRepository.getReferenceById(userId);
        Vote vote = repository.getByUserIdAndDate(user.getId(), date)
                .orElseThrow(() -> new NotFoundException("Vote is not found"));

        return VoteUtil.mapperTo(vote);
    }
}