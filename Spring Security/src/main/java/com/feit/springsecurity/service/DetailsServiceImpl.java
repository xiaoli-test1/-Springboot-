package com.feit.springsecurity.service;

import com.feit.springsecurity.dao.UserDao;
import com.feit.springsecurity.entity.User; // 确保导入 User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder; // 使用内置 User
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username); // 从 UserDao 获取用户

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // 使用 Long 类型的 user ID 来获取权限
        List<String> privileges = userDao.findPrivilegesByUserId(user.getId());
        String[] privilegesArray = privileges.toArray(new String[0]);

        // 创建 UserDetails 对象
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword());
        builder.authorities(privilegesArray);
        return builder.build();
    }
}