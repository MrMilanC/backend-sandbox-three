package com.example.backendsandboxthree.service;

import com.example.backendsandboxthree.exception.UserException;
import com.example.backendsandboxthree.model.User;
import com.example.backendsandboxthree.repository.CartRepository;
import com.example.backendsandboxthree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    public boolean doesUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository
                .findByUsername(username);
    }

    public User addUser(User user) throws UserException {
        return userRepository.save(user);
    }

    public User updateUser(User user) throws UserException {
        User c = userRepository.findById(user.getId()).orElseThrow(() -> new UserException("Customer not found"));
        if (c != null) {
            userRepository.save(user);
        }
        return c;
    }

    public User removeUser(Long userId) throws UserException {
        User userRemoved = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        userRepository.delete(userRemoved);
        return userRemoved;
    }

    public List<User> viewAllUser() throws UserException {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users;
        } else {
            throw new UserException("customer not found");
        }
    }

    public User viewUser(Long userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new UserException("User not found with user id - " + userId);
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
