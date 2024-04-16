package com.ngdugn.project.springcrud.service;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;

import java.util.List;

public interface UserService {

    UserAccount addUser(UserAccount userAccount);
    List<UserAccount> getUsers();
    UserAccount getUser(Integer id);

    UserAccount updateUser(Integer id, UserAccount userAccount);

    List<UserAccount> deleteUser(Integer id);

    void updateName(Integer id, UserDTO userDTO);

}
