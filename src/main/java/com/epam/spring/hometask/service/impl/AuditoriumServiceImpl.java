package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.AuditoriumDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.service.AuditoriumService;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class AuditoriumServiceImpl implements AuditoriumService {

  private AuditoriumDAO auditoriumDAO;

  @Nonnull
  @Override
  public Set<Auditorium> getAll() throws ServiceException {
    Set<Auditorium> auditoriums;
    try {
      auditoriums = (Set<Auditorium>) auditoriumDAO.getAll();
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
}
