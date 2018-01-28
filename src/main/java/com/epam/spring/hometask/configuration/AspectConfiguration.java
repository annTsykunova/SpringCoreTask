package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.aspect.CounterAspect;
import com.epam.spring.hometask.aspect.DiscountAspect;
import com.epam.spring.hometask.aspect.LuckyWinnerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

  @Bean
  public CounterAspect counterAspect() {
    return new CounterAspect();
  }

  @Bean
  public DiscountAspect discountAspect() {
    return new DiscountAspect();
  }

  @Bean
  public LuckyWinnerAspect luckyWinnerAspect() {
    return new LuckyWinnerAspect();
  }
}
