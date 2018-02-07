package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.dao.BookingCounterDAO;
import com.epam.spring.hometask.dao.CallCounterDAO;
import com.epam.spring.hometask.dao.PriceCounterDAO;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */

@Aspect
public class CounterAspect {

  @Autowired
  private BookingCounterDAO bookingCounterDao;

  @Autowired
  private CallCounterDAO callCounterDao;

  @Autowired
  private PriceCounterDAO priceCounterDao;

  @Pointcut("execution(* getByName(..))")
  private void execEventDAOGetByName() {
  }

  @Pointcut("target(com.epam.spring.hometask.dao.EventDAO)")
  private void withinEventDAO() {
  }

  @Pointcut("execution(* getTicketsPrice(..)) && args(event, ..)")
  private void execBookingServiceGetTicketPrice(Event event) {
  }

  @Pointcut("target(com.epam.spring.hometask.service.BookingService)")
  private void withinBookingService() {
  }

  @Pointcut("execution(* *..BookingService.bookTicket(..))")
  private void execEventTicketBooked() {
  }

  @AfterReturning(
      pointcut = "execEventDAOGetByName() && withinEventDAO()",
      returning = "event")
  public void afterExecEventRepositoryGetByName(JoinPoint jp, Event event) {
    String eventName = event.getName();
    if (callCounterDao.getAll().containsKey(event)) {
      callCounterDao.put(eventName, callCounterDao.get(eventName) + 1);
    } else {
      callCounterDao.put(eventName, 1);
    }

    System.out.println("afterEventGetByName from " + jp.getTarget().toString() + ", event: " + event);
  }

  @AfterReturning(
      pointcut = "execBookingServiceGetTicketPrice(event)", argNames = "jp,event")
  public void afterBookingServiceGetTicketPrice(JoinPoint jp, Event event) {
    String eventName = event.getName();
      if (priceCounterDao.getAll().containsKey(eventName)) {
        priceCounterDao.put(eventName, priceCounterDao.get(eventName) + 1);
      }
     else {
      priceCounterDao.put(eventName, 1);
    }

    System.out.println("afterEventGetTicketPrice  from " + jp.getTarget().toString() + ", event: " + event);
  }

  @AfterReturning(
      pointcut = "execEventTicketBooked()",
      returning = "ticket")
  public void afterEventTicketBooked(JoinPoint jp, Ticket ticket) {

    Event event = ticket.getEvent();
    String eventName = event.getName();
    if (bookingCounterDao.getAll().containsKey(eventName)) {
      bookingCounterDao.put(eventName, bookingCounterDao.get(eventName) + 1);
    } else {
      bookingCounterDao.put(eventName, 1);
    }

    System.out.println("afterEventTicketBooked  from " + jp.getTarget().toString() + ", event: " + event);
  }

}
