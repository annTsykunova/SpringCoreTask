package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;

import java.time.LocalDateTime;


public interface DiscountStrategy {
  double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);
}
