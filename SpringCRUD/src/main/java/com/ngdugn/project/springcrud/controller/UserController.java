package com.ngdugn.project.springcrud.controller;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/private-scoped/users")
    public ResponseEntity<UserAccount> addUser(@RequestBody UserAccount userAccount){
        UserAccount newUser = userService.addUser(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping(value = "/public/users")
    public List<UserAccount> getUsers() throws InterruptedException {
        return userService.getUsers();
    }

    @GetMapping(value = "/public/users/{id}")
    public UserAccount getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    @PutMapping(value = "/private/users/{id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable Integer id, @RequestBody UserAccount userAccount) {
        UserAccount updatedUser = userService.updateUser(id, userAccount);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping(value = "/private/users/{id}")
    public List<UserAccount> deleteUser(@PathVariable Integer id) throws InterruptedException {
        return userService.deleteUser(id);
    }

    @PatchMapping(value = "/private/users/update-name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        userService.updateName(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}
