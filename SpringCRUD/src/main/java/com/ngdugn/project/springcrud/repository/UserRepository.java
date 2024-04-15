package com.ngdugn.project.springcrud.repository;

import com.ngdugn.project.springcrud.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Integer > {

}
