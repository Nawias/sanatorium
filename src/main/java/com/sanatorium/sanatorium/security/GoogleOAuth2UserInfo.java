package com.sanatorium.sanatorium.security;

public class GoogleOAuth2UserInfo {
    private String email;
    private String id;
    private String name;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GoogleOAuth2UserInfo{" +
                "email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
