package com.adnan.joblisting.dto;

public class LoginResponse {

    private String id;
    private String cname;
    private String email;
    private String token;

    public LoginResponse(String id, String cname, String email, String token) {
        this.id = id;
        this.cname = cname;
        this.email = email;
        this.token = token;
    }

    public String getId() { return id; }
    public String getCname() { return cname; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}
