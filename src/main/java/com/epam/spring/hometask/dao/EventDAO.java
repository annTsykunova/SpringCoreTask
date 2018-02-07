package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Event;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public interface EventDAO extends AbstractDAO<Event,Integer> {
  public Event getByName(String name) throws DAOException;
  //public Collection<Event> getForDateRange(Date from, Date to) throws DAOException;
  //public Collection<Event> getNextEvents(Date to) throws DAOException;
}
