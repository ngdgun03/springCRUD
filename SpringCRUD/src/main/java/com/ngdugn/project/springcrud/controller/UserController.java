package com.ngdugn.project.springcrud.controller;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserAccount> addUser(@RequestBody UserAccount userAccount){
        UserAccount newUser = userService.addUser(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    /**
     * get users as list
     */

    @GetMapping
    public List<UserAccount> getUsers() {
        return userService.getUsers();
    }

    /**
     * get user by id
     */

    @GetMapping("/{id}")
    public UserAccount getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    /**
     * update user
     */

    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable Integer id, @RequestBody UserAccount userAccount) {
        UserAccount updatedUser = userService.updateUser(id, userAccount);
        return ResponseEntity.ok().body(updatedUser);
    }

    /**
     * delete user
     */

    @DeleteMapping("/{id}")
    public List<UserAccount> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }


    /**
     * update name
     */

    @PatchMapping("/update-name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        userService.updateName(id, userDTO);

        return ResponseEntity.noContent().build();
    }


}
