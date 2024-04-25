package com.ngdugn.project.springcrud.controller;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public List<UserAccount> addUser(@RequestBody List<UserAccount> userAccounts) {
        List<UserAccount> newUserList = new ArrayList<>();
        for (UserAccount userAccount : userAccounts) {
            List<UserAccount> addedUsers = userService.addUser(userAccount);
            newUserList.addAll(addedUsers);
        }
        return newUserList;
    }



    @GetMapping()
    public List<UserAccount> getUsers() throws InterruptedException {
        return userService.getUsers();
    }

    @GetMapping(value ="/{id}")
    public UserAccount getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable Integer id, @RequestBody UserAccount userAccount) {
        UserAccount updatedUser = userService.updateUser(id, userAccount);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id)  {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/update-name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        userService.updateName(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}
