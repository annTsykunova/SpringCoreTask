package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.User;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     * 
     * @param user
     *            User that buys tickets. Can be <code>null</code>
     * @param event
     *            Event that tickets are bought for
     * @param airDateTime
     *            The date and time event will be aired
     * @param numberOfTickets
     *            Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) throws ServiceException;

    void setDiscountStrategies(List<DiscountStrategy> discountStrategies);
}
