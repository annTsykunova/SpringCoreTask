package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.EventRating;
import com.epam.spring.hometask.utils.GeneratorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.sql.DataSource;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Repository
public class EventDAOImpl implements EventDAO {

  private static final String GET_BY_NAME = "select * from events where name = ?";
  private static final String GET_BY_ID = "select * from events where id = ?";
  private static final String INSERT_QUERY = "insert into events values(?, ?, ?)";
  private static final String DELETE_QUERY = "delete from events";
  private static final String GET_ALL = "select * from events";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Event getById(Long key) throws DAOException {
    Event event = (Event) jdbcTemplate.queryForObject(
        GET_BY_ID, new Object[] { key },
        new BeanPropertyRowMapper(Event.class));
    return event;
  }

  @Override
  public void save(Event event) throws DAOException {
    Object[] values = {event.getName(), event.getBasePrice(), event.getRating().name()};
    int[] types = {Types.VARCHAR, Types.DOUBLE, Types.VARCHAR};
    jdbcTemplate.update(INSERT_QUERY, values, types);
  }

  @Override
  public void delete(Event event) throws DAOException {
    jdbcTemplate.update(con -> {
      PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
      statement.setLong(1, event.getId());
      return statement;
    });
  }

  @Override
  public Collection<Event> getAll() throws DAOException {
    return new HashSet<Event>(jdbcTemplate.query(GET_ALL, (rs, rowNum) -> {
      Event event = new Event();
      event.setId(rs.getLong(1));
      event.setName(rs.getString(2));
      event.setBasePrice(rs.getDouble(3));
      return event;
    }));
  }

  @Override
  public Event getByName(String name) throws DAOException {
    Event event = (Event) jdbcTemplate.queryForObject(
        GET_BY_NAME, new Object[] { name },
        new BeanPropertyRowMapper(Event.class));
    return event;
  }

}
