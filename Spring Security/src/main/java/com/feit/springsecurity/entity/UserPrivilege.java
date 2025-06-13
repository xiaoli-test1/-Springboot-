package com.feit.springsecurity.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_priv")
public class UserPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "priv_id")
    private Long privId;

    // 构造函数
    public UserPrivilege() {
    }

    public UserPrivilege(Long userId, Long privId) {
        this.userId = userId;
        this.privId = privId;
    }

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPrivId() {
        return privId;
    }

    public void setPrivId(Long privId) {
        this.privId = privId;
    }
}