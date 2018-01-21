package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.TicketDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.exception.DAOException;
import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.EventRating;
import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.discount.DiscountService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class BookingServiceImpl implements BookingService {

  private TicketDAO ticketDAO;

  private UserDAO userDAO;

  private DiscountService discountService;

  @Override
  public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
      @Nonnull Set<Long> seats) throws ServiceException {
    double basePrice = event.getBasePrice();
    double price;
    double discount = 0;
    try {
      discount = discountService.getDiscount(user, event, dateTime, seats.size());
      Set<Long> vipSeats = event.getAuditoriums().floorEntry(dateTime).getValue().getVipSeats();
      long countOfVipSeats = seats.stream().filter(vipSeats::contains).count();
      price = basePrice*countOfVipSeats*2 + basePrice*(seats.size()-countOfVipSeats);
      if (event.getRating().equals(EventRating.HIGH)) {
        price *= 1.2;
      }
      price = (price * (100 - discount)) / 100;
    }
    catch (ServiceException e) {
      throw new ServiceException();
    }
    return price;
  }

  @Override
  public void bookTickets(@Nonnull Set<Ticket> tickets) throws ServiceException {
    try {
      Collection<User> users = userDAO.getAll();
      for (Ticket ticket: tickets) {
        if (users.contains(ticket.getUser())){
          User user = ticket.getUser();
          user.getTickets().add(ticket);

          userDAO.save(user);
        }
        ticketDAO.save(ticket);
      }

    } catch (DAOException e) {
      throw new ServiceException();
    }

  }

  @Nonnull
  @Override
  public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) throws ServiceException {
      Set<Ticket> purchasedTicket = new HashSet<>();
    try {
      for(Ticket ticket: ticketDAO.getAll()){
        if (ticket.getEvent().equals(event)){
          if (ticket.getEvent().getAirDates().contains(dateTime)){
            purchasedTicket.add(ticket);
          }
        }
      }
      return purchasedTicket;
    }catch (DAOException e) {
      throw new ServiceException();
    }
  }

  public void setTicketDAO(TicketDAO ticketDAO) {
    this.ticketDAO = ticketDAO;
  }

  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public void setDiscountService(DiscountService discountService) {
    this.discountService = discountService;
  }
}
