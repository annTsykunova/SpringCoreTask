package com.epam.spring.hometask.dao;

import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 2/6/2018.
 */
public interface PriceCounterDAO {
  public Map<String, Integer> getAll();
  public int get(String eventName);
  public void put(String name, int occurrences);
}
