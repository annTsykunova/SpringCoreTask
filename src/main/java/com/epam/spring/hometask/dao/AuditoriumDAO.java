package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Auditorium;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public interface AuditoriumDAO extends AbstractDAO<Auditorium,Long> {
  public Auditorium getByName(String name) throws DAOException;
}
