package org.acme.services.persistence;

import org.acme.domain.exceptions.UserNotFoundException;
import org.acme.domain.models.UserAccount;

public interface UserAccountPersistenceService {
    UserAccount getUserAccount(String email) throws UserNotFoundException;
    void createUserAccount(UserAccount userAccount);

    UserAccount updateUserAccount(String email, UserAccount userAccountUpdateTo) throws UserNotFoundException;

    void deleteUserAccount(String email);

    boolean userExists(String email);
}
