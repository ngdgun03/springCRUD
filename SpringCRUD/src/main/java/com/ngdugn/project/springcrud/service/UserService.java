package com.ngdugn.project.springcrud.service;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;

import java.util.List;

public interface UserService {

    void addUser(UserAccount userAccount);
    List<UserAccount> getUsers();
    UserAccount getUser(Integer id);

    void updateUser(Integer id, UserAccount userAccount);

    void deleteUser(Integer id);

    void updateName(Integer id, UserDTO userDTO);

}
