package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.model.User;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public interface UserDAO extends AbstractDAO<User,Long> {
  public User getByEmail(String email) throws DAOException;
}
