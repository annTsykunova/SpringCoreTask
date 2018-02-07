package com.epam.spring.hometask.ui.console.state;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Event;
import com.epam.spring.hometask.model.EventRating;
import com.epam.spring.hometask.service.AuditoriumService;
import com.epam.spring.hometask.service.EventService;
import org.springframework.context.ApplicationContext;

import java.util.Locale;

/**
 * State for managing events
 * 
 * @author Yuriy_Tkach
 */
public class EventManageState extends AbstractDomainObjectManageState<Event, EventService> {

    private AuditoriumService auditoriumService;

    public EventManageState(ApplicationContext context) {
        super(context.getBean(EventService.class));
        this.auditoriumService = context.getBean(AuditoriumService.class);
    }

    @Override
    protected String getObjectName() {
        return Event.class.getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    protected void printObject(Event event) {
        System.out.println("[" + event.getId() + "] " + event.getName() + " (Rating: " + event.getRating() + ", Price: "
                + event.getBasePrice() + ")");
    }

    @Override
    protected Event createObject() {
        System.out.println("Adding event");
        String name = readStringInput("Name: ");
        EventRating rating = readEventRating();
        double basePrice = readDoubleInput("Base price: ");

        Event event = new Event();
        event.setName(name);
        event.setRating(rating);
        event.setBasePrice(basePrice);
        Event savedEvent = null;
        try {
            savedEvent = service.save(event);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return savedEvent;
    }

    private EventRating readEventRating() {
        EventRating rating = null;
        do {
            String str = readStringInput("Rating (LOW, MID, HIGH): ");
            try {
                rating = EventRating.valueOf(str);
            } catch (Exception e) {
                rating = null;
            }
        } while (rating == null);
        return rating;
    }

    @Override
    protected int printSubActions(int maxDefaultActions) {
        int index = maxDefaultActions;
        System.out.println(" " + (++index) + ") Find event by name");
        System.out.println(" " + (++index) + ") Manage event info (air dates, auditoriums)");
        return index - maxDefaultActions;
    }

    @Override
    protected void runSubAction(int action, int maxDefaultActions) {
        int index = action - maxDefaultActions;
        switch (index) {
        case 1:
            findEventByName();
            break;
        case 2:
            manageEventInfo();
            break;
        default:
            System.err.println("Unknown action");
        }
    }

    private void manageEventInfo() {
        int id = readIntInput("Input event id: ");

        Event event = null;
        try {
            event = service.getById(Integer.valueOf(id));
            if (event == null) {
                System.out.println("Not found (searched for " + id + ")");
            } else {
                printDelimiter();

                AbstractState manageState = new EventInfoManageState(event, service, auditoriumService);
                manageState.run();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void findEventByName() {
        String name = readStringInput("Input event name: ");
        Event event = null;
        try {
            event = service.getByName(name);

            if (event == null) {
                System.out.println("Not found (searched for " + name + ")");
            } else {
                printObject(event);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
