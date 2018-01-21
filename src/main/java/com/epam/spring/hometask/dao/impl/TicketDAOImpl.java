package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.TicketDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.utils.GeneratorId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class TicketDAOImpl implements TicketDAO {

  private Set<Ticket> tickets = new HashSet<>();

  @Override
  public Ticket getById(Long key) throws DAOException {
    for (Ticket ticket: tickets) {
      if (ticket.getId() == key) {
        return ticket;
      }
    }
    return null;
  }

  @Override
  public Ticket save(Ticket ticket) throws DAOException {
    Long id = GeneratorId.generateId();
    ticket.setId(id);
    boolean flag = tickets.add(ticket);
    if (flag) {
      return ticket;
    }
    return null;
  }

  @Override
  public void delete(Ticket ticket) throws DAOException {

  }

  @Override
  public Collection<Ticket> getAll() throws DAOException {
    return tickets;
  }

}
