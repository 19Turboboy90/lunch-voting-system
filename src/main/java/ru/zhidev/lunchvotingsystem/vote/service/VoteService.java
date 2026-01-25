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
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        final LocalDate dateOfVote = LocalDate.now(clock);
        final LocalTime timeOfVote = LocalTime.now(clock);

        User user = userRepository.getExisted(userId);
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
        Map<Restaurant, Integer> restaurantsWithPoints = getRestaurantsWithPoints(date);

        int maxPoint = getMaxPoint(restaurantsWithPoints);

        return restaurantsWithPoints.entrySet().stream()
                .filter(v -> v.getValue() == maxPoint)
                .map(ob -> new VoteReadWinnerTo(date, ob.getKey().getName(), maxPoint))
                .toList();
    }

    private Map<Restaurant, Integer> getRestaurantsWithPoints(LocalDate date) {
        return repository.getByDate(date)
                .stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.summingInt(v -> 1)));
    }

    private int getMaxPoint(Map<Restaurant, Integer> restaurantsWithPoints) {
        return restaurantsWithPoints.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }


    public VoteReadTo getByDateAndUserId(LocalDate date, int userId) {
        User user = userRepository.getExisted(userId);
        Vote vote = repository.getByDateAndUserId(date, user.getId())
                .orElseThrow(() -> new NotFoundException("Vote is not found"));

        return VoteUtil.mapperTo(vote);
    }
}