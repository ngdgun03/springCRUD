package com.ngdugn.project.springcrud.service.impl;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.repository.UserRepository;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CacheManager cacheManager;

    /**
     * add user
     */
    @Override
    public UserAccount addUser(UserAccount userAccount) {
        evictUserCache();
        return userRepository.save(userAccount);

    }

    /**
     * get users as list
     */
    @Override
    @Cacheable(value = "user_cache", key = "'users'")
    public List<UserAccount> getUsers() throws InterruptedException {
        Thread.sleep(10_000);
        return userRepository.findAll();
    }

    public void evictUserCache() {
        cacheManager.getCache("user_cache").evict("users");
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
    public UserAccount updateUser(Integer id, UserAccount userAccount) {
//        check weather the user is in database or not
        userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        userAccount.setUserId(id);

       return userRepository.save(userAccount);

    }

    @Override

    public List<UserAccount> deleteUser(Integer id) throws InterruptedException {

        UserAccount userAccount = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));


        userRepository.delete(userAccount);


        return getUsers();
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
