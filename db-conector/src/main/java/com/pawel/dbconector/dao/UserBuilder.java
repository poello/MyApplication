package com.pawel.dbconector.dao;

public final class UserBuilder {
    //    @ID
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        return user;
    }
}
