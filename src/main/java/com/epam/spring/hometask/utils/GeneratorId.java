package com.epam.spring.hometask.utils;

import java.util.Random;

public class GeneratorId {
  private static Integer ID = new Integer(0);
  public static Integer generateId(){
    return ++ID;
  }

}

