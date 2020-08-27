package com.example.webApp;

public class SearchingQuery {
    private String byId;
    private String byLogin;

    public String getById() {
        return byId;
    }

    public void setById(String byId) {
        this.byId = byId;
    }

    public String getByLogin() {
        return byLogin;
    }

    public void setByLogin(String byLogin) {
        this.byLogin = byLogin;
    }
}
