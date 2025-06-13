package com.feit.springsecurity.service;

import com.feit.springsecurity.entity.User;
import com.feit.springsecurity.entity.Privilege; // 确保导入了权限实体
import com.feit.springsecurity.entity.UserPrivilege; // 确保导入了用户权限实体
import com.feit.springsecurity.dao.UserRepository;
import com.feit.springsecurity.dao.PrivilegeRepository; // 导入权限数据库接口
import com.feit.springsecurity.dao.UserPrivilegeRepository; // 导入用户权限数据库接口
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository; // 引入 UserPrivilegeRepository

    public User save(User user) {
        // 保存用户
        User savedUser = userRepository.save(user);

        // 自动为新用户分配 ROLE_COMMON 权限
        Privilege roleCommon = privilegeRepository.findByAuthority("ROLE_COMMON");
        UserPrivilege userPrivilege = new UserPrivilege(savedUser.getId(), roleCommon.getId());
        userPrivilegeRepository.save(userPrivilege); // 保存用户权限

        return savedUser;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}