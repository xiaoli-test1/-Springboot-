package com.feit.springsecurity.dao;

import com.feit.springsecurity.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByAuthority(String authority);
}