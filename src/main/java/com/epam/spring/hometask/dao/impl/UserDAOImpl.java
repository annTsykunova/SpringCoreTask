package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.utils.GeneratorId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class UserDAOImpl implements UserDAO {

  private Map<Long,User> users = new HashMap<>();

  @Override
  public User getByEmail(String email) throws DAOException {
    for (User user: users.values()) {
      if (user.getEmail().equals(email)){
        return user;
      }
    }
    return null;
  }

  @Override
  public User getById(Long key) throws DAOException {
    return users.get(key);
  }

  @Override
  public User save(User user) throws DAOException {
    Long id = GeneratorId.generateId();
    return users.put(id,user);
  }

  @Override
  public void delete(User user) throws DAOException {
    users.remove(user);
  }

  @Override
  public Collection<User> getAll() throws DAOException {
    return users.values();
  }
}
