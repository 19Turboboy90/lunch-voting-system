package ru.zhidev.lunchvotingsystem.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhidev.lunchvotingsystem.user.model.User;
import ru.zhidev.lunchvotingsystem.user.repository.UserRepository;

import java.util.List;

import static ru.zhidev.lunchvotingsystem.app.config.CacheConfig.USERS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @CacheEvict(value = USERS, allEntries = true)
    @Transactional
    public User prepareAndSave(User user) {
        return repository.prepareAndSave(user);
    }

    public User getExistedByEmail(String email) {
        return repository.getExistedByEmail(email);
    }
}
