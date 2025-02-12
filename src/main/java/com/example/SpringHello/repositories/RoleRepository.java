package com.example.SpringHello.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.SpringHello.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;

@Repository
public class RoleRepository {

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  public RoleRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Set<Role> getByUserId(Long userId) {
    String sql = """
        SELECT r.id, r.name
        FROM roles r
        INNER JOIN user_roles ur ON r.id = ur.role_id
        WHERE ur.user_id = ?
        """;
    String cleanedSql = sql.stripIndent().replace("\n", " ").strip();

    System.out.println("Used parameter(s) from previous SQL statement: " + formatParameters("userId", userId));

    try {
      List<Role> roleList = jdbcTemplate.query(cleanedSql, new RoleRowMapper(), userId);
      return new HashSet<>(roleList);
    } catch (Exception e) {
      System.err.println("Error fetching roles for userId: " + userId);
      e.printStackTrace();
      return new HashSet<>();
    }
  }

  private static class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
      Role role = new Role();
      role.setId(rs.getLong("id"));
      role.setName(rs.getString("name"));
      return role;
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
