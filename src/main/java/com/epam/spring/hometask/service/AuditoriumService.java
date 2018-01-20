package com.epam.spring.hometask.service;


import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.Auditorium;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    public @Nonnull Set<Auditorium> getAll() throws ServiceException;

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable Auditorium getByName(@Nonnull String name) throws ServiceException;

}
