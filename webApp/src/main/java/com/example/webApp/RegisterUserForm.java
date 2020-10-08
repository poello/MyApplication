package com.example.webApp;

public class RegisterUserForm {
    private String email;
    private String password1;
    private String password2;
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
