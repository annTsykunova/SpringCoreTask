package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.discount.DiscountService;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */

@Aspect
public class DiscountAspect {

  private Map<DiscountStrategy, Record> discountCounter = new HashMap<>();

  public static class Record {
    long totalCount;
    Map<User, Long> countPerUser = new HashMap<>();

    public long getTotalCount() {
      return totalCount;
    }

    public Map<User, Long> getCountPerUser() {
      return countPerUser;
    }
  }

  @Pointcut(value = "execution(* getDiscount(..)) && args(user, event, date, numberOfTickets)", argNames = "user,event,date,numberOfTickets")
  private void execDiscountStrategyGetDiscount(User user, Event event, LocalDateTime date, long numberOfTickets) {
  }

  @Pointcut("target(com.epam.spring.hometask.service.discount.DiscountStrategy)")
  private void targetDiscountStrategy() {
  }

  @AfterReturning(
      pointcut = "execDiscountStrategyGetDiscount(user, event, date, numberOfTickets) && targetDiscountStrategy()",
      returning = "discount", argNames = "jp,user,event,date,discount")
  public void afterDiscountStrategyGetDiscount(JoinPoint jp, User user, Event event, LocalDateTime date, long numberOfTickets, double discount) {

    System.out.println("afterDiscountStrategyGetDiscount for " + jp.getTarget().getClass().getName());

    if (discount != 0 && DiscountStrategy.class.isInstance(jp.getTarget())) {

      DiscountStrategy discountStrategy = (DiscountStrategy) jp.getTarget();

      if (!discountCounter.containsKey(discountStrategy)) {
        discountCounter.put(discountStrategy, new Record());
      }

      Record record = discountCounter.get(discountStrategy);
      record.totalCount++;

      if (record.countPerUser.containsKey(user)) {
        record.countPerUser.put(user, record.countPerUser.get(user) + 1);
      } else {
        record.countPerUser.put(user, 1L);
      }
    }
  }

  public Map<DiscountStrategy, Record> getDiscountCounter() {
    return discountCounter;
  }
}
