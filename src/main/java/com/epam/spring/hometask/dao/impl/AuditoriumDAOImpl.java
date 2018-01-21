package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.AuditoriumDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.utils.GeneratorId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class AuditoriumDAOImpl implements AuditoriumDAO {

  private Map<Long,Auditorium> auditoriums = new HashMap<>();

  @Override
  public Auditorium getByName(String name) throws DAOException {
    for (Auditorium auditorium: auditoriums.values()) {
      if (auditorium.getName().equals(name)) {
        return auditorium;
      }
    }
    return null;
  }

  @Override
  public Auditorium getById(Long key) throws DAOException {
    return auditoriums.get(key);
  }

  @Override
  public Auditorium save(Auditorium auditorium) throws DAOException {
    Long id = GeneratorId.generateId();
    Auditorium savedAuditorium = auditoriums.put(id, auditorium);
    if (savedAuditorium != null){
      return savedAuditorium;
    }
    return auditorium;
  }

  @Override
  public void delete(Auditorium auditorium) throws DAOException {
    auditoriums.remove(auditorium);
  }

  @Override
  public Collection<Auditorium> getAll() throws DAOException {
    return auditoriums.values();
  }

  public void setAuditoriums(Map<Long, Auditorium> auditoriums) {
    this.auditoriums = auditoriums;
  }
}
