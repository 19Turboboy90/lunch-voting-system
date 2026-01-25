package ru.zhidev.lunchvotingsystem.common.error;

import static ru.zhidev.lunchvotingsystem.common.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}