package ru.zhidev.lunchvotingsystem.vote.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhidev.lunchvotingsystem.app.AuthUser;
import ru.zhidev.lunchvotingsystem.vote.model.Vote;
import ru.zhidev.lunchvotingsystem.vote.service.VoteService;
import ru.zhidev.lunchvotingsystem.vote.to.VoteReadWinnerTo;
import ru.zhidev.lunchvotingsystem.vote.to.VoteWriteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class VoteRestController {

    static final String REST_URL = "/api/votes";

    private final VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> save(@Valid @RequestBody VoteWriteTo vote, @AuthenticationPrincipal AuthUser authUser) {
        log.info("save: vote.getRestaurantId() = {}, authUser.id() = {}", vote.getRestaurantId(), authUser.id());
        Vote created = service.saveOrUpdate(vote.getRestaurantId(), authUser.getUser().id());

        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{voteId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteWriteTo vote, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update: vote.getRestaurantId() = {}, authUser.id() = {}", vote.getRestaurantId(), authUser.id());

        service.saveOrUpdate(vote.getRestaurantId(), authUser.getUser().id());
    }

    @GetMapping("/winners")
    public List<VoteReadWinnerTo> showWinnerByDate(@RequestParam LocalDate date) {
        log.info("getWinnerByDate: date = {}", date);
        return service.calculateResult(date);
    }

    @GetMapping("/rating")
    public List<VoteReadWinnerTo> showRatingByDate(@RequestParam LocalDate date) {
        log.info("showRatingByDate: date = {}", date);
        return service.showRatingByDate(date);
    }
}