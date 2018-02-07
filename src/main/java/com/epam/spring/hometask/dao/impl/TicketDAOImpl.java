package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.TicketDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.utils.GeneratorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Repository
public class TicketDAOImpl implements TicketDAO {

  private static final String INSERT_QUERY = "insert into tickets values(?, ?, ?, ?, ?, ?)";
  private static final String GET_ALL = "select * from tickets";


  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  UserDAO userDAO;

  @Autowired
  EventDAO eventDAO;

  @Override
  public Ticket getById(Integer key) throws DAOException {
    return null;
  }

  @Override
  public Ticket save(Ticket ticket) throws DAOException {
    Integer id = GeneratorId.generateId();
    Object[] values = {id, ticket.getUser().getId(), ticket.getEvent().getId(), ticket.getDateTime(), ticket.getSeat(), ticket.getPrice()};
    int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DATE, Types.INTEGER, Types.FLOAT};
    jdbcTemplate.update(INSERT_QUERY, values, types);
    return getById(id);
  }

  @Override
  public void delete(Ticket ticket) throws DAOException {

  }

  @Override
  public Collection<Ticket> getAll(){
      return new HashSet<Ticket>(jdbcTemplate.query(GET_ALL, (rs, rowNum) -> {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt(1));
        User user = new User();

        ticket.setUser(user);
        try {
          ticket.setUser(userDAO.getById(rs.getInt(2)));
          ticket.setEvent(eventDAO.getById(rs.getInt(3)));
        } catch (DAOException e) {
          e.printStackTrace();
        }
        if (rs.getDate(4) != null) {
          ticket.setDateTime(LocalDateTime.from(rs.getDate(4).toLocalDate()));
        }
        ticket.setSeat(rs.getInt(5));
        ticket.setPrice(rs.getFloat(6));
        return ticket;
      }));
  }

}
