package com.ngdugn.project.springcrud.repository;

import com.ngdugn.project.springcrud.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserAccount, Integer > {

}
