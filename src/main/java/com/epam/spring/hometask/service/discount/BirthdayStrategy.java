package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.DiscountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Hanna_Tsykunova on 1/20/2018.
 */
public class BirthdayStrategy implements DiscountStrategy {

  private int discountValue;

  public void setDiscountValue(int discountValue) {
    this.discountValue = discountValue;
  }

  @Override
  public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
    final int days = 5;
    List<LocalDateTime> dates = Stream.iterate(airDateTime, d -> d.plusDays(1))
        .limit(days)
        .collect(Collectors.toList());
    if (dates.contains(user.getBirthDate())){
      return discountValue;
    }
    return 0;
  }
}
