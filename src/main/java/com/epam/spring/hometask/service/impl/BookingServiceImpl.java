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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
@Service
public class BookingServiceImpl implements BookingService {

  @Autowired
  private TicketDAO ticketDAO;

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private DiscountService discountService;

  @Override
  public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
      @Nonnull Set<Long> seats) throws ServiceException {

    double basePrice = event.getBasePrice();
    double priceAll = 0;
    double discount = 0;
    try {
      List<Long> listOfSeats = new ArrayList<>(seats);
      Set<Long> vipSeats = event.getAuditoriums().get(dateTime).getVipSeats();
      for (int i = 0; i < listOfSeats.size(); ++i) {
        double price = 0;
        if (vipSeats.contains(listOfSeats.get(i))) {
          price += basePrice * 2;
        } else {
          price = basePrice;
        }
        discount = discountService.getDiscount(user, event, dateTime, i);
        price *= (100 - discount) / 100;
        priceAll += price;
      }

      if (EventRating.HIGH.equals(event.getRating())){
        priceAll*= 1.2;
      }
    }
    catch (ServiceException e) {
      throw new ServiceException("Exception: we have issue with tickets price");
    }
    return priceAll;
  }

  @Override
  public void bookTickets(@Nonnull Set<Ticket> tickets) throws ServiceException {
    try {
      Set<Long> seats;
      Collection<User> users = userDAO.getAll();
      for (Ticket ticket: tickets) {
        if (users.contains(ticket.getUser())){
          User user = ticket.getUser();
          user.getTickets().add(ticket);
          userDAO.save(user);
        }

        double price = getTicketPrice(ticket.getEvent(),ticket.getDateTime(),ticket.getUser(),ticket.getSeat());
        ticket.setPrice(price);
        ticketDAO.save(ticket);
      }

    } catch (DAOException e) {
      throw new ServiceException("Exception: the tickets is not booked");
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
      throw new ServiceException("Exception:can't get all tickets");
    }
  }

  @Override
  public double getTicketPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
      @Nonnull long seat) throws ServiceException {
    double basePrice = event.getBasePrice();
    Set<Long> vipSeats = event.getAuditoriums().get(dateTime).getVipSeats();
    double price = 0;
    if (vipSeats.contains(seat)) {
      price += basePrice * 2;
    } else {
      price = basePrice;
    }
    if (EventRating.HIGH.equals(event.getRating())) {
      price *= 1.2;
    }

    return price;
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
