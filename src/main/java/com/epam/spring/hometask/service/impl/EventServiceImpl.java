package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.service.EventService;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class EventServiceImpl implements EventService {

  private EventDAO eventDAO;

  @Nullable
  @Override
  public Event getByName(@Nonnull String name) throws ServiceException {
    Event event;
    try {
      event = eventDAO.getByName(name);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return event;
  }

  @Override
  public Event save(@Nonnull Event object) throws ServiceException {
    Event event;
    try {
      event = eventDAO.save(object);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return event;
  }

  @Override
  public void remove(@Nonnull Event object) throws ServiceException {
    try {
      eventDAO.delete(object);
    } catch (DAOException e) {
      throw new ServiceException();
    }
  }

  @Override
  public Event getById(@Nonnull Long id) throws ServiceException {
    Event event;
    try {
      event = eventDAO.getById(id);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return event;
  }

  @Nonnull
  @Override
  public Collection<Event> getAll() throws ServiceException {
    Collection<Event> events;
    try {
      events = eventDAO.getAll();
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return events;
  }

  public void setEventDAO(EventDAO eventDAO) {
    this.eventDAO = eventDAO;
  }
}
