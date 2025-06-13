package com.feit.springsecurity.dao;

import com.feit.springsecurity.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
    // 若有自定义查询需求，可在此添加方法
}