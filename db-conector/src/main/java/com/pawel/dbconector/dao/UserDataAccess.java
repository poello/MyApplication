package com.pawel.dbconector.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service // dostarcza dane
//@Component // jest do procesowania danych
public class UserDataAccess {
    private final String INSERT_SQL = "INSERT INTO EXAMPLE_SCHEMA.USERS(login, password, first_name, last_name) values(:login, :password, :first_name, :last_name)";
    private final String SELECT_SQL = "SELECT * FROM EXAMPLE_SCHEMA.USERS WHERE id = :userId";
    private final String SELECT_BY_LOGIN_SQL = "SELECT * FROM EXAMPLE_SCHEMA.USERS WHERE login LIKE :login";
    private final String UPDATE_SQL = "UPDATE EXAMPLE_SCHEMA.USERS SET password = :password WHERE id = :userId";
    private final String DELETE_SQL = "DELETE EXAMPLE_SCHEMA.USERS WHERE id = :userId";

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void createUser(User user) {
//        jdbcTemplate.batchUpdate("INSERT INTO EXAMPLE_SCHEMA.USERS(login, password, first_name, last_name) VALUES (?,?,?,?)", user.getLogin(), Base64.getEncoder().encodeToString(user.getPassword().getBytes()), user.getFirstName(), user.getLastName());
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("login", user.getLogin())
                .addValue("password", Base64.getEncoder().encodeToString(user.getPassword().getBytes()))
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName());
        namedParameterJdbcTemplate.update(INSERT_SQL, parameters, holder);
        user.setId(holder.getKey().intValue());
    }

    public User getUserById(long userId) {
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("userId", userId);
        List<User> users = namedParameterJdbcTemplate.query(SELECT_SQL, parameter, new UserMapper());
        
        if(users.size() > 1) {
            return users.get(0);
        } else if (users.size() == 0) {
            return null;
        }
        
        return users.get(0);
    }

    public void deleteUserById(long userId) {
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("userId", userId);
        namedParameterJdbcTemplate.update(DELETE_SQL, parameter);
    }

    public void updateUserPasswordById(long userId, String password) {
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("password", Base64.getEncoder().encodeToString(password.getBytes()));
        namedParameterJdbcTemplate.update(UPDATE_SQL, parameter);
    }

    public List<User> getUsersByLogin(String login) {
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("login", "%"+login+"%");

        List<User> users = new ArrayList<>();
        users = namedParameterJdbcTemplate.query(SELECT_BY_LOGIN_SQL, parameter, new UserMapper());

        return users;
    }

    private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return UserBuilder.anUser()
                    .withId(rs.getLong("id"))
                    .withLogin(rs.getString("login"))
                    .withPassword(rs.getString("password"))
                    .withFirstName(rs.getString("first_name"))
                    .withLastName(rs.getString("last_name"))
                    .build();
        }
    }
}
