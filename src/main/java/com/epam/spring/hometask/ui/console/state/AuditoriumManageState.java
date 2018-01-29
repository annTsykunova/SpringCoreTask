package com.epam.spring.hometask.ui.console.state;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Auditorium;
import com.epam.spring.hometask.service.AuditoriumService;
import org.springframework.context.ApplicationContext;

import java.util.Set;

/**
 * State for managing auditoriums
 * 
 * @author Yuriy_Tkach
 */
public class AuditoriumManageState extends AbstractState {
    
    private AuditoriumService auditoriumService;

    public AuditoriumManageState(ApplicationContext context) {
        auditoriumService = (AuditoriumService) context.getBean("auditoriumService");
    }

    @Override
    protected int printMainActions() {
        System.out.println(" 1) Search auditorium by name");
        System.out.println(" 2) View all");
        return 2;
    }

    @Override
    protected void runAction(int action) {
        switch (action) {
        case 1:
            searchAuditorium();
            break;
        case 2:
            printDefaultInformation();
            break;
        default:
            System.err.println("Unknown action");
        }
    }

    private void searchAuditorium() {
        try {
            String searchTerm = readStringInput("Input auditorium name: ");
            Auditorium a = null;

                a = auditoriumService.getByName(searchTerm);

            if (a == null) {
                System.out.println("Not found (searched for: " + searchTerm + ")");
            } else {
                printAuditorium(a);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void printDefaultInformation() {
        System.out.println("All auditoriums:");
        Set<Auditorium> all = null;
        try {
            all = auditoriumService.getAll();

            all.forEach(a -> printAuditorium(a));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void printAuditorium(Auditorium a) {
        System.out.println(a.getName() + ", " + a.getNumberOfSeats() + " seats, vips: " + a.getVipSeats());
    }

}
