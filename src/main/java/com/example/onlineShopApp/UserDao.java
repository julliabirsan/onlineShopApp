package com.example.onlineShopApp;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username); //select * from user where username=''
}
