package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.service.discount.BirthdayStrategy;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.EveryTenTicketStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */
@Configuration
public class DiscountConfiguration {

  @Bean
  public DiscountStrategy birthdayDiscount() {
    return new BirthdayStrategy();
  }

  @Bean
  public DiscountStrategy everyTenTicketStrategy() {
    return new EveryTenTicketStrategy();
  }

  @Bean
  public List<DiscountStrategy> discountStrategies() {
    return Arrays.asList(birthdayDiscount(), everyTenTicketStrategy());
  }
}
