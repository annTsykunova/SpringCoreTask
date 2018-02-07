package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.AuditoriumDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.utils.GeneratorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
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
public class AuditoriumDAOImpl implements AuditoriumDAO {

  private static final String GET_BY_NAME = "select * from auditoriums where name = ?";
  private static final String GET_BY_ID = "select * from auditoriums where id = ?";
  private static final String INSERT_QUERY = "insert into auditoriums values(?, ?, ?, ?)";
  private static final String DELETE_QUERY = "delete from auditoriums";
  private static final String GET_ALL = "select * from auditoriums";


  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Auditorium getByName(String name) throws DAOException {
    Auditorium auditorium = new Auditorium();
    jdbcTemplate.query(GET_BY_NAME, new Object[] {name}, (rs, rowNum) -> {
      auditorium.setId(rs.getInt(1));
      auditorium.setName(rs.getString(2));
      auditorium.setNumberOfSeats(Long.parseLong(rs.getString(3)));
      auditorium.setVipSeats(LongStream.rangeClosed(1, rs.getLong(4)).boxed().collect(Collectors.toSet()));
      return auditorium;
    });
   return auditorium;
  }

  @Override
  public Auditorium getById(Integer key) throws DAOException {
    Auditorium auditorium = new Auditorium();
    jdbcTemplate.query(GET_BY_ID, new Object[] {key},(rs, rowNum) -> {
      auditorium.setId(rs.getInt(1));
      auditorium.setName(rs.getString(2));
      auditorium.setNumberOfSeats(Long.parseLong(rs.getString(3)));
      auditorium.setVipSeats(LongStream.rangeClosed(1, rs.getLong(4)).boxed().collect(Collectors.toSet()));
      return auditorium;
    });
    return auditorium;
  }

  @Override
  public Auditorium save(Auditorium auditorium) throws DAOException {
    Integer id = GeneratorId.generateId();
    Object[] values = {id,auditorium.getName(), auditorium.getAllSeats().size(), auditorium.getVipSeats().size()};
    int[] types = {Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
    jdbcTemplate.update(INSERT_QUERY, values, types);
    auditorium.setId(id);
    return auditorium;
  }

  @Override
  public void delete(Auditorium auditorium) throws DAOException {
    jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
      statement.setLong(1, auditorium.getId());
      return statement;
    });
  }

  @Override
  public Collection<Auditorium> getAll() throws DAOException {
    return new HashSet<Auditorium>(jdbcTemplate.query(GET_ALL, (rs, rowNum) -> {
      Auditorium auditorium = new Auditorium();
      auditorium.setId(rs.getInt(1));
      auditorium.setName(rs.getString(2));
      auditorium.setNumberOfSeats(Long.parseLong(rs.getString(3)));
      auditorium.setVipSeats(LongStream.rangeClosed(1, rs.getLong(4)).boxed().collect(Collectors.toSet()));
      return auditorium;
    }));
  }
}
