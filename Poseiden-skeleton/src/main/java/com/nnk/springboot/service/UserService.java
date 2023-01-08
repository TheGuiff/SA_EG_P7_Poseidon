package com.nnk.springboot.service;

import com.nnk.springboot.dal.entity.Rating;
import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.RatingRepository;
import com.nnk.springboot.dal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
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
