package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */
@Aspect
public class LuckyWinnerAspect {

  @Pointcut("execution(* * ..BookingService.bookTicket(..))")
  public void accessEventTicketBooking() {
  }

  @Around(value = "accessEventTicketBooking() && args(user, ticket)",
      argNames = "joinPoint,user,ticket")
  public void checkLuckyUser(ProceedingJoinPoint joinPoint, User user, Ticket ticket) {
    try {
      if (isUserLucky()) {
        // set ticket price to 0
        ticket.setPrice(0D);
      }

      joinPoint.proceed(new Object[]{user, ticket});
    } catch (Throwable throwable) {
      System.err.println("Exception during checkLucky user: " + throwable.getMessage());
    }
  }

  private boolean isUserLucky(){
    Random rand = new Random();
    return 1 == rand.nextInt(1000);
  }
}
