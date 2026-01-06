package ru.zhidev.lunch_voting_system.vote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunch_voting_system.restaurant.model.Restaurant;
import ru.zhidev.lunch_voting_system.restaurant.repository.RestaurantRepository;
import ru.zhidev.lunch_voting_system.user.model.User;
import ru.zhidev.lunch_voting_system.user.repository.UserRepository;
import ru.zhidev.lunch_voting_system.vote.error.VoteTimeExpiredException;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.repository.VoteRepository;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {
    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote saveOrUpdate(int restaurantId, int userId) {
        log.info("saveOrUpdate:  restaurantId =  {}, userId = {}", restaurantId, userId);

        final LocalDateTime localDateTime = LocalDateTime.now();
        final LocalDate dateOfVote = localDateTime.toLocalDate();
        final LocalTime timeOfVote = localDateTime.toLocalTime();

        User user = userRepository.getExisted(userId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Optional<Vote> getExistVote = repository.getByUserIdAndDate(user.getId(), dateOfVote);

        return getExistVote
                .map(vote -> updateVote(timeOfVote, vote, restaurant))
                .orElseGet(() -> repository.save(new Vote(null, restaurant, user, dateOfVote)));
    }

    public List<VoteReadTo> calculateResult(LocalDate date) {
        Map<Restaurant, Integer> restaurantsWithPoints = repository.getByDate(date)
                .stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.summingInt(v -> 1)));

        int maxPoint = restaurantsWithPoints.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        return restaurantsWithPoints.entrySet().stream()
                .filter(v -> v.getValue() == maxPoint)
                .map(ob -> new VoteReadTo(date, ob.getKey().getName(), maxPoint))
                .toList();
    }

    private Vote updateVote(LocalTime timeOfVote, Vote vote, Restaurant restaurant) {
        if (timeOfVote.isAfter(DEADLINE)) {
            throw new VoteTimeExpiredException("Voting time is over");
        }

        vote.setRestaurant(restaurant);
        return repository.save(vote);
    }
}