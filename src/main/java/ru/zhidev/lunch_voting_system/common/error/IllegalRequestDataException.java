package ru.zhidev.lunch_voting_system.common.error;

import static ru.zhidev.lunch_voting_system.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}