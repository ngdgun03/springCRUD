package com.ngdugn.project.springcrud.service.impl;

import com.ngdugn.project.springcrud.dto.UserDTO;
import com.ngdugn.project.springcrud.entity.UserAccount;
import com.ngdugn.project.springcrud.repository.UserRepository;
import com.ngdugn.project.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ListOperations<String, Object> listOperations;
    /**
     * add user
     */
    @Override
    public List<UserAccount> addUser(UserAccount userAccount) {


        listOperations.rightPushAll("userList", userAccount);
        return getUser();
    }


    public List<UserAccount> getUser() {

        List<Object> userListObjects = listOperations.range("userList", 0, -1);
        List<UserAccount> userList = new ArrayList<>();

        if (userListObjects != null) {
            for (Object obj : userListObjects) {
                if (obj instanceof UserAccount) {
                    userList.add((UserAccount) obj);
                }
            }
        }

        return userList;
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
        String key = "user:" + id;
        if (redisTemplate.hasKey(key)) {
            return (UserAccount) redisTemplate.opsForValue().get(key);
        } else {
            UserAccount userAccount = userRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));
            redisTemplate.opsForValue().set(key, userAccount);
            return userAccount;
        }
    }

    /**
     * update user
     */

    @Override
    public UserAccount updateUser(Integer id, UserAccount userAccount) {

        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        userAccount.setUserId(id);

        userRepository.save(userAccount);
        redisTemplate.opsForValue().set("user:" + id, userAccount);

        return userAccount;
    }

    @Override

    public void deleteUser(Integer id) {

        UserAccount userAccount = userRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        userRepository.delete(userAccount);


        redisTemplate.delete("user:" + id);

    }


    @Override
    public void updateName(Integer id, UserDTO userDTO) {
        UserAccount user = userRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user Id:" + id));

        user.setFullName(userDTO.getName());

        userRepository.save(user);
        redisTemplate.opsForValue().set("user:" + id, user);

    }

}
