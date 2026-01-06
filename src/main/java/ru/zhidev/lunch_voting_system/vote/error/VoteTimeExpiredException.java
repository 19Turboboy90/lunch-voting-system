package ru.zhidev.lunch_voting_system.vote.error;

public class VoteTimeExpiredException extends RuntimeException {
    public VoteTimeExpiredException(String message) {
        super(message);
    }
}