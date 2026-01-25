package ru.zhidev.lunchvotingsystem.common.error;

import static ru.zhidev.lunchvotingsystem.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}