package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.CallCounterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 2/6/2018.
 */
@Repository
public class CallCounterDAOImpl implements CallCounterDAO {
  static final String GET_OCCURRENCES = "select * from CALL_COUNTERS where name = ?";
  static final String SET_OCCURRENCES = "update CALL_COUNTERS set occurrences=? where name = ?";
  static final String GET_ALL = "select * from CALL_COUNTERS";


  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Map<String, Integer> getAll() {
    jdbcTemplate.query(GET_ALL, (ResultSetExtractor<Map>) rs -> {
      HashMap<String,Integer> mapRet= new HashMap<String,Integer>();
      while(rs.next()){
        mapRet.put(rs.getString(1),rs.getInt(2));
      }
      return mapRet;
    });
    return Collections.emptyMap();
  }

  @Override
  public int get(String eventName) {
    int nOccurrences = (int)jdbcTemplate.queryForObject(
        GET_OCCURRENCES, new Object[] { eventName },
        new BeanPropertyRowMapper(Integer.class));
    return nOccurrences;
  }

  @Override
  public void put(String name, int occurrences) {
    Object[] values = {name, occurrences};
    int[] types = {Types.VARCHAR, Types.INTEGER};
    jdbcTemplate.update(SET_OCCURRENCES, values, types);
  }
}
