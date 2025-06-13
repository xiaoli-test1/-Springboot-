package com.feit.springsecurity.dao;

import com.feit.springsecurity.entity.User; // 确保导入 User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        String sql = "select * from user where username=?";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    // 需要确保返回的权限格式为ROLE_开头
    public List<String> findPrivilegesByUserId(Long userId) {
        // 修复SQL语句：添加缺失的表别名和空格
        String sql = "SELECT CONCAT('ROLE_', p.authority) as authority " +
                "FROM user u " +
                "JOIN user_priv up ON u.id = up.user_id " +
                "JOIN priv p ON up.priv_id = p.id " +
                "WHERE u.id = ?";

        // 修复结果处理：直接获取权限字符串列表
        return jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);
    }
}