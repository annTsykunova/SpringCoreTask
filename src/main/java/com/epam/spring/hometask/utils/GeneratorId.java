package com.epam.spring.hometask.utils;

import java.util.Random;

public class GeneratorId {
  private static Integer ID = new Integer(0);
  public static Integer generateId(){
    Random rn = new Random();
    int i = rn.nextInt(1000) % 2+ rn.nextInt(654);
    return i;
  }

}

