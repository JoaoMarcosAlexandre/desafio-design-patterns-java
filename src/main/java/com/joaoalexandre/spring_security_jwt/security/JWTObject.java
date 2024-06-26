package com.joaoalexandre.spring_security_jwt.security;

import io.jsonwebtoken.lang.NestedCollection;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTObject {
    private String subject; //nome do usuario
    private Date issuedAt; //data de criação do token
    private Class<? extends NestedCollection> expiration; // data de expiração do token
    private List<String> roles; //perfis de acesso

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Object getExpiration() {
        return expiration;
    }

    public void setExpiration(Class<? extends NestedCollection> expiration) {
        this.expiration = expiration;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setRoles(String... roles){
        this.roles = Arrays.asList(roles);
    }

    public void setIssuedAt(Class<? extends NestedCollection> aClass) {
    }
}