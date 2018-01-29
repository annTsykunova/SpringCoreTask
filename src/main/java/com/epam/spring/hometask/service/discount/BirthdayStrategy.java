package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;

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

  public BirthdayStrategy() {
  }

  public BirthdayStrategy(int discountValue) {
    this.discountValue = discountValue;
  }

  @Override
  public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
    final int days = 5;
    if (user.getBirthDate() != null) {
      List<LocalDateTime> datesPlus = Stream.iterate(airDateTime, d -> d.plusDays(1))
          .limit(days)
          .collect(Collectors.toList());
      List<LocalDateTime> datesMinus = Stream.iterate(airDateTime, d -> d.minusDays(1))
          .limit(days)
          .collect(Collectors.toList());
      LocalDateTime userDate = LocalDateTime.of(airDateTime.getYear(), user.getBirthDate().getMonth(), user.getBirthDate().getDayOfMonth(), airDateTime.getHour(), airDateTime.getMinute());

      if (datesMinus.contains(userDate) || datesPlus.contains(userDate)) {
          return discountValue;
      }
    }
    return 0;
  }

}
