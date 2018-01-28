package com.epam.spring.hometask.aspect;

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

  private Map<Event, Long> counterEventGetByName = new HashMap<>();
  private Map<Event, Long> counterEventGetTicketPrice = new HashMap<>();
  private Map<Event, Long> counterEventTicketBooked = new HashMap<>();

  @Pointcut("execution(* getByName(..))")
  private void execEventRepositoryGetByName() {
  }

  @Pointcut("target(com.epam.spring.hometask.dao.EventDAO)")
  private void withinEventRepository() {
  }

  @Pointcut("execution(* getTicketsPrice(..)) && args(event, ..)")
  private void execTicketServiceGetTicketPrice(Event event) {
  }

  @Pointcut("target(com.epam.spring.hometask.service.TicketService)")
  private void withinTicketService() {
  }

  @Pointcut("execution(* *..TicketService.bookTicket(..))")
  private void execEventTicketBooked() {
  }

  @AfterReturning(
      pointcut = "execEventDAOGetByName() && withinEventDAO()",
      returning = "event")
  public void afterExecEventRepositoryGetByName(JoinPoint jp, Event event) {

    if (counterEventGetByName.containsKey(event)) {
      counterEventGetByName.put(event, counterEventGetByName.get(event) + 1);
    } else {
      counterEventGetByName.put(event, 1L);
    }

    System.out.println("afterEventGetByName from " + jp.getTarget().toString() + ", event: " + event);
  }

  @AfterReturning(
      pointcut = "execTicketServiceGetTicketPrice(event)", argNames = "jp,event")
  public void afterTicketServiceGetTicketPrice(JoinPoint jp, Event event) {

    if (counterEventGetTicketPrice.containsKey(event)) {
      counterEventGetTicketPrice.put(event, counterEventGetTicketPrice.get(event) + 1);
    } else {
      counterEventGetTicketPrice.put(event, 1L);
    }

    System.out.println("afterEventGetTicketPrice  from " + jp.getTarget().toString() + ", event: " + event);
  }

  @AfterReturning(
      pointcut = "execEventTicketBooked()",
      returning = "ticket")
  public void afterEventTicketBooked(JoinPoint jp, Ticket ticket) {

    Event event = ticket.getEvent();

    if (counterEventTicketBooked.containsKey(event)) {
      counterEventTicketBooked.put(event, counterEventTicketBooked.get(event) + 1);
    } else {
      counterEventTicketBooked.put(event, 1L);
    }

    System.out.println("afterEventTicketBooked  from " + jp.getTarget().toString() + ", event: " + event);
  }

  public Map<Event, Long> getCounterEventGetByName() {
    return counterEventGetByName;
  }

  public Map<Event, Long> getCounterEventGetTicketPrice() {
    return counterEventGetTicketPrice;
  }

  public Map<Event, Long> getCounterEventTicketBooked() {
    return counterEventTicketBooked;
  }

}
