package com.epam.spring.hometask.utils;

import java.util.Random;

public class GeneratorId {
  public static Long generateId(){
    Random randomno = new Random();
    return randomno.nextLong();
  }

}

