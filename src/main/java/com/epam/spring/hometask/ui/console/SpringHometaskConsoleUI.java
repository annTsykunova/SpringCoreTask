package com.epam.spring.hometask.ui.console;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.EventRating;
import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;
import com.epam.spring.hometask.service.AuditoriumService;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.EventService;
import com.epam.spring.hometask.service.UserService;
import com.epam.spring.hometask.ui.console.state.MainState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Simple console UI application for the hometask code. UI provides different
 * action to input and output data. In order for the application to work, the
 * Spring context initialization code should be placed into
 * {@link #initContext()} method.
 * 
 * @author Yuriy_Tkach
 */
public class SpringHometaskConsoleUI {

    private ApplicationContext context;

    public static void main(String[] args) {
        SpringHometaskConsoleUI ui = new SpringHometaskConsoleUI();
        try {
            ui.initContext();
            ui.run();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void initContext() {
       context =
            new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
        //throw new IllegalStateException("Please, add Spring context initialization logic here");
    }

    private void run() throws ServiceException {
        System.out.println("Welcome to movie theater console service");
        
        fillInitialData();

        MainState state = new MainState(context);

        state.run();

        System.out.println("Exiting.. Thank you.");
    }

    private void fillInitialData() throws ServiceException {
        UserService userService = context.getBean(UserService.class);
        EventService eventService = context.getBean(EventService.class);
        AuditoriumService auditoriumService = context.getBean(AuditoriumService.class);
        BookingService bookingService = context.getBean(BookingService.class);
        
        Auditorium auditorium = auditoriumService.getAll().iterator().next();
        if (auditorium == null) {
            throw new IllegalStateException("Failed to fill initial data - no auditoriums returned from AuditoriumService");
        }
        if (auditorium.getNumberOfSeats() <= 0) {
            throw new IllegalStateException("Failed to fill initial data - no seats in the auditorium " + auditorium.getName());
        }
        
        User user = new User();
        user.setEmail("my@email.com");
        user.setFirstName("Foo");
        user.setLastName("Bar");
        
        user = userService.save(user);
        
        Event event = new Event();
        event.setName("Grand concert");
        event.setRating(EventRating.MID);
        event.setBasePrice(10);
        LocalDateTime airDate = LocalDateTime.of(2020, 6, 15, 19, 30);
        event.addAirDateTime(airDate, auditorium);
        
        event = eventService.save(event);
        
        Ticket ticket1 = new Ticket(user, event, airDate, 1);
        bookingService.bookTickets(Collections.singleton(ticket1));
        
        if (auditorium.getNumberOfSeats() > 1) {
            User userNotRegistered = new User();
            userNotRegistered.setEmail("somebody@a.b");
            userNotRegistered.setFirstName("A");
            userNotRegistered.setLastName("Somebody");
            Ticket ticket2 = new Ticket(userNotRegistered, event, airDate, 2);
            bookingService.bookTickets(Collections.singleton(ticket2));
        }
    }
}
