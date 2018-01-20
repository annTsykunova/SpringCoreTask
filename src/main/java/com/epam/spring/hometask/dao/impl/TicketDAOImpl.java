package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.TicketDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;

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

  private Map<Long,Ticket> tickets = new HashMap<>();

  @Override
  public Ticket getById(Long key) throws DAOException {
    return null;
  }

  @Override
  public Ticket save(Ticket persistenceObject) throws DAOException {
    return null;
  }

  @Override
  public void delete(Ticket ticket) throws DAOException {

  }

  @Override
  public Collection<Ticket> getAll() throws DAOException {
    return tickets.values();
  }

  @Override
  public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) throws DAOException {
    Set<Ticket> purchasedTicket = new HashSet<>();
    for(Ticket ticket: tickets.values()){
      if (ticket.getEvent().equals(event)){
        if (ticket.getEvent().getAirDates().contains(dateTime)){
          purchasedTicket.add(ticket);
        }
      }
    }
    return purchasedTicket;
  }
}
