package com.feit.springsecurity.dao;

import com.feit.springsecurity.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {
}