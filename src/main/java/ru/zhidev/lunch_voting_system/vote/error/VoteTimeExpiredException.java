package ru.zhidev.lunch_voting_system.vote.error;

import ru.zhidev.lunch_voting_system.common.error.AppException;
import ru.zhidev.lunch_voting_system.common.error.ErrorType;

public class VoteTimeExpiredException extends AppException {

    public VoteTimeExpiredException(String message) {
        super(message, ErrorType.VOTE_TIME_EXPIRED);
    }
}