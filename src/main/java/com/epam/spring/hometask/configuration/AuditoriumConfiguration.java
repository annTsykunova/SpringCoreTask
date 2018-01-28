package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.model.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */
@Configuration
@PropertySource({
        "classpath:properties/october-cinema.properties",
        "classpath:properties/velcom-cinema.properties"})
public class AuditoriumConfiguration {

  @Autowired
  private Environment env;

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  private Auditorium createAuditorium(String prefix) {
    String nameString = env.getProperty(prefix + ".name");
    String strNumberOfSeats = env.getProperty(prefix + ".seats.number");
    Long[] vipSeats = env.getProperty(prefix + ".seats.vip", Long[].class);
    Set<Long> setVipSeats = Arrays.stream(vipSeats).collect(Collectors.toSet());

    Auditorium auditorium = new Auditorium();
    auditorium.setName(nameString);
    auditorium.setNumberOfSeats(Integer.valueOf(strNumberOfSeats));
    auditorium.setVipSeats(setVipSeats);
    return auditorium;
  }

  @Bean
  public Auditorium auditoriumOct() {
    return createAuditorium("auditoriumOct");
  }

  @Bean
  public Auditorium auditoriumVelc() {
    return createAuditorium("auditoriumVelc");
  }

}
