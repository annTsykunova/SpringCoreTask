package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.utils.GeneratorId;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Repository
public class EventDAOImpl implements EventDAO {

  private Set<Event> events = new HashSet<>();

  @Override
  public Event getById(Long key) throws DAOException {
    for (Event event: events) {
      if (event.getId() == key) {
        return event;
      }
    }
    return null;
  }

  @Override
  public Event save(Event event) throws DAOException {
    Long id = GeneratorId.generateId();
    event.setId(id);
    boolean flag = events.add(event);
    if (flag) {
      return event;
    }
    return null;
  }

  @Override
  public void delete(Event event) throws DAOException {
    events.remove(event);
  }

  @Override
  public Collection<Event> getAll() throws DAOException {
    return events;
  }

  @Override
  public Event getByName(String name) throws DAOException {
    for (Event event: events) {
      if (event.getName().equals(name)){
        return event;
      }
    }
    return null;
  }

}
