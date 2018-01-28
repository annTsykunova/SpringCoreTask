package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

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
    Object targetClass = joinPoint.getTarget();
    Class<?> bookingServiceClass = targetClass.getClass();

    try {
      Method method = bookingServiceClass.getMethod("checkLucky", User.class);
      method.setAccessible(true);
      boolean isUserLucky = (boolean) method.invoke(targetClass, user);

      if (isUserLucky) {
        // set ticket price to 0
        ticket.setPrice(0D);
      }

      joinPoint.proceed(new Object[]{user, ticket});
    } catch (Throwable throwable) {
      System.err.println("Exception during checkLucky user: " + throwable.getMessage());
    }
  }
}
