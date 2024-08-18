package com.example.SpringHello.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.SpringHello.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String sql = """
                SELECT id, username, email, password
                FROM users
                WHERE username = ?
                LIMIT 1;
                """;
        String cleanedSql = sql.stripIndent().replace("\n", " ").strip();
        System.out.println("Used parameter(s) from previous SQL statement: " + formatParameters("username", username));

        return queryForObject(cleanedSql, new UserRowMapper(), username);
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

    // TODO: add this to a class of generic functions
    private <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            T result = jdbcTemplate.queryForObject(sql, rowMapper, params);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String formatParameters(Object... params) {
        return Arrays.stream(params)
                .map(param -> param instanceof List<?> ? formatList((List<?>) param) : formatParam(param))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String formatParam(Object param) {
        if (param == null)
            return "NULL";
        return "'" + param.toString() + "'";
    }

    private String formatList(List<?> list) {
        return list.stream()
                .map(this::formatParam)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
