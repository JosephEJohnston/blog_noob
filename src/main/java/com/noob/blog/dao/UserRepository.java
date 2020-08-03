package com.noob.blog.dao;

import com.noob.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 第二个泛型是主键类型
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据方法名提供对应方法
    User findByUsernameAndPassword(String username, String password);
}
