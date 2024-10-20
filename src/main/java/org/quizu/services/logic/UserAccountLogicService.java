package org.quizu.services.logic;

import org.quizu.domain.exceptions.RequiredPropertiesMissingException;
import org.quizu.domain.exceptions.UserAlreadyExistsException;
import org.quizu.domain.exceptions.UserNotFoundException;
import org.quizu.domain.exceptions.WrongPasswordException;
import org.quizu.domain.models.UserAccount;
import org.quizu.domain.requests.UserAccountUpdateRequest;

public interface UserAccountLogicService {
    void registerUserAccount(UserAccount user) throws UserAlreadyExistsException, RequiredPropertiesMissingException;
    String login(String email, String password) throws UserNotFoundException, WrongPasswordException;

    String updateUserAccount(String email, UserAccountUpdateRequest updateRequest) throws WrongPasswordException, UserNotFoundException;

    void deleteUserAccount(String email) throws UserNotFoundException;

    void logout(String email);
}
