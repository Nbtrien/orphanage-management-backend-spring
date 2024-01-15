package com.graduatebackend.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_token")
public class UserToken extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_TOKEN_ID")
    private int userTokenId;

    @Basic
    @Column(name = "TOKEN")
    private String token;

    @Basic
    @Column(name = "IS_EXPIRED")
    private boolean isExpired;

    @Basic
    @Column(name = "IS_REVOKED")
    private boolean isRevoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public int getUserTokenId() {
        return userTokenId;
    }

    public void setUserTokenId(int userTokenId) {
        this.userTokenId = userTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
