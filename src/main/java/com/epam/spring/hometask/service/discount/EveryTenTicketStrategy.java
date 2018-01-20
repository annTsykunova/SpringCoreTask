package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;

/**
 * Created by Hanna_Tsykunova on 1/20/2018.
 */
public class EveryTenTicketStrategy implements DiscountStrategy {
  private int discountValue;

  public void setDiscountValue(int discountValue) {
    this.discountValue = discountValue;
  }

  @Override
  public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
    if (numberOfTickets/10 >= 1){
      if (numberOfTickets%10 == 0) {
        return discountValue/10;
      }else {
        return discountValue/numberOfTickets;
      }

    }
    return 0;
  }
}
