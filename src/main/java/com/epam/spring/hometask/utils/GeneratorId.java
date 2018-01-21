package com.epam.spring.hometask.utils;

import java.util.Random;

public class GeneratorId {
  private static Long ID = new Long(0);
  public static Long generateId(){
    return ++ID;
  }

}

