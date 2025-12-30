package ru.zhidev.lunch_voting_system.user.util;

import lombok.experimental.UtilityClass;
import ru.zhidev.lunch_voting_system.user.model.Role;
import ru.zhidev.lunch_voting_system.user.model.User;
import ru.zhidev.lunch_voting_system.user.to.UserTo;

@UtilityClass
public class UsersUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
