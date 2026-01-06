package ru.zhidev.lunch_voting_system.vote.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhidev.lunch_voting_system.app.AuthUser;
import ru.zhidev.lunch_voting_system.vote.model.Vote;
import ru.zhidev.lunch_voting_system.vote.service.VoteService;
import ru.zhidev.lunch_voting_system.vote.to.VoteReadTo;
import ru.zhidev.lunch_voting_system.vote.to.VoteWriteTo;

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

    @GetMapping("/winner")
    public List<VoteReadTo> getWinnerByDate(@RequestParam LocalDate date) {
        log.info("getWinner: date = {}", date);
        return service.calculateResult(date);
    }
}