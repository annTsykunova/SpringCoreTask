package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.utils.GeneratorId;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class EventDAOImpl implements EventDAO {

  private Map<Long,Event> events = new HashMap<>();

  @Override
  public Event getById(Long key) throws DAOException {
    return events.get(key);
  }

  @Override
  public Event save(Event event) throws DAOException {
    Long id = GeneratorId.generateId();
    return events.put(id, event);
  }

  @Override
  public void delete(Event event) throws DAOException {
    events.remove(event);
  }

  @Override
  public Collection<Event> getAll() throws DAOException {
    return events.values();
  }

  @Override
  public Event getByName(String name) throws DAOException {
    for (Event event: events.values()) {
      if (event.getName().equals(name)){
        return event;
      }
    }
    return null;
  }

}
