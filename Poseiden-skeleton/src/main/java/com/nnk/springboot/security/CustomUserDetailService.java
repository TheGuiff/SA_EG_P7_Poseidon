package com.nnk.springboot.security;

import com.nnk.springboot.dal.entity.User;
import com.nnk.springboot.dal.repositories.UserRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final List<User> userList = userRepository.findByUsername(username);
        if (userList.size() == 0) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(userList.get(0));
    }
}
