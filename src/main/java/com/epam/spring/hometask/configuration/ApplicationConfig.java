package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.aspect.CounterAspect;
import com.epam.spring.hometask.aspect.DiscountAspect;
import com.epam.spring.hometask.aspect.LuckyWinnerAspect;

import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.service.discount.BirthdayStrategy;
import com.epam.spring.hometask.service.discount.DiscountService;
import com.epam.spring.hometask.service.discount.DiscountServiceImpl;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.EveryTenTicketStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;

/**
 * Created by Hanna_Tsykunova on 1/28/2018.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.spring.hometask"})
@PropertySource({
    "classpath:discountValue.properties",
    "classpath:october-cinema.properties",
    "classpath:velcom-cinema.properties",
    "classpath:database.properties"})
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

  @Bean(name = "dataSource")
  public DataSource createDataSource() {

    String driver = env.getProperty("db.driver");
    String url = env.getProperty("db.url");
    String user = env.getProperty("db.user");
    String pass  = env.getProperty("db.password");

    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(pass);

    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(createDataSource());
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
