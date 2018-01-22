package com.epam.spring.hometask.service;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.Ticket;
import com.epam.spring.hometask.model.User;

import java.time.LocalDateTime;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface BookingService {

    /**
     * Getting price when buying all supplied seats for particular event
     * 
     * @param event
     *            Event to get base ticket price, vip seats and other
     *            information
     * @param dateTime
     *            Date and time of event air
     * @param user
     *            User that buys ticket could be needed to calculate discount.
     *            Can be <code>null</code>
     * @param seats
     *            Set of seat numbers that user wants to buy
     * @return total price
     * @throws ServiceException
     */
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
        @Nonnull Set<Long> seats) throws ServiceException;

    /**
     * Books tickets in internal system. If user is not
     * <code>null</code> in a ticket then booked tickets are saved with it
     * 
     * @param tickets
     *            Set of tickets
     * @throws ServiceException
     */
    public void bookTickets(@Nonnull Set<Ticket> tickets) throws ServiceException;

    /**
     * Getting all purchased tickets for event on specific air date and time
     * 
     * @param event
     *            Event to get tickets for
     * @param dateTime
     *            Date and time of airing of event
     * @return set of all purchased tickets
     * @throws ServiceException
     */
    public @Nonnull Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) throws ServiceException;



}
