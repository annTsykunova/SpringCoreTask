package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.utils.GeneratorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.sql.DataSource;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Repository
public class UserDAOImpl implements UserDAO {

  private static final String GET_BY_EMAIL = "select * from users where email = ?";
  private static final String GET_BY_ID = "select * from users where id = ?";
  private static final String INSERT_QUERY = "insert into users values(?, ?, ?, ?, ?)";
  private static final String DELETE_QUERY = "delete from users";
  private static final String GET_ALL = "select * from users";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public User getByEmail(String email) throws DAOException {
    jdbcTemplate.query(GET_BY_EMAIL, (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getInt(1));
      user.setFirstName(rs.getString(2));
      user.setLastName(rs.getString(3));
      user.setEmail(rs.getString(4));
      if (rs.getDate(5) != null) {
        user.setBirthDate(LocalDateTime.from(rs.getDate(5).toLocalDate()));
      }
      return user;
    }, email);
    return null;
  }

  @Override
  public User getById(Integer key) throws DAOException {
    jdbcTemplate.query(GET_BY_ID, (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getInt(1));
      user.setFirstName(rs.getString(2));
      user.setLastName(rs.getString(3));
      user.setEmail(rs.getString(4));
      if (rs.getDate(5) != null) {
        user.setBirthDate((rs.getDate(5).toLocalDate().atStartOfDay()));
      }
      return user;
    }, key);
    return null;
  }

  @Override
  public User save(User user) throws DAOException {
    Integer id = GeneratorId.generateId();
    Date dateBirth =  Date.valueOf(user.getBirthDate().toLocalDate());
    Object[] values = {id, user.getFirstName(), user.getLastName(), user.getEmail(), dateBirth};
    int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE};
    jdbcTemplate.update(INSERT_QUERY, values, types);
    return getById(id);
  }

  @Override
  public void delete(User user) throws DAOException {
    jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
      statement.setLong(1, user.getId());
      return statement;
    });
  }

  @Override
  public Collection<User> getAll() throws DAOException {
    return new HashSet<User>(jdbcTemplate.query(GET_ALL, (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getInt(1));
      user.setFirstName(rs.getString(2));
      user.setLastName(rs.getString(3));
      user.setEmail(rs.getString(4));
      if (rs.getDate(5) != null) {
        user.setBirthDate(rs.getDate(5).toLocalDate().atStartOfDay());
      }
      return user;
    }));
  }
}
