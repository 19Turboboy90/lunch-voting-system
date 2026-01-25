package ru.zhidev.lunchvotingsystem.common.error;

import static ru.zhidev.lunchvotingsystem.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}