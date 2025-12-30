package ru.zhidev.lunch_voting_system.common.error;

import static ru.zhidev.lunch_voting_system.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}