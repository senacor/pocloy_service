package com.senacor.bankathon2018.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class AxwaySession {

    @Id
    private String username;
    @Lob
    private String cookie;

    @Column(name = "DATE_TIME", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    public AxwaySession() {
    }

    public AxwaySession(String username, String cookie) {
        this.username = username;
        this.cookie = cookie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "AxwaySession{" +
                "username='" + username + '\'' +
                ", cookie='" + cookie + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
