package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.discount.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class DiscountServiceImpl implements DiscountService {
  private List<DiscountStrategy> discountStrategyList;

  @Override
  public int getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
    List<Integer> discounts = new ArrayList<>();
    for(DiscountStrategy discountStrategy : discountStrategyList){
      discounts.add(discountStrategy.getDiscount(user, event,airDateTime,numberOfTickets));
    }
    return Collections.max(discounts);
  }
}
