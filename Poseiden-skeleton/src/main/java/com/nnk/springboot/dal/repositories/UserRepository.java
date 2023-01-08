package com.nnk.springboot.dal.repositories;

import com.nnk.springboot.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.InputMismatchException;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    List<User> findByUsername (String username);

    default String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    default User hashPasswordAndSave(User user) {
        Boolean hasDigit = false;
        Boolean hasUpper = false;
        Boolean hasSpecial = false;
        String passwordToTest = user.getPassword();
        String specialsChar = ",?;.:/!§-_&";
        for (char c:passwordToTest.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            if (specialsChar.contains(String.valueOf(c))) {
                hasSpecial = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        if (hasDigit && hasSpecial && hasUpper && passwordToTest.length() > 7) {
            user.setPassword(hashPassword(user.getPassword()));
            return save(user);
        } else throw new InputMismatchException("Incorrect password - at least 8 characters, one upper character, one digit and one in "+specialsChar);
    }

}
