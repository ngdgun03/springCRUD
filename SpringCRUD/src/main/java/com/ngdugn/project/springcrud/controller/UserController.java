package com.ngdugn.project.springcrud.controller;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody UserAccount userAccount){
        userService.addUser(userAccount);
        return "Add successfully!";
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

    @GetMapping("/get")
    public UserAccount getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    /**
     * update user
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody UserAccount userAccount) {
        userService.updateUser(id, userAccount);

        return ResponseEntity.noContent().build();
    }

    /**
     * delete user
     */

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
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
