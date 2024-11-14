package org.quizu.services.persistence;

import org.quizu.domain.exceptions.UserNotFoundException;
import org.quizu.domain.models.UserAccount;

public interface UserAccountPersistenceService {
    UserAccount getUserAccount(String email) throws UserNotFoundException;
    void createUserAccount(UserAccount userAccount);

    void updateUserAccount(String email, UserAccount userAccountUpdateTo) throws UserNotFoundException;

    void deleteUserAccount(String email) throws UserNotFoundException;

    boolean userExists(String email);
}
