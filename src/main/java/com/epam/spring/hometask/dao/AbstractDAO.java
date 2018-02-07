package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.exception.DAOException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public interface AbstractDAO<T, PK extends Serializable> {
  T getById(PK key) throws DAOException;

  T save(T object) throws DAOException;

  void delete(T object) throws DAOException;

  Collection<T> getAll() throws DAOException;
}
