package ru.zhidev.lunch_voting_system.common.error;

import static ru.zhidev.lunch_voting_system.common.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}