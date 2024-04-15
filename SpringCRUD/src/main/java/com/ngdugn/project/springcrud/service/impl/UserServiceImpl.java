package com.ngdugn.project.springcrud.service.impl;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.repository.UserRepository;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * add user
     */
    @Override
    public void addUser(UserAccount userAccount) {
        userRepository.save(userAccount);
    }

    /**
     * get users as list
     */
    @Override
    public List<UserAccount> getUsers() {
        return userRepository.findAll();
    }

    /**
     * get user by id
     */

    @Override
    public UserAccount getUser(Integer id) {
//        check weather the user is in database or not
        UserAccount userAccount = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        return userAccount;
    }

    /**
     * update user
     */

    @Override
    public void updateUser(Integer id, UserAccount userAccount) {
//        check weather the user is in database or not
        userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        userAccount.setUserId(id);

        userRepository.save(userAccount);

    }

    @Override
    public void deleteUser(Integer id) {
//        check weather the user is in database or not
        UserAccount userAccount = userRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        userRepository.delete(userAccount);
    }

    @Override
    public void updateName(Integer id, UserDTO userDTO) {
//        check weather the user is in database or not
        UserAccount user = userRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        user.setFullName(userDTO.getName());

        userRepository.save(user);

    }

}
