package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.CallCounterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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
    Map<String, Object> result = jdbcTemplate.queryForMap(GET_ALL);
    Map<String, Integer> resultMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : result.entrySet()) {
      resultMap.put(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
    }
    return resultMap;
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
    int[] types = {Types.INTEGER, Types.VARCHAR};
    jdbcTemplate.update(SET_OCCURRENCES, values, types);
  }
}
