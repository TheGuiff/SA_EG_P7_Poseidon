package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser (final Integer id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public  void deleteUser(final Integer id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        return userRepository.hashPasswordAndSave(user);
    }

}
