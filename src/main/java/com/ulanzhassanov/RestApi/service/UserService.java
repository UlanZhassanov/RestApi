package com.ulanzhassanov.RestApi.service;

import com.ulanzhassanov.RestApi.model.User;
import com.ulanzhassanov.RestApi.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
        return userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }
}
