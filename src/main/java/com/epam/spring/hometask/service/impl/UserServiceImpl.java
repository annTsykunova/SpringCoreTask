package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.UserService;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  @Nullable
  @Override
  public User getUserByEmail(@Nonnull String email) throws ServiceException {
    User user;
    try {
      user = userDAO.getByEmail(email);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return user;
  }

  @Override
  public User save(@Nonnull User object) throws ServiceException {
    User user = null;
    try {
      user = userDAO.save(object);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return user;
  }

  @Override
  public void remove(@Nonnull User object) throws ServiceException {
    try {
      userDAO.delete(object);
    } catch (DAOException e) {
      throw new ServiceException();
    }
  }

  @Override
  public User getById(@Nonnull Long id) throws ServiceException {
    User user = null;
    try {
      user = userDAO.getById(id);
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return user;
  }

  @Nonnull
  @Override
  public Collection<User> getAll() throws ServiceException {
    Collection<User> userList = null;
    try {
      userList = userDAO.getAll();
    } catch (DAOException e) {
      throw new ServiceException();
    }
    return userList;
  }
}
