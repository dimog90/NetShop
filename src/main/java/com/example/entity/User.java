package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

// сущность Юзер, для хранения и передачи данных о полбзователе
@Entity
@Table(name = "user", schema = "shop")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // указываем что имя должно быть в виде email
    @Column(name = "username")
    @Email(message = "username")
    private String username;

    // указываем длину пароля
    @Column(name = "password")
    @Size(min = 6, max = 20, message = "password")
    private String password;

    // статус пользователя (залогинен или нет)
    @Column(name = "logging_in")
    private Boolean loggingIn;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getLoggingIn() {
        return loggingIn;
    }

    public void setLoggingIn(Boolean loggingIn) {
        this.loggingIn = loggingIn;
    }

}
