package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public interface TicketDAO extends AbstractDAO<Ticket,Long> {

  Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) throws DAOException;
}
