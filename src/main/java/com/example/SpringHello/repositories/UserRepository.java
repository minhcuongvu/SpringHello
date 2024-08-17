package com.example.SpringHello.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.SpringHello.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, email, password FROM users WHERE username = ? LIMIT 1;";
        return queryForObject(sql, new UserRowMapper(), username);
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPassword(encodePassword(user.getPassword()));
            return user;
        }
    }

    // You can use this method for other queries with different parameters
    private <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            T result = jdbcTemplate.queryForObject(sql, rowMapper, params);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            // Handle exception if necessary
            return Optional.empty();
        }
    }
}
