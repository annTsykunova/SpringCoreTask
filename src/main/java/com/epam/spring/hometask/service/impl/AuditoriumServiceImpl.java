package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.AuditoriumDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

  @Autowired
  private AuditoriumDAO auditoriumDAO;

  @Override
  public void remove(@Nonnull Auditorium object) throws ServiceException {

  }

  @Override
  public Auditorium getById(@Nonnull Integer id) throws ServiceException {
    Auditorium auditorium;
    try {
      auditorium = auditoriumDAO.getById(id);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return auditorium;
  }

  @Nonnull
  @Override
  public Set<Auditorium> getAll() throws ServiceException {
    Set<Auditorium> auditoriums;
    try {
      auditoriums = new HashSet<>(auditoriumDAO.getAll());
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return auditoriums;
  }

  @Nullable
  @Override
  public Auditorium getByName(@Nonnull String name) throws ServiceException {
    Auditorium auditorium;
    try {
      auditorium = auditoriumDAO.getByName(name);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return auditorium;
  }

  @Override
  public Auditorium save(@Nonnull Auditorium object) throws ServiceException {
    Auditorium auditorium = null;
    try {
      auditorium = auditoriumDAO.save(object);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return auditorium;
  }

  public void setAuditoriumDAO(AuditoriumDAO auditoriumDAO) {
    this.auditoriumDAO = auditoriumDAO;
  }
}
