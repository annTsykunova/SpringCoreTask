package com.epam.spring.hometask.service;

import com.epam.spring.hometask.exception.ServiceException;
import com.epam.spring.hometask.model.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     * @throws ServiceException
     */
    public @Nullable User getUserByEmail(@Nonnull String email) throws ServiceException;

}
