package ru.zhidev.lunchvotingsystem.vote.error;

import ru.zhidev.lunchvotingsystem.common.error.AppException;
import ru.zhidev.lunchvotingsystem.common.error.ErrorType;

public class VoteTimeExpiredException extends AppException {

    public VoteTimeExpiredException(String message) {
        super(message, ErrorType.VOTE_TIME_EXPIRED);
    }
}