package com.noob.blog.service;

import com.noob.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
