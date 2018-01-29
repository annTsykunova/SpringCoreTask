package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.aspect.CounterAspect;
import com.epam.spring.hometask.aspect.DiscountAspect;
import com.epam.spring.hometask.aspect.LuckyWinnerAspect;
import com.epam.spring.hometask.dao.AuditoriumDAO;
import com.epam.spring.hometask.dao.EventDAO;
import com.epam.spring.hometask.dao.TicketDAO;
import com.epam.spring.hometask.dao.UserDAO;
import com.epam.spring.hometask.dao.impl.AuditoriumDAOImpl;
import com.epam.spring.hometask.dao.impl.EventDAOImpl;
import com.epam.spring.hometask.dao.impl.TicketDAOImpl;
import com.epam.spring.hometask.dao.impl.UserDAOImpl;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.service.AuditoriumService;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.EventService;
import com.epam.spring.hometask.service.UserService;
import com.epam.spring.hometask.service.discount.BirthdayStrategy;
import com.epam.spring.hometask.service.discount.DiscountService;
import com.epam.spring.hometask.service.discount.DiscountServiceImpl;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.EveryTenTicketStrategy;
import com.epam.spring.hometask.service.impl.AuditoriumServiceImpl;
import com.epam.spring.hometask.service.impl.BookingServiceImpl;
import com.epam.spring.hometask.service.impl.EventServiceImpl;
import com.epam.spring.hometask.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.spring.hometask"})
@PropertySource({
    "classpath:discountValue.properties",
    "classpath:october-cinema.properties",
    "classpath:velcom-cinema.properties"})
public class ApplicationConfig {

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

  @Bean(name = "auditoriumOct")
  public Auditorium auditoriumOct() {
    return createAuditorium("october");
  }

  @Bean(name = "auditoriumVelc")
  public Auditorium auditoriumVelc() {
    return createAuditorium("velcom");
  }

  @Bean(name = "birthdayDiscount")
  public DiscountStrategy birthdayDiscount() {
    String discount = env.getProperty("birthdayDiscount");
    DiscountStrategy birthdayStrategy = new BirthdayStrategy(Integer.valueOf(discount));
    return birthdayStrategy;
  }

  @Bean(name = "everyTenTicketStrategy")
  public DiscountStrategy everyTenTicketStrategy() {
    String discount = env.getProperty("everyTenTicketDiscount");
    DiscountStrategy everyTenTicketStrategy = new EveryTenTicketStrategy(Integer.valueOf(discount));
    return everyTenTicketStrategy;
  }

  @Bean(name = "discountStrategies")
  public List<DiscountStrategy> discountStrategies() {
    return Arrays.asList(birthdayDiscount(), everyTenTicketStrategy());
  }

  @Bean(name = "discountService")
  public DiscountService getDiscountService() {
    DiscountService discountService = new DiscountServiceImpl();
    discountService.setDiscountStrategies(discountStrategies());
    return discountService;
  }

  @Bean(name = "eventDAO")
  public EventDAO getEventDAO() {
    return new EventDAOImpl();
  }

  @Bean(name = "ticketDAO")
  public TicketDAO getTicketDAO() {
    return new TicketDAOImpl();
  }

  @Bean(name = "userDAO")
  public UserDAO getUserDAO() {
    return new UserDAOImpl();
  }

  @Bean(name = "auditoriumDAO")
  public AuditoriumDAO getAuditoriumDAO() {
    return new AuditoriumDAOImpl();
  }

  @Bean(name = "eventService")
  public EventService getEventService() {
    return new EventServiceImpl();
  }

  @Bean(name = "userService")
  public UserService getUserService() {
    return new UserServiceImpl();
  }

  @Bean(name = "auditoriumService")
  public AuditoriumService getAuditoriumService() {
    return new AuditoriumServiceImpl();
  }

  @Bean(name = "bookingService")
  public BookingService getBookingService() {
    return new BookingServiceImpl();
  }

  @Bean(name = "counterAspect")
  public CounterAspect counterAspect() {
    return new CounterAspect();
  }

  @Bean(name = "discountAspect")
  public DiscountAspect discountAspect() {
    return new DiscountAspect();
  }

  @Bean(name = "luckyWinnerAspect")
  public LuckyWinnerAspect luckyWinnerAspect() {
    return new LuckyWinnerAspect();
  }
}
