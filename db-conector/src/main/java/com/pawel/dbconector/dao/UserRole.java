package com.pawel.dbconector.dao;

public enum UserRole {
    admin(1), user(2), moderator(3);
    private int roleID;

    UserRole(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    public static UserRole getRole(int id) {
        switch (id) {
            case 1:
                return admin;
            case 2:
                return user;
            case 3:
                return moderator;
            default:
                throw new IllegalArgumentException();
        }
    }
}
