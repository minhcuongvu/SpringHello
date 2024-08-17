package com.example.SpringHello.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.SpringHello.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

@Repository
public class RoleRepository {

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  public RoleRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Set<Role> getByUserId(Long userId) {
    String sql = "SELECT id, user_id, role FROM user_roles "
        + "WHERE user_id = ?";
    List<Role> roleList = jdbcTemplate.query(sql, new RoleRowMapper(), userId);
    return new HashSet<>(roleList); // Convert List to Set
  }

  private static class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
      Role role = new Role();
      role.setId(rs.getLong("id"));
      role.setName(rs.getString("role"));
      return role;
    }
  }
}
