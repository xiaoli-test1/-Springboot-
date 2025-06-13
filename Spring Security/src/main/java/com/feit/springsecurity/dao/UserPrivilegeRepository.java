package com.feit.springsecurity.dao;

import com.feit.springsecurity.entity.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {
}
